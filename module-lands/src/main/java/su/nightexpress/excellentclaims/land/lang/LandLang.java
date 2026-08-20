package su.nightexpress.excellentclaims.land.lang;

import org.bukkit.Sound;

import su.nightexpress.excellentclaims.api.ClaimsPlaceholders;
import su.nightexpress.excellentclaims.land.LandsPlaceholders;
import su.nightexpress.nightcore.locale.LangContainer;
import su.nightexpress.nightcore.locale.LangEntry;
import su.nightexpress.nightcore.locale.entry.IconLocale;
import su.nightexpress.nightcore.locale.entry.MessageLocale;
import su.nightexpress.nightcore.locale.entry.TextLocale;
import su.nightexpress.nightcore.util.placeholder.CommonPlaceholders;
import su.nightexpress.nightcore.util.text.night.wrapper.TagWrappers;

public class LandLang implements LangContainer {

    public static final TextLocale COMMAND_ARGUMENT_NAME_LAND = LangEntry.builder("Command.Argument.Name.Land")
        .text("land");

    public static final TextLocale COMMAND_LAND_DESC = LangEntry.builder("Lands.Command.Land.Desc")
        .text("Land claim commands.");

    public static final TextLocale COMMAND_LAND_CREATE_DESC = LangEntry.builder("Lands.Command.Land.Create.Desc")
        .text("Create new land claim.");

    public static final TextLocale COMMAND_LAND_REMOVE_DESC = LangEntry.builder("Lands.Command.Land.Remove.Desc")
        .text("Remove land claim.");

    public static final TextLocale COMMAND_LAND_CLAIM_DESC = LangEntry.builder("Lands.Command.Land.Claim.Desc")
        .text("Claim a chunk to a land claim.");

    public static final TextLocale COMMAND_LAND_UNCLAIM_DESC = LangEntry.builder("Lands.Command.Land.Unclaim.Desc")
        .text("Unclaim a chunk from a land claim.");

    public static final TextLocale COMMAND_LAND_RULES_DESC = LangEntry.builder("Lands.Command.Land.Rules.Desc")
        .text("Open Rules GUI.");

    public static final TextLocale COMMAND_LAND_MEMBERS_DESC = LangEntry.builder("Lands.Command.Land.Members.Desc")
        .text("Open Members GUI.");

    public static final TextLocale COMMAND_LAND_MENU_DESC = LangEntry.builder("Lands.Command.Land.Menu.Desc")
        .text("Open Land GUI.");

    public static final TextLocale COMMAND_LAND_LIST_DESC = LangEntry.builder("Lands.Command.Land.List.Desc")
        .text("Open GUI with your claims.");

    public static final TextLocale COMMAND_LAND_INSPECT_DESC = LangEntry.builder("Lands.Command.Land.Inspect.Desc")
        .text("Inspect player's claims.");

    public static final TextLocale COMMAND_LAND_SET_HOME_DESC = LangEntry.builder("Lands.Command.Land.SetHome.Desc")
        .text("Set claim home.");

    public static final TextLocale COMMAND_LAND_BORDERS_DESC = LangEntry.builder("Lands.Command.Land.Borders.Desc")
        .text("Toggle claim borders.");

    public static final TextLocale COMMAND_LAND_SET_NAME_DESC = LangEntry.builder("Lands.Command.Land.Rename.Desc")
        .text("Rename claim.");

    public static final TextLocale COMMAND_LAND_SET_DESCRIPTION_DESC = LangEntry
        .builder("Lands.Command.Land.Description.Desc")
        .text("Set claim description.");

    public static final TextLocale COMMAND_LAND_SET_PRIORITY_DESC = LangEntry.builder(
        "Lands.Command.Land.SetPriority.Desc")
        .text("Set claim priority.");

    public static final MessageLocale COMMAND_SYNTAX_INVALID_LAND_CLAIM = LangEntry
        .builder("Lands.Command.Syntax.InvalidLandClaim")
        .chatMessage(
            TagWrappers.GRAY.wrap(TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_INPUT) +
                " is not a valid land claim!")
        );

    public static final MessageLocale ERROR_NO_LAND_IN_LOCATION = LangEntry
        .builder("Lands.Error.NoLandClaimInLocation")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("There are no land claim(s) at your location.")
        );

    public static final MessageLocale ERROR_LAND_DISABLED = LangEntry.builder("Lands.Error.LandDisabled")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("The claim was disabled because the world it belongs to was unloaded.")
        );

    public static final MessageLocale LAND_INSPECT_EMPTY = LangEntry.builder("Lands.Inspect.Empty")
        .chatMessage(
            TagWrappers.GRAY.wrap("Player " + TagWrappers.RED.wrap(CommonPlaceholders.PLAYER_NAME) +
                " is not involved in any claim as an owner or a member.")
        );

    public static final MessageLocale LAND_TELEPORT_SUCCESS = LangEntry.builder("Lands.Teleport.Success")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have been teleported to the " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_NAME) + " spawn point.")
        );

    public static final MessageLocale LAND_TELEPORT_CANCELLED = LangEntry.builder("Lands.Teleport.Cancelled")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("The teleport was interrupted.")
        );

    public static final MessageLocale LAND_BOUNDS_ENABLED = LangEntry.builder("Lands.Borders.Enabled")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have toggled chunk borders " + TagWrappers.GREEN.wrap("ON") + ".")
        );

    public static final MessageLocale LAND_BOUNDS_DISABLED = LangEntry.builder("Lands.Borders.Disabled")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have toggled chunk borders " + TagWrappers.RED.wrap("OFF") + ".")
        );

    public static final MessageLocale CREATION_SUCCESS = LangEntry.builder("Lands.Creation.Success")
        .chatMessage(
            Sound.ENTITY_PLAYER_LEVELUP,
            TagWrappers.GRAY.wrap("You have successfully created a new claim with name " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_NAME) + "!"),
            "",
            "Use " + TagWrappers.GREEN.wrap("/land claim") + " to claim chunks for your new land claim."
        );

    public static final MessageLocale CREATION_ALREADY_EXISTS = LangEntry.builder("Lands.Creation.AlreadyExists")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("There is already a claim with this name.")
        );

    public static final MessageLocale CREATION_DENIED = LangEntry.builder("Lands.Creation.Denied")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("A claim creation was denied by an external factor.")
        );

    public static final MessageLocale CLAIM_REMOVAL_SUCCESS = LangEntry.builder("Lands.Claim.Removal.Success")
        .chatMessage(
            Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
            TagWrappers.GRAY.wrap("You have successfully removed " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_NAME) + " claim!")
        );

    public static final MessageLocale CLAIM_REMOVAL_DENIED = LangEntry.builder("Lands.Claim.Removal.Denied")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("A claim removal was denied by an external factor.")
        );

    public static final MessageLocale CLAIMING_SUCCESS = LangEntry.builder("Lands.Claim.Success")
        .chatMessage(
            Sound.ENTITY_PLAYER_LEVELUP,
            TagWrappers.GRAY.wrap("You have successfully claimed a chunk for your " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_NAME) + " claim!")
        );

    public static final MessageLocale CLAIMING_BAD_WORLD = LangEntry.builder("Lands.Claim.BadWorld")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't claim chunks here.")
        );

    public static final MessageLocale CLAIMING_NO_LANDS_CREATED = LangEntry.builder("Lands.Claim.NoLandsCreated")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You don't have any land claims created yet. Use " +
                TagWrappers.RED.wrap("/land create") + " to create your first land claim.")
        );

    public static final MessageLocale CLAIMING_MULTIPLE_LANDS_IN_LOCATION = LangEntry
        .builder("Lands.Claim.MultipleLandClaimsInLocation")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("There are multiple land claims at your location. Please specify the claim name: " +
                TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_NAME) + ".")
        );

    public static final MessageLocale CLAIMING_DIFFERENT_WORLD = LangEntry.builder("Lands.Claim.DifferentWorld")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't claim chunks in a different world than your land claim: " +
                TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_NAME) + ".")
        );

    public static final MessageLocale CLAIMING_CHUNK_NOT_ADJACENT = LangEntry.builder("Lands.Claim.ChunkNotAdjacent")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can only claim chunks that are adjacent to your existing claim(s).")
        );

    public static final MessageLocale CLAIMING_INVALID_NAME = LangEntry.builder("Lands.Claim.InvalidName")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't use this name for your claim.")
        );

    public static final MessageLocale CLAIMING_ALREADY_OCCUPIED = LangEntry.builder("Lands.Claim.AlreadyOccupied")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("This chunk is already claimed.")
        );

    public static final MessageLocale CLAIMING_ALREADY_CLAIMED = LangEntry
        .builder("Lands.Claim.AlreadyClaimed")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You already own this chunk in " +
                TagWrappers.RED.wrap(LandsPlaceholders.LAND_NAME) + " claim.")
        );

    public static final MessageLocale CREATION_QUOTA_EXCEEDED = LangEntry.builder("Lands.Creation.QuotaExceeded")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You already has maximum of " +
                TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_AMOUNT) + " claims created.")
        );

    public static final MessageLocale CLAIMING_CHUNK_QUOTA = LangEntry.builder("Lands.Claim.ChunkQuota")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You already has maximum of " +
                TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_AMOUNT) + " chunks claimed in your " +
                TagWrappers.RED.wrap(LandsPlaceholders.LAND_NAME) + " claim.")
        );

    public static final MessageLocale CLAIMING_OVERLAP_FORBIDDEN = LangEntry
        .builder("Lands.Claim.Overlap.Forbidden")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't claim chunks that overlaps with other claims.")
        );

    public static final MessageLocale CLAIMING_OVERLAP_INCOMPATIBLE_CLAIM = LangEntry
        .builder("Lands.Claim.Overlap.IncompatibleClaim")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("This chunk contains " + TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_NAME) +
                " claim that you are not allowed to overlap with.")
        );

    public static final MessageLocale CLAIMING_INSUFFICIENT_FUNDS = LangEntry
        .builder("Lands.Claim.InsufficientFunds")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You need " + TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_AMOUNT) +
                " to claim this chunk.")
        );

    public static final MessageLocale UNCLAIMING_SUCCESS = LangEntry.builder("Lands.Unclaim.Success")
        .chatMessage(
            Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
            TagWrappers.GRAY.wrap("You have successfully unclaimed this chunk.")
        );

    public static final MessageLocale UNCLAIMING_DIFFERENT_WORLD = LangEntry.builder("Lands.Unclaim.DifferentWorld")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't unclaim chunks in a different world than your land claim: " +
                TagWrappers.RED.wrap(CommonPlaceholders.GENERIC_NAME) + ".")
        );

    public static final MessageLocale UNCLAIMING_CHUNK_NOT_CLAIMED = LangEntry.builder("Lands.Unclaim.ChunkNotClaimed")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("This chunk is not claimed by your claim.")
        );

    public static final MessageLocale UNCLAIMING_LAST_CHUNK = LangEntry.builder("Lands.Unclaim.LastChunk")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("You can't unclaim this chunk because it is the last chunk of your claim.")
        );

    public static final MessageLocale UNCLAIMING_CHUNK_WOULD_DISCONNECT = LangEntry
        .builder("Lands.Unclaim.ChunkWouldDisconnect")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap(
                "You can't unclaim this chunk because it would disconnect your claim into multiple parts.")
        );

    public static final MessageLocale EDITOR_SET_NAME_SUCCESS = LangEntry.builder("Lands.Editor.SetName.Success")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have successfully changed name of your claim to " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_NAME) + "!")
        );

    public static final MessageLocale EDITOR_SET_NAME_TOO_LONG = LangEntry.builder("Lands.Editor.SetName.TooLong")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("Claim name can not be longer than " +
                TagWrappers.RED.wrap(ClaimsPlaceholders.GENERIC_MAX) + " characters!")
        );


    public static final MessageLocale EDITOR_SET_DESCRIPTION_SUCCESS = LangEntry
        .builder("Lands.Editor.SetDescription.Success")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have successfully changed description of your " +
                TagWrappers.WHITE.wrap(LandsPlaceholders.LAND_NAME) + " claim to:"),
            TagWrappers.GRAY.wrap("\"" + TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_DESCRIPTION) + "\"")
        );

    public static final MessageLocale EDITOR_SET_DESCRIPTION_TOO_LONG = LangEntry
        .builder("Lands.Editor.SetDescription.TooLong")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("Claim description can not be longer than " +
                TagWrappers.RED.wrap(ClaimsPlaceholders.GENERIC_MAX) + " characters!")
        );


    public static final MessageLocale EDITOR_SET_SPAWN_SUCCESS = LangEntry.builder("Lands.Editor.SetSpawn.Set")
        .chatMessage(
            Sound.BLOCK_WOODEN_DOOR_OPEN,
            TagWrappers.GRAY.wrap("You have successfully updated spawn location of your " +
                TagWrappers.WHITE.wrap(LandsPlaceholders.LAND_NAME) + " claim!")
        );

    public static final MessageLocale EDITOR_SET_SPAWN_OUTSIDE = LangEntry.builder("Lands.Editor.SetSpawn.OutSide")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("Claim spawn location must be within claim bounds!")
        );


    public static final MessageLocale EDITOR_SET_PRIORITY_SUCCESS = LangEntry.builder("Lands.Editor.Priority.Set")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have successfully updated priority of your " +
                TagWrappers.WHITE.wrap(LandsPlaceholders.LAND_NAME) + " claim to " +
                TagWrappers.GREEN.wrap(LandsPlaceholders.LAND_PRIORITY) + "!")
        );

    public static final MessageLocale EDITOR_PRIORITY_NOT_IN_BOUNDS = LangEntry
        .builder("Lands.Editor.Priority.NotInBounds")
        .chatMessage(
            Sound.ENTITY_VILLAGER_NO,
            TagWrappers.GRAY.wrap("Priority value must be between " +
                TagWrappers.RED.wrap(ClaimsPlaceholders.GENERIC_MIN) + " and " +
                TagWrappers.RED.wrap(ClaimsPlaceholders.GENERIC_MAX) + "!")
        );


    public static final MessageLocale EDITOR_SET_ICON_SUCCESS = LangEntry.builder("Lands.Editor.Icon.Set")
        .chatMessage(
            TagWrappers.GRAY.wrap("You have successfully updated icon of your " +
                TagWrappers.WHITE.wrap(LandsPlaceholders.LAND_NAME) + " claim!")
        );

    public static final IconLocale UI_EDITOR_MENU_ICON_BUTTON = LangEntry.iconBuilder("Lands.UI.Editor.Menu.IconButton")
        .accentColor(TagWrappers.GOLD)
        .name("Icon")
        .appendInfo("Represents the claim in GUIs.")
        .br()
        .appendClick("Click to edit")
        .build();
}
