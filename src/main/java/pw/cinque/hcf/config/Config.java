package pw.cinque.hcf.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.cinque.hcf.HCFPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Object> values = new HashMap<>();

    public Config(File file) {
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            if (file.exists()) {
                configuration.load(file); // load from disk
            } else {
                configuration.load(new InputStreamReader(HCFPlugin.getInstance().getResource(file.getName()))); // use defaults
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configuration.getKeys(false).forEach(key -> values.put(key, configuration.get(key)));

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T getValue(String key) {
        return (T) values.get(key);
    }

}
