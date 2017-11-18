package pw.cinque.hcf;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pw.cinque.hcf.command.FactionsCommandExecutor;
import pw.cinque.hcf.config.Config;
import pw.cinque.hcf.config.Lang;

import java.io.File;

public class HCFPlugin extends JavaPlugin {

    @Getter
    private static HCFPlugin instance;
    @Getter
    private static Config settings;
    @Getter
    private static Lang lang;

    @Override
    public void onEnable() {
        instance = this;
        settings = new Config(new File(getDataFolder(), "config.yml"));
        lang = new Lang(new File(getDataFolder(), "lang.yml"));

        FactionsCommandExecutor.getInstance().register();
        FactionManager.getInstance().loadFactions(new File(getDataFolder(), "factions.yml"));

        startSaveTask();
    }

    @Override
    public void onDisable() {
        FactionManager.getInstance().saveFactions(new File(getDataFolder(), "factions.yml"));
    }

    private void startSaveTask() {
        int saveInterval = settings.getValue("save-interval");

        new BukkitRunnable() {

            @Override
            public void run() {
                FactionManager.getInstance().saveFactions(new File(getDataFolder(), "factions.yml"));
            }

        }.runTaskTimer(this, saveInterval * 20L, saveInterval * 20L);
    }

}
