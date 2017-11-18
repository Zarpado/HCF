package pw.cinque.hcf.command.impl;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.command.SubCommand;
import pw.cinque.hcf.Faction;
import pw.cinque.hcf.FactionManager;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;

public class CommandCreate extends SubCommand {

    public CommandCreate() {
        super("create", "Creates a new faction", true);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(HCFPlugin.getLang().getMessage("commands.invalid-args", "/f create <faction name>"));
            return;
        }

        FactionPlayer fplayer = FactionPlayer.fromPlayer(player);

        if (fplayer.getFaction() != null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.already-member"));
            return;
        }

        String factionName = args[0];

        if (!StringUtils.isAlphanumeric(factionName)) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.name-not-alphanumeric"));
            return;
        }

        int minLength = HCFPlugin.getSettings().getValue("factions.name-min-length");
        int maxLength = HCFPlugin.getSettings().getValue("factions.name-max-length");

        if (factionName.length() < minLength) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.name-too-short", String.valueOf(minLength)));
            return;
        }

        if (factionName.length() > maxLength) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.name-too-long", String.valueOf(maxLength)));
            return;
        }

        if (FactionManager.getInstance().getFactionByName(factionName) != null) {
            player.sendMessage(HCFPlugin.getLang().getMessage("factions.name-taken", factionName));
            return;
        }

        Faction faction = new Faction(factionName);

        faction.addMember(fplayer);
        fplayer.setRole(FactionRole.OWNER);

        FactionManager.getInstance().register(faction);
        player.sendMessage(HCFPlugin.getLang().getMessage("factions.created", factionName));
    }

}
