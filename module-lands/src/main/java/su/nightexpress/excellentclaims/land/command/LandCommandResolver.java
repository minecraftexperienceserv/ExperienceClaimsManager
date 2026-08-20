package su.nightexpress.excellentclaims.land.command;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import su.nightexpress.excellentclaims.api.claim.ClaimRepository;
import su.nightexpress.excellentclaims.land.data.model.LandClaim;
import su.nightexpress.excellentclaims.land.lang.LandLang;
import su.nightexpress.nightcore.commands.context.CommandContext;
import su.nightexpress.nightcore.commands.context.ParsedArguments;
import su.nightexpress.nightcore.commands.exceptions.CommandSyntaxException;
import su.nightexpress.nightcore.core.config.CoreLang;

@NullMarked
public class LandCommandResolver {

    private final ClaimRepository<LandClaim> repository;

    public LandCommandResolver(ClaimRepository<LandClaim> repository) {
        this.repository = repository;
    }

    /**
     * Resolves a LandClaim from the argument, falling back to the player's location.
     * Returns null if no claim can be found.
     * 
     * @param arguments The parsed command arguments.
     * @param argName   The name of the argument to resolve.
     * @return The resolved LandClaim, or null if not found.
     */
    public @Nullable LandClaim resolveFromArgument(ParsedArguments arguments, String argName) {
        if (arguments.contains(argName)) {
            return arguments.get(argName, LandClaim.class);
        }
        return null;
    }

    /**
     * Resolves a LandClaim from the argument, falling back to the player's location.
     * Throws an exception if no claim can be found, halting the command automatically.
     */
    public LandClaim resolveTarget(CommandContext context,
                                   ParsedArguments arguments,
                                   String argName) throws CommandSyntaxException {

        LandClaim claim = this.resolveFromArgument(arguments, argName);
        if (claim != null) {
            return claim;
        }

        Player player = context.getPlayer();
        if (player == null) {
            throw CommandSyntaxException.custom(CoreLang.COMMAND_EXECUTION_PLAYER_ONLY);
        }

        return this.resolveAtLocation(player);
    }

    /**
     * Resolves a LandClaim from the argument, falling back to the player's location.
     * Throws an exception if no claim can be found, halting the command automatically.
     */
    public LandClaim resolveAtLocation(Player player) throws CommandSyntaxException {
        Location location = player.getLocation();
        if (location == null) {
            throw CommandSyntaxException.custom(CoreLang.COMMAND_EXECUTION_PLAYER_ONLY);
        }

        LandClaim claim = this.repository.getPrioritizedClaim(location);
        if (claim == null) {
            throw CommandSyntaxException.custom(LandLang.ERROR_NO_LAND_IN_LOCATION);
        }

        return claim;
    }
}