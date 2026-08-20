package su.nightexpress.excellentclaims.rules.impl.environment;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.claim.Claim;
import su.nightexpress.excellentclaims.api.rule.RuleBehavior;
import su.nightexpress.excellentclaims.api.rule.RuleCategory;
import su.nightexpress.excellentclaims.api.rule.RuleDefinition;
import su.nightexpress.excellentclaims.api.rule.RuleResult;
import su.nightexpress.excellentclaims.rules.evaluation.context.environment.LightningStrikeContext;
import su.nightexpress.excellentclaims.rules.spec.SimpleSpec;
import su.nightexpress.excellentclaims.rules.type.RuleTypes;

@NullMarked
public class LightningDamageRule extends SimpleSpec<LightningStrikeContext, Boolean> {

    public LightningDamageRule() {
        super(LightningStrikeContext.class, RuleTypes.BOOLEAN, RuleCategory.NATURAL);
    }

    @Override
    public RuleBehavior<LightningStrikeContext, Boolean> createBehavior() {
        return this.behaviorBuilder()
            .process((context, registry, resolver) -> {
                LightningStrike lightning = context.lightning();
                Location location = lightning.getLocation();

                Claim claim = registry.getPrioritizedClaim(location);
                if (claim == null) return RuleResult.allow();

                Boolean state = resolver.resolveValue(claim).orElse(null);
                return state == null ? RuleResult.allow() : RuleResult.of(state);
            })
            .build();
    }

    @Override
    public RuleDefinition getDefaultDefinition() {
        return RuleDefinition.builder("Lightning Damage")
            .description(
                "Controls whether lightning can cause damage,",
                "spawn fire, or affect entities."
            )
            .icon(Material.LIGHTNING_ROD)
            .build();
    }

    @Override
    public Boolean getDefaultValue() {
        return false;
    }
}
