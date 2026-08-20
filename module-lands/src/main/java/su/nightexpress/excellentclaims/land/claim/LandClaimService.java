package su.nightexpress.excellentclaims.land.claim;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import su.nightexpress.excellentclaims.api.core.EventPublisher;
import su.nightexpress.excellentclaims.api.core.id.Identifier;
import su.nightexpress.excellentclaims.api.event.land.LandChunkClaimEvent;
import su.nightexpress.excellentclaims.api.event.land.LandChunkClaimedEvent;
import su.nightexpress.excellentclaims.api.event.land.LandChunkUnclaimEvent;
import su.nightexpress.excellentclaims.api.event.land.LandChunkUnclaimedEvent;
import su.nightexpress.excellentclaims.api.event.land.LandCreateEvent;
import su.nightexpress.excellentclaims.api.event.land.LandCreatedEvent;
import su.nightexpress.excellentclaims.api.event.land.LandRemoveEvent;
import su.nightexpress.excellentclaims.api.event.land.LandRemovedEvent;
import su.nightexpress.excellentclaims.api.service.ActionResult;
import su.nightexpress.excellentclaims.core.Lang;
import su.nightexpress.excellentclaims.land.claim.validation.LandActionValidator;
import su.nightexpress.excellentclaims.land.data.LandDataService;
import su.nightexpress.excellentclaims.land.data.model.LandClaim;
import su.nightexpress.excellentclaims.land.lang.LandLang;
import su.nightexpress.excellentclaims.land.settings.LandSettings;
import su.nightexpress.excellentclaims.land.util.LandConsts;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;
import su.nightexpress.nightcore.util.placeholder.CommonPlaceholders;
import su.nightexpress.nightcore.util.placeholder.PlaceholderContext;

@NullMarked
public class LandClaimService {

    private final EventPublisher eventPublisher;

    private final LandSettings        settings;
    private final LandDataService     dataService;
    private final LandActionValidator validator;

    @Nullable
    private final LandBillingService billingService;

    public LandClaimService(EventPublisher eventPublisher,
                            LandSettings settings,
                            LandDataService dataService,
                            LandActionValidator validator,
                            @Nullable LandBillingService billingService) {
        this.eventPublisher = eventPublisher;

        this.settings = settings;
        this.dataService = dataService;

        this.validator = validator;
        this.billingService = billingService;
    }

    private String resolveClaimName(Player player, @Nullable String name) {
        if (name != null && !name.isBlank()) {
            return name;
        }

        String defName = this.settings.getDefaultName();

        PlaceholderContext namePlaceholders = PlaceholderContext.builder()
            .with(CommonPlaceholders.PLAYER.resolver(player))
            .build();


        return namePlaceholders.apply(defName);
    }

    public ActionResult createClaim(Player player, @Nullable String name) {
        // Normalize and validate name
        String displayName = this.resolveClaimName(player, name);
        String sanitized = Identifier.sanitize(displayName).orElse(null);
        if (sanitized == null) {
            return ActionResult.fail(LandLang.CLAIMING_INVALID_NAME);
        }

        // Check if claim with this name already exists
        Identifier claimId = Identifier.of(sanitized);
        LandClaim existingClaim = this.dataService.getClaim(claimId);
        if (existingClaim != null) {
            return ActionResult.fail(LandLang.CREATION_ALREADY_EXISTS, ctx -> ctx
                .with(existingClaim.placeholders())
            );
        }

        World world = player.getWorld();
        ChunkPos chunkPos = ChunkPos.from(player.getChunk());

        // Check if the chunk is already claimed.
        if (this.dataService.isChunkClaimed(world, chunkPos)) {
            return ActionResult.fail(LandLang.CLAIMING_ALREADY_OCCUPIED);
        }

        // Validate business rules (Quota, World, Permissions, etc)
        ActionResult validation = this.validator.validateCreate(player, world);
        if (!validation.success()) return validation;

        // Check if claim creation is allowed by external factors (plugins, events, etc)
        if (!this.eventPublisher.fireEvent(new LandCreateEvent(player, claimId))) {
            return ActionResult.fail(LandLang.CREATION_DENIED);
        }

        LandClaim claim = this.dataService.createClaim(claimId, player, world, chunkPos);
        claim.setName(displayName);
        claim.setIcon(this.settings.getDefaultIcon());
        claim.setPriority(this.settings.getDefaultPriority());

        this.eventPublisher.fireEvent(new LandCreatedEvent(claim, player));

        return ActionResult.ok(LandLang.CREATION_SUCCESS, ctx -> ctx.with(claim.placeholders()));
    }

    public ActionResult removeClaim(Player player, LandClaim claim) {
        ActionResult validation = this.validator.validateRemove(player, claim);
        if (!validation.success()) return validation;

        // TODO Billing Refund?

        boolean removeEvent = this.eventPublisher.fireEvent(new LandRemoveEvent(claim, player));
        if (!removeEvent) {
            return ActionResult.fail(LandLang.CLAIM_REMOVAL_DENIED);
        }

        this.dataService.deleteClaim(claim);
        this.eventPublisher.fireEvent(new LandRemovedEvent(claim, player));

        return ActionResult.ok(LandLang.CLAIM_REMOVAL_SUCCESS, ctx -> ctx.with(claim.placeholders()));
    }

    public ActionResult claimChunk(Player player, ChunkPos targetChunk, @Nullable LandClaim explicitClaim) {
        World world = player.getWorld();

        // Check if the target chunk is already claimed by any claim in the world.
        if (this.dataService.isChunkClaimed(world, targetChunk)) {
            return ActionResult.fail(LandLang.CLAIMING_ALREADY_OCCUPIED);
        }

        // Resolve target claim
        LandClaim claimToExpand;
        if (explicitClaim == null) {
            // If player has no claims, they cannot claim a chunk.
            if (!this.dataService.hasClaims(player)) {
                return ActionResult.fail(LandLang.CLAIMING_NO_LANDS_CREATED);
            }

            Set<LandClaim> adjacentClaims = this.dataService.getAdjacentClaims(world, targetChunk)
                .stream()
                .filter(claim -> this.validator.isAllowedToExpand(player, claim))
                .collect(Collectors.toSet());

            // If no adjacent claims are found, the target chunk cannot be claimed.
            if (adjacentClaims.isEmpty()) {
                return ActionResult.fail(LandLang.CLAIMING_CHUNK_NOT_ADJACENT);
            }

            // If multiple adjacent claims are found, we cannot determine which one to expand.
            if (adjacentClaims.size() > LandConsts.SINGLE_CHUNK) {
                return ActionResult.fail(LandLang.CLAIMING_MULTIPLE_LANDS_IN_LOCATION, ctx -> ctx
                    .with(CommonPlaceholders.GENERIC_NAME, () -> {
                        return adjacentClaims.stream()
                            .map(LandClaim::idValue)
                            .collect(Collectors.joining(Lang.OTHER_DELIMITER.text()));
                    })
                );
            }

            claimToExpand = adjacentClaims.iterator().next();
        }
        else {
            // Ensure the provided claim is in the same world as the target chunk.
            if (!explicitClaim.isWorld(world)) {
                return ActionResult.fail(LandLang.CLAIMING_DIFFERENT_WORLD, ctx -> ctx
                    .with(explicitClaim.placeholders())
                    .with(CommonPlaceholders.GENERIC_NAME, () -> explicitClaim.getWorldKey().asString())
                );
            }

            // Check if the current chunk is adjacent to the provided claim only if the claim has at least one chunk. 
            // If the claim is empty (which normally should not happen), it can be expanded from any direction.
            if (explicitClaim.getChunksAmount() > LandConsts.ZERO_CHUNKS && !explicitClaim.isAdjacentTo(targetChunk)) {
                return ActionResult.fail(LandLang.CLAIMING_CHUNK_NOT_ADJACENT, ctx -> ctx
                    .with(explicitClaim.placeholders())
                );
            }

            claimToExpand = explicitClaim;
        }

        // Validate business rules (Quota, Ownership, Permissions, etc)
        ActionResult validation = this.validator.validateExpansion(player, world, targetChunk, claimToExpand);
        if (!validation.success()) {
            return validation;
        }

        if (this.billingService != null) {
            ActionResult billCheck = this.billingService.canAffordClaim(player);
            if (!billCheck.success()) {
                return billCheck;
            }
        }

        boolean claimEvent = this.eventPublisher.fireEvent(new LandChunkClaimEvent(player, world, targetChunk));
        if (!claimEvent) return ActionResult.fail();

        this.dataService.addChunkPosition(claimToExpand, targetChunk);

        if (this.billingService != null) {
            this.billingService.chargeForClaim(player); // TODO Inform Player
        }

        this.eventPublisher.fireEvent(new LandChunkClaimedEvent(claimToExpand, player, world, targetChunk));

        return ActionResult.ok(LandLang.CLAIMING_SUCCESS, ctx -> ctx.with(claimToExpand.placeholders()));
    }

    public ActionResult unclaimChunk(Player player, ChunkPos targetChunk, LandClaim claim) {
        World world = player.getWorld();

        // Ensure the provided claim is in the same world as the target chunk.
        if (!claim.isWorld(world)) {
            return ActionResult.fail(LandLang.UNCLAIMING_DIFFERENT_WORLD, ctx -> ctx
                .with(claim.placeholders())
                .with(CommonPlaceholders.GENERIC_NAME, () -> claim.getWorldKey().asString())
            );
        }

        // Check business rules
        ActionResult validation = this.validator.validateUnclaim(player, targetChunk, claim);
        if (!validation.success()) return validation;

        // TODO Billing Refund?

        boolean unclaimEvent = this.eventPublisher.fireEvent(new LandChunkUnclaimEvent(claim, player, world,
            targetChunk));
        if (!unclaimEvent) return ActionResult.fail(); // TODO Msg

        this.dataService.removeChunkPosition(claim, targetChunk);
        this.eventPublisher.fireEvent(new LandChunkUnclaimedEvent(claim, player, world, targetChunk));

        return ActionResult.ok(LandLang.UNCLAIMING_SUCCESS, ctx -> ctx.with(claim.placeholders()));
    }
}
