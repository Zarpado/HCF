package pw.cinque.hcf.command.impl;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.command.SubCommand;
import pw.cinque.hcf.faction.Faction;
import pw.cinque.hcf.faction.FactionManager;
import pw.cinque.hcf.faction.FactionPlayer;
import pw.cinque.hcf.faction.FactionRole;

public class CommandCreate extends SubCommand {

    public CommandCreate() {
        super("create", "Creates a new faction", true);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(HCFPlugin.getLang().getMessage("command-invalid-args", "/f create <faction name>"));
            return;
        }

        String factionName = args[0];

        if (!StringUtils.isAlphanumeric(factionName)) {
            player.sendMessage(HCFPlugin.getLang().getMessage("faction-name-not-alphanumeric"));
            return;
        }

        int minLength = HCFPlugin.getSettings().getValue("faction-name-min-length");
        int maxLength = HCFPlugin.getSettings().getValue("faction-name-max-length");

        if (factionName.length() < minLength) {
            player.sendMessage(HCFPlugin.getLang().getMessage("faction-name-too-short", String.valueOf(minLength)));
            return;
        }

        if (factionName.length() > maxLength) {
            player.sendMessage(HCFPlugin.getLang().getMessage("faction-name-too-long", String.valueOf(maxLength)));
            return;
        }

        if (FactionManager.getInstance().getFactionByName(factionName) != null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("faction-name-taken", factionName));
            return;
        }

        FactionPlayer fplayer = FactionPlayer.fromPlayer(player);

        if (fplayer.getFaction() != null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("faction-already-member"));
            return;
        }

        Faction faction = new Faction(factionName);

        faction.addMember(fplayer);
        fplayer.setRole(FactionRole.OWNER);

        FactionManager.getInstance().register(faction);
        player.sendMessage(HCFPlugin.getLang().getMessage("faction-created", factionName));
    }

}
