package su.nightexpress.excellentclaims.api.service;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.MessageDispatcher;

@NullMarked
public interface FeedbackHandler {

    MessageDispatcher getDispatcher();

    default boolean handleFeedback(Player player, ActionResult result) {
        return result.handleFeedback((locale, ctx) -> {
            this.getDispatcher().send(locale, player, ctx);
        });
    }
}