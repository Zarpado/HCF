package pw.cinque.hcf.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.cinque.hcf.HCFPlugin;
import pw.cinque.hcf.command.impl.CommandCreate;
import pw.cinque.hcf.command.impl.CommandDisband;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionsCommandExecutor implements CommandExecutor {

    @Getter
    private static final FactionsCommandExecutor instance = new FactionsCommandExecutor();

    private List<SubCommand> subCommands = new ArrayList<>();

    private FactionsCommandExecutor() {
        this.subCommands.add(new CommandCreate());
        this.subCommands.add(new CommandDisband());
    }

    public void register() {
        HCFPlugin.getInstance().getCommand("factions").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            printHelp(sender);
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

    private void printHelp(CommandSender sender) {
        sender.sendMessage(HCFPlugin.getLang().getMessage("commands.help-title"));
        subCommands.forEach(subCommand -> sender.sendMessage(HCFPlugin.getLang().getMessage("commands.help-format", subCommand.getName(), subCommand.getDescription())));
    }

}
