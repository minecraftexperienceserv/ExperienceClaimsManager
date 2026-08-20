package su.nightexpress.excellentclaims.land.claim.validation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.claim.ClaimPermissionAPI;
import su.nightexpress.excellentclaims.api.service.ActionResult;
import su.nightexpress.excellentclaims.core.Lang;
import su.nightexpress.excellentclaims.land.data.model.LandClaim;
import su.nightexpress.excellentclaims.land.lang.LandLang;
import su.nightexpress.excellentclaims.land.permission.LandPerms;
import su.nightexpress.excellentclaims.land.settings.LandSettings;
import su.nightexpress.excellentclaims.land.util.LandConsts;
import su.nightexpress.excellentclaims.land.util.LandUtils;
import su.nightexpress.nightcore.util.BukkitThing;
import su.nightexpress.nightcore.util.NumberUtil;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;
import su.nightexpress.nightcore.util.placeholder.CommonPlaceholders;

@NullMarked
public class LandActionValidator {

    private final LandSettings         settings;
    private final ClaimPermissionAPI   permissions;
    private final LandQuotaValidator   quota;
    private final LandOverlapValidator overlap;

    public LandActionValidator(LandSettings settings,
                               ClaimPermissionAPI permissions,
                               LandQuotaValidator quota,
                               LandOverlapValidator overlap) {
        this.settings = settings;
        this.permissions = permissions;
        this.quota = quota;
        this.overlap = overlap;
    }

    public ActionResult validateCreate(Player player, World world) {
        // Check World
        if (this.isWorldBanned(player, world)) {
            return ActionResult.fail(LandLang.CLAIMING_BAD_WORLD);
        }

        if (!this.quota.canClaimMore(player)) {
            return ActionResult.fail(LandLang.CREATION_QUOTA_EXCEEDED, ctx -> ctx
                .with(CommonPlaceholders.GENERIC_AMOUNT, () -> NumberUtil.format(this.quota.getMaxClaims(player)))
            );
        }

        return ActionResult.ok();
    }

    public ActionResult validateRemove(Player player, LandClaim claim) {
        if (!claim.isOwner(player) && !this.permissions.hasBypass(player)) {
            return ActionResult.fail(Lang.ERROR_NO_CLAIM_PERMISSION);
        }

        return ActionResult.ok();
    }

    public boolean isAllowedToExpand(Player player, LandClaim claim) {
        return claim.isOwner(player) || this.permissions.hasBypass(player);
    }

    public ActionResult validateExpansion(Player player,
                                          World world,
                                          ChunkPos targetChunk,
                                          LandClaim claimToExpand) {
        // Only claim owners or players with bypass permission can expand claims.
        if (!this.isAllowedToExpand(player, claimToExpand)) {
            return ActionResult.fail(Lang.ERROR_NO_CLAIM_PERMISSION);
        }

        // Check if the target chunk is already part of the claim being expanded.
        if (claimToExpand.contains(targetChunk)) {
            return ActionResult.fail(LandLang.CLAIMING_ALREADY_CLAIMED, ctx -> ctx
                .with(claimToExpand.placeholders())
            );
        }

        // Check if the player can expand the claim based on their chunks quota.
        if (!this.quota.canExpandClaim(player, claimToExpand)) {
            return ActionResult.fail(LandLang.CLAIMING_CHUNK_QUOTA, ctx -> ctx
                .with(claimToExpand.placeholders())
                .with(CommonPlaceholders.GENERIC_AMOUNT, () -> NumberUtil.format(this.quota.getMaxClaimSize(player)))
            );
        }

        // Check if there are any overlapping claims in the target chunk and if the player has permission to bypass overlap restrictions.
        if (!player.hasPermission(LandPerms.BYPASS_CLAIMING_OVERLAP) && !this.permissions.hasBypass(player)) {
            ActionResult overlapResult = this.overlap.validateOverlap(player, world, targetChunk);
            if (!overlapResult.success()) return overlapResult;
        }

        return ActionResult.ok();
    }

    public ActionResult validateUnclaim(Player player, ChunkPos targetChunk, LandClaim claim) {
        // Check if the player is the owner of the claim or has bypass permission to unclaim chunks.
        if (!claim.isOwner(player) && !this.permissions.hasBypass(player)) {
            return ActionResult.fail(Lang.ERROR_NO_CLAIM_PERMISSION);
        }

        // Ensure the claim has the target chunk.
        if (!claim.contains(targetChunk)) {
            return ActionResult.fail(LandLang.UNCLAIMING_CHUNK_NOT_CLAIMED, ctx -> ctx
                .with(claim.placeholders())
            );
        }

        // Check if the claim has more than one chunk before allowing unclaiming.
        if (claim.getChunksAmount() <= LandConsts.SINGLE_CHUNK) {
            return ActionResult.fail(LandLang.UNCLAIMING_LAST_CHUNK, ctx -> ctx
                .with(claim.placeholders())
            );
        }

        // Check if removing the target chunk would disrupt the claim's contiguity.
        if (!this.checkDisruptedChunks(claim, targetChunk)) {
            return ActionResult.fail(LandLang.UNCLAIMING_CHUNK_WOULD_DISCONNECT, ctx -> ctx
                .with(claim.placeholders())
            );
        }

        return ActionResult.ok();
    }

    private boolean checkDisruptedChunks(LandClaim claim, ChunkPos targetChunk) {
        Set<ChunkPos> currentChunks = claim.getChunkData().getChunkPositions();
        Set<ChunkPos> remainingChunks = new HashSet<>(currentChunks);
        remainingChunks.remove(targetChunk);

        // If no chunks remain or only one chunk remains, there's no disruption.
        if (remainingChunks.size() <= LandConsts.SINGLE_CHUNK) return true;

        for (ChunkPos chunk : remainingChunks) {
            if (!this.isConnectedToAny(chunk, remainingChunks)) {
                return false; // Found a chunk that is not connected to any other remaining chunk.
            }
        }

        return true;
    }

    private boolean isConnectedToAny(ChunkPos chunk, Set<ChunkPos> chunks) {
        // Check if the given chunk is adjacent to any other chunk in the set.
        for (ChunkPos other : chunks) {
            if (LandUtils.areChunksConnected(chunk, other)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWorldBanned(Player player, World world) {
        if (player.hasPermission(LandPerms.BYPASS_CLAIMING_WORLD)) {
            return false;
        }

        String worldName = BukkitThing.getAsString(world);

        return this.settings.getClaimBannedWorlds().contains(worldName);
    }
}