package net.gcnt.skywarsreloaded.command.kits;

import net.gcnt.skywarsreloaded.SkyWarsReloaded;
import net.gcnt.skywarsreloaded.command.Cmd;
import net.gcnt.skywarsreloaded.data.player.PlayerStat;
import net.gcnt.skywarsreloaded.game.kits.SWKit;
import net.gcnt.skywarsreloaded.wrapper.SWCommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetKitRequirementsCmd extends Cmd {

    public SetKitRequirementsCmd(SkyWarsReloaded plugin) {
        super(plugin, "skywarskit", "requirements", "skywars.command.kit.requirements", false, "<kit> <requirement> <value>", "Manage the kit's requirements.");
    }

    @Override
    public boolean run(SWCommandSender sender, String[] args) {
        final StringBuilder options = new StringBuilder();
        options.append("&7").append("permission").append("&f, ");
        options.append("&7").append("cost").append("&f, ");
        options.append("&7").append("level").append("&f, ");
        options.append("&7").append("experience").append("&f, ");
        options.append("&7").append("stats");
        final String optionVal = options.toString();

        final StringBuilder statOptions = new StringBuilder();
        for (int i = 0; i < PlayerStat.values().length; i++) {
            statOptions.append("&7").append(PlayerStat.values()[i].getProperty());
            if (i != PlayerStat.values().length - 1) {
                statOptions.append("&f, ");
            }
        }
        final String statOptionVal = statOptions.toString();

        if (args.length == 0) {
            sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a kit name."));
            return true;
        } else if (args.length == 1) {
            sender.sendMessage(plugin.getUtils().colorize(String.format("&cPlease enter the kit requirement you want to change. Options: %s", optionVal)));
            return true;
        } else if (args.length == 2) {
            sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a kit requirement value."));
            return true;
        }

        String requirement = args[1].toLowerCase();
        String value;
        boolean checkInt = false;
        switch (requirement) {
            case "cost":
            case "level":
            case "experience":
                // value entered, checking if it's an int.
                value = args[2];
                checkInt = true;
                break;
            case "permission":
                value = args[2].toLowerCase();
                if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                    sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a valid kit requirement value (boolean)"));
                    return true;
                }
                break;
            case "stats":
                if (args.length == 3) {
                    sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a kit requirement value."));
                    return true;
                } else {
                    requirement = args[2];
                    value = args[3];
                    checkInt = true;

                    PlayerStat stat = PlayerStat.fromString(requirement);
                    if (stat == null) {
                        sender.sendMessage(plugin.getUtils().colorize(String.format("&cPlease enter a valid kit stat requirement. Options: &7%s", statOptionVal)));
                        return true;
                    }
                }
                break;
            default:
                sender.sendMessage(plugin.getUtils().colorize(String.format("&cPlease enter a valid kit requirement. Options: &7%s", optionVal)));
                return true;
        }

        if (checkInt) {
            if (!plugin.getUtils().isInt(value)) {
                sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a valid kit requirement value (number)."));
                return true;
            }
            int index = Integer.parseInt(value);
            if (index < 0) {
                sender.sendMessage(plugin.getUtils().colorize("&cPlease enter a valid kit requirement value (0 or greater)"));
                return true;
            }
        }

        final String kitName = args[0];
        SWKit kit = plugin.getKitManager().getKitByName(kitName);
        if (kit == null) {
            sender.sendMessage(plugin.getUtils().colorize("&cThere is no kit with that name."));
            return true;
        }

        switch (requirement) {
            case "cost" -> kit.getRequirements().setCost(Integer.parseInt(value));
            case "level" -> kit.getRequirements().setLevel(Integer.parseInt(value));
            case "experience" -> kit.getRequirements().setExperience(Integer.parseInt(value));
            case "permission" -> kit.getRequirements().setRequirePermission(Boolean.parseBoolean(value));
            default -> {
                kit.getRequirements().addMinimumStat(PlayerStat.fromString(requirement), Integer.parseInt(value));
            }
        }

        kit.saveData();
        sender.sendMessage(plugin.getUtils().colorize("&aThe &e" + requirement + "&a kit requirement of the kit &e" + kitName + " &ahas been changed to &e" + value + "&a!"));
        return true;
    }
}