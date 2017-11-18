package pw.cinque.hcf.command.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.Faction;
import pw.cinque.hcf.FactionManager;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.command.SubCommand;

public class CommandDisband extends SubCommand {

    public CommandDisband() {
        super("disband", "Disbands your faction", false);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        Faction faction;

        if (args.length != 0) {
            if (!sender.hasPermission("hcf.disbandothers")) {
                sender.sendMessage(HCFPlugin.getLang().getMessage("commands.no-permission", "disband other factions"));
                return;
            }

            if ((faction = FactionManager.getInstance().getFactionByName(args[0])) == null) {
                sender.sendMessage(HCFPlugin.getLang().getMessage("factions.not-found", args[0]));
                return;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.invalid-args", "/f disband <faction name>"));
            return;
        } else {
            FactionPlayer fplayer = FactionPlayer.fromPlayer((Player) sender);

            if ((faction = fplayer.getFaction()) == null) {
                sender.sendMessage(HCFPlugin.getLang().getMessage("factions.not-member"));
                return;
            }

            if (fplayer.getRole() != FactionRole.OWNER) {
                sender.sendMessage(HCFPlugin.getLang().getMessage("commands.no-permission", "disband your faction"));
                return;
            }
        }

        faction.getMembers().forEach(faction::removeMember);
        FactionManager.getInstance().unregister(faction);

        sender.sendMessage(HCFPlugin.getLang().getMessage("factions.disbanded", faction.getName()));
    }

}
