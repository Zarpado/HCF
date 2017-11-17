package pw.cinque.hcf;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pw.cinque.hcf.command.FactionsCommandExecutor;
import pw.cinque.hcf.lang.Lang;

import java.io.File;

public class HCFPlugin extends JavaPlugin {

    @Getter
    private static HCFPlugin instance;

    public HCFPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Lang.getInstance().load(new File(getDataFolder(), "lang.yml"));
        FactionsCommandExecutor.getInstance().register();
    }

}
