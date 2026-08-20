package su.nightexpress.excellentclaims.land.claim.validation;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.ClaimRegistry;
import su.nightexpress.excellentclaims.api.claim.Claim;
import su.nightexpress.excellentclaims.api.claim.ClaimPermissionAPI;
import su.nightexpress.excellentclaims.api.claim.OwnableClaim;
import su.nightexpress.excellentclaims.api.service.ActionResult;
import su.nightexpress.excellentclaims.land.LandsRepository;
import su.nightexpress.excellentclaims.land.lang.LandLang;
import su.nightexpress.excellentclaims.land.settings.LandSettings;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;
import su.nightexpress.nightcore.util.placeholder.CommonPlaceholders;

@NullMarked
public class LandOverlapValidator {

    private final ClaimRegistry registry;
    //private final ClaimPermissionAPI permissions;
    //private final LandsRepository    repository;
    private final LandSettings settings;

    public LandOverlapValidator(ClaimRegistry registry,
                                ClaimPermissionAPI permissions,
                                LandsRepository repository,
                                LandSettings settings) {
        this.registry = registry;
        //this.permissions = permissions;
        //this.repository = repository;
        this.settings = settings;
    }

    public ActionResult validateOverlap(Player player, World world, ChunkPos targetChunk) {
        Set<Claim> overlaps = this.registry.getInChunk(world, targetChunk);
        if (overlaps.isEmpty()) return ActionResult.ok();

        if (!this.settings.isOverlappingAllowed()) {
            return ActionResult.fail(LandLang.CLAIMING_OVERLAP_FORBIDDEN);
        }

        Claim overlap = overlaps.stream()
            .filter(other -> !this.canOverlapWith(player, other))
            .findFirst()
            .orElse(null);

        if (overlap != null) {
            return ActionResult.fail(LandLang.CLAIMING_OVERLAP_INCOMPATIBLE_CLAIM, ctx -> ctx
                .with(CommonPlaceholders.GENERIC_NAME, overlap::getName)
            );
        }

        return ActionResult.ok();
    }

    private boolean canOverlapWith(Player player, Claim with) {
        if (with.isBackgroundClaim()) return true;

        String withModuleId = with.getModuleKey().value();

        boolean allowed = this.settings.getAllowedOverlappingTypes().contains(withModuleId);
        if (!allowed) return false;

        if (with instanceof OwnableClaim ownable) {
            return ownable.isOwner(player);
        }

        return false;
    }
}
