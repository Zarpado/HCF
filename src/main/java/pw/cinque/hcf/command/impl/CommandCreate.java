package pw.cinque.hcf.command.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.faction.Faction;
import pw.cinque.hcf.faction.FactionManager;
import pw.cinque.hcf.command.SubCommand;
import pw.cinque.hcf.faction.FactionPlayer;
import pw.cinque.hcf.faction.FactionRole;
import pw.cinque.hcf.lang.Lang;

public class CommandCreate extends SubCommand {

    public CommandCreate() {
        super("create", "Creates a new faction", true);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Lang.getInstance().getMessage("command-invalid-args", "/f create <faction name>"));
            return;
        }

        String factionName = args[0];

        if (FactionManager.getInstance().getFactionByName(factionName) != null) {
            player.sendMessage(Lang.getInstance().getMessage("faction-name-taken", factionName));
            return;
        }

        FactionPlayer fplayer = FactionPlayer.fromPlayer(player);
        Faction faction = new Faction(factionName);

        faction.addMember(fplayer);
        fplayer.setRole(FactionRole.OWNER);

        FactionManager.getInstance().register(faction);
        player.sendMessage(Lang.getInstance().getMessage("faction-created", factionName));
    }

}
