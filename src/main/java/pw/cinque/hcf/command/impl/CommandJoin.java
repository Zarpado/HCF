package pw.cinque.hcf.command.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.FactionManager;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.PlayerManager;
import pw.cinque.hcf.command.SubCommand;
import pw.cinque.hcf.impl.FactionImpl;

public class CommandJoin extends SubCommand {

    public CommandJoin() {
        super("join", "Join another player's faction", true);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.invalid-usage", "/f join <faction name>"));
            return;
        }

        FactionImpl faction = (FactionImpl) FactionManager.getInstance().getFactionByName(args[0]);

        if (faction == null) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("factions.not-found", args[0]));
            return;
        }

        FactionPlayer fplayer = PlayerManager.getInstance().getFactionPlayer((Player) sender);

        if (fplayer.getFaction() != null) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("factions.already-member"));
            return;
        }

        if (!faction.isInvited(fplayer)) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("factions.not-invited", faction.getName()));
            return;
        }

        String joinMessage = HCFPlugin.getLang().getMessage("factions.joined", fplayer.getName());

        fplayer.setRole(FactionRole.MEMBER);

        faction.addMember(fplayer);
        faction.getMembers().forEach(member -> {

            Player player = member.getPlayer();

            if (player != null) {
                member.getPlayer().sendMessage(joinMessage);
            }

        });
    }

}
