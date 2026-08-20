package su.nightexpress.excellentclaims.rules.evaluation.controller.environment;

import org.bukkit.entity.LightningStrike;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.rule.EventState;
import su.nightexpress.excellentclaims.rules.evaluation.EvaluatorEngine;
import su.nightexpress.excellentclaims.rules.evaluation.context.environment.LightningStrikeContext;
import su.nightexpress.excellentclaims.rules.evaluation.controller.AbstractEventController;

@NullMarked
public class LightningStrikeController extends AbstractEventController {

    public LightningStrikeController(EvaluatorEngine engine) {
        super(engine);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onLightningStrike(LightningStrikeEvent event) {
        LightningStrike lightning = event.getLightning();
        if (lightning.isEffect()) return; // We only want to handle the actual lightning strike, not the effect

        LightningStrikeContext context = new LightningStrikeContext(lightning);

        if (this.engine.evaluate(context).state() == EventState.DENY) {
            // Since LightningStrike has no way to be changed to effect only, we cancel the event and strike the effect manually
            event.setCancelled(true);

            lightning.getWorld().strikeLightningEffect(lightning.getLocation());
        }
    }
}
