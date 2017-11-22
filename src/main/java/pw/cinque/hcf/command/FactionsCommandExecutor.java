package pw.cinque.hcf.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.command.impl.CommandCreate;
import pw.cinque.hcf.command.impl.CommandDisband;
import pw.cinque.hcf.command.impl.CommandInvite;
import pw.cinque.hcf.command.impl.CommandJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionsCommandExecutor implements CommandExecutor {

    private List<SubCommand> subCommands = new ArrayList<>();

    public FactionsCommandExecutor() {
        this.subCommands.add(new CommandCreate());
        this.subCommands.add(new CommandDisband());
        this.subCommands.add(new CommandInvite());
        this.subCommands.add(new CommandJoin());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.help-title"));
            subCommands.forEach(subCommand -> sender.sendMessage(HCFPlugin.getLang().getMessage("commands.help-format", subCommand.getName(), subCommand.getDescription())));
            return true;
        }

        String subCommandName = args[0];
        SubCommand subCommand = subCommands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(subCommandName)).findFirst().orElse(null);

        if (subCommand == null) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.unknown", subCommandName));
            return true;
        }

        if (subCommand.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage(HCFPlugin.getLang().getMessage("commands.player-only"));
            return true;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.onCommand(sender, subArgs);

        return true;
    }

}
