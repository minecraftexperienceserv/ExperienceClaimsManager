package su.nightexpress.excellentclaims.land.claim.command;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import su.nightexpress.excellentclaims.api.MessageDispatcher;
import su.nightexpress.excellentclaims.api.command.CommandExtension;
import su.nightexpress.excellentclaims.api.service.FeedbackHandler;
import su.nightexpress.excellentclaims.land.claim.LandClaimService;
import su.nightexpress.excellentclaims.land.command.LandCommandSuggestions;
import su.nightexpress.excellentclaims.land.lang.LandLang;
import su.nightexpress.excellentclaims.land.permission.LandPerms;
import su.nightexpress.nightcore.commands.Arguments;
import su.nightexpress.nightcore.commands.Commands;
import su.nightexpress.nightcore.commands.context.CommandContext;
import su.nightexpress.nightcore.commands.context.ParsedArguments;
import su.nightexpress.nightcore.commands.tree.ExecutableNode;
import su.nightexpress.nightcore.core.config.CoreLang;

@NullMarked
public class CreateCommand implements CommandExtension, FeedbackHandler {

    private static final String ARG_NAME = "name";

    private final LandClaimService  claimService;
    private final MessageDispatcher dispatcher;

    public CreateCommand(LandCommandSuggestions suggestions,
                         LandClaimService claimService,
                         MessageDispatcher dispatcher) {
        this.claimService = claimService;
        this.dispatcher = dispatcher;
    }

    @Override
    public MessageDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public ExecutableNode createCommand() {
        return Commands.literal("create", builder -> builder
            .playerOnly()
            .description(LandLang.COMMAND_LAND_CREATE_DESC)
            .permission(LandPerms.COMMAND_LAND_CREATE)
            .withArguments(Arguments.string(ARG_NAME)
                .optional()
                .localized(CoreLang.COMMAND_ARGUMENT_NAME_NAME)
            )
            .executes(this::run)
        );
    }

    private boolean run(CommandContext context, ParsedArguments arguments) {
        Player player = context.getPlayerOrThrow();
        String name = arguments.contains(ARG_NAME) ? arguments.getString(ARG_NAME) : null;

        return this.handleFeedback(player, this.claimService.createClaim(player, name));
    }
}
