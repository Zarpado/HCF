package pw.cinque.hcf.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.cinque.hcf.HCFPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Config {

    private Map<String, Object> values = new HashMap<>();

    public Config(File file) {
        YamlConfiguration defaults = new YamlConfiguration();
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            defaults.load(new InputStreamReader(HCFPlugin.getInstance().getResource(file.getName())));

            if (file.exists()) {
                configuration.load(file);
            }

            Predicate<String> isSet = configuration::isSet;
            defaults.getKeys(false).stream().filter(isSet.negate()).forEach(key -> configuration.set(key, defaults.get(key)));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configuration.getKeys(false).forEach(key -> checkKey(configuration, key));

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkKey(ConfigurationSection section, String key) {
        if (section.isConfigurationSection(key)) {
            ConfigurationSection subSection = section.getConfigurationSection(key);
            subSection.getKeys(false).forEach(subKey -> checkKey(subSection, subKey));
        } else {
            String valueName = section.getParent() == null ? key : (section.getCurrentPath() + "." + key);
            this.values.put(valueName, section.get(key));
        }
    }

    public <T> T getValue(String key) {
        return (T) values.get(key);
    }

}
