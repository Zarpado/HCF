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

            defaults.getKeys(false).forEach(key -> checkDefaultKey(defaults, configuration, key));
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

    private void checkDefaultKey(ConfigurationSection section, YamlConfiguration configuration, String key) {
        if (section.isConfigurationSection(key)) {
            ConfigurationSection subSection = section.getConfigurationSection(key);
            subSection.getKeys(false).forEach(subKey -> checkDefaultKey(subSection, configuration, subKey));
        } else {
            String path = section.getParent() == null ? key : (section.getCurrentPath() + "." + key);
            configuration.set(path, section.get(key));
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
