package su.nightexpress.excellentclaims.land.data;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.core.id.Identifier;
import su.nightexpress.excellentclaims.core.claim.data.AbstractDataService;
import su.nightexpress.excellentclaims.land.LandsRepository;
import su.nightexpress.excellentclaims.land.data.model.LandClaim;
import su.nightexpress.excellentclaims.land.io.LandIOService;
import su.nightexpress.excellentclaims.land.util.LandConsts;
import su.nightexpress.excellentclaims.land.util.LandUtils;
import su.nightexpress.nightcore.util.geodata.GeoUtils;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;
import su.nightexpress.nightcore.util.geodata.pos.ExactPos;

@NullMarked
public class LandDataService extends AbstractDataService<LandClaim> {

    public LandDataService(LandIOService ioService, LandsRepository repository) {
        super(ioService, repository);
    }

    public LandClaim createClaim(Identifier id, Player player, World world, ChunkPos chunkPos) {
        LandClaim claim = this.ioService.createClaim(id, world);

        int blockX = GeoUtils.shiftToCoord(chunkPos.getX());
        int blockZ = GeoUtils.shiftToCoord(chunkPos.getZ());

        claim.setSpawnLocation(ExactPos.from(world.getHighestBlockAt(blockX, blockZ)));
        claim.addOwner(player);
        claim.getChunkData().addChunkPosition(chunkPos);

        this.markDirty(claim);
        this.repository.register(claim);

        return claim;
    }

    public void addChunkPosition(LandClaim claim, ChunkPos chunkPos) {
        this.repository.remove(claim); // Clear current index to avoid duplicates
        claim.getChunkData().addChunkPosition(chunkPos);
        this.repository.register(claim); // Reindex claim with new chunk position

        this.markDirty(claim);
    }

    public void removeChunkPosition(LandClaim claim, ChunkPos chunkPos) {
        this.repository.remove(claim); // Clear current index to avoid duplicates
        claim.getChunkData().removeChunkPosition(chunkPos);
        this.repository.register(claim); // Reindex claim with updated chunk positions

        this.markDirty(claim);
    }

    public boolean isChunkClaimed(World world, ChunkPos chunkPos) {
        return !this.repository.getInChunk(world, chunkPos).isEmpty();
    }

    public boolean hasClaims(Player player) {
        return this.repository.countClaims(player) > LandConsts.ZERO_CHUNKS;
    }

    public Set<LandClaim> getAdjacentClaims(World world, ChunkPos targetChunk) {
        ChunkPos[] adjacentPositions = LandUtils.getAdjacentChunks(targetChunk);

        Set<LandClaim> adjacentClaims = new HashSet<>();
        for (ChunkPos pos : adjacentPositions) {
            adjacentClaims.addAll(this.repository.getInChunk(world, pos));
        }

        return adjacentClaims;
    }
}
