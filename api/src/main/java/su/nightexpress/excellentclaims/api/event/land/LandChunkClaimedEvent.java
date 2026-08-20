package su.nightexpress.excellentclaims.api.event.land;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.land.Land;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;

@NullMarked
public class LandChunkClaimedEvent extends PlayerLandEvent {

    private static final HandlerList handlers = new HandlerList();

    private final World    world;
    private final ChunkPos chunk;

    public LandChunkClaimedEvent(Land land, Player player, World world, ChunkPos chunk) {
        super(land, player);
        this.world = world;
        this.chunk = chunk;
    }

    public World getWorld() {
        return this.world;
    }

    public ChunkPos getChunk() {
        return this.chunk;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
