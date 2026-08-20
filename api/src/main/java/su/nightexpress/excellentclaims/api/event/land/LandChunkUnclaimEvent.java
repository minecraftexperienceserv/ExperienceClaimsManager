package su.nightexpress.excellentclaims.api.event.land;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.land.Land;
import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;

@NullMarked
public class LandChunkUnclaimEvent extends PlayerLandEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final World    world;
    private final ChunkPos chunk;

    private boolean cancelled;

    public LandChunkUnclaimEvent(Land land, Player player, World world, ChunkPos chunk) {
        super(land, player);
        this.world = world;
        this.chunk = chunk;
    }

    public World getWorld() {
        return this.world;
    }

    public ChunkPos getChunk() {
        return chunk;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
