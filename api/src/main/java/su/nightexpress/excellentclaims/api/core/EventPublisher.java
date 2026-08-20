package su.nightexpress.excellentclaims.api.core;

import org.bukkit.event.Event;
import org.jspecify.annotations.NonNull;

public interface EventPublisher {

    /**
     * Fires the given event.
     *
     * @param event the event to fire
     * @return true if the event was not cancelled, false otherwise
     */
    boolean fireEvent(@NonNull Event event);
}
