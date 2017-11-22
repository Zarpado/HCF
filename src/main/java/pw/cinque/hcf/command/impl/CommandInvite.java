package pw.cinque.hcf.command.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.PlayerManager;
import pw.cinque.hcf.command.SubCommand;
import pw.cinque.hcf.impl.FactionImpl;

public class CommandInvite extends SubCommand {

    public CommandInvite() {
        super("invite", "Invite a player to your faction", true);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.invalid-usage", "/f invite <player>"));
            return;
        }

        Player invited = Bukkit.getPlayerExact(args[0]);

        if (invited == null) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.player-not-found", args[0]));
            return;
        }

        Player player = (Player) sender;
        FactionPlayer fplayer = PlayerManager.getInstance().getFactionPlayer(player);
        FactionPlayer finvited = PlayerManager.getInstance().getFactionPlayer(invited);

        if (fplayer == finvited) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.invite-self"));
            return;
        }

        if (finvited.getFaction() != null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.already-member-other", invited.getName()));
            return;
        }

        if (fplayer.getFaction() == null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.not-member"));
            return;
        }

        if (fplayer.getRole() == FactionRole.MEMBER) {
            player.sendMessage(HCFPlugin.getLang().getMessage("commands.no-permission", "invite other players"));
            return;
        }

        FactionImpl faction = (FactionImpl) fplayer.getFaction();

        if (faction.isInvited(finvited)) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.invite-cooldown", invited.getName()));
            return;
        }

        faction.invitePlayer(finvited);

        player.sendMessage(HCFPlugin.getLang().getMessage("factions.invited", invited.getName()));
        invited.sendMessage(HCFPlugin.getLang().getMessage("factions.invited-other", player.getName(), faction.getName()));
    }
}
