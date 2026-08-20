package su.nightexpress.excellentclaims.rules.evaluation.context.environment;

import org.bukkit.entity.LightningStrike;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.rule.context.ActionContext;

@NullMarked
public record LightningStrikeContext(LightningStrike lightning) implements ActionContext {

}
