package pw.cinque.hcf.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public abstract class SubCommand {

    private final String name;
    private final String description;
    private final boolean playerOnly;

    protected abstract void onCommand(CommandSender sender, String[] args);

}
