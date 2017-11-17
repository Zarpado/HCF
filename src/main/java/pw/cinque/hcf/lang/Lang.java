package pw.cinque.hcf.lang;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.cinque.hcf.HCFPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Lang {

    @Getter
    private static final Lang instance = new Lang();

    private Map<String, String> messages = new HashMap<>();

    private Lang() {
    }

    public void load(File langFile) {
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            if (langFile.exists()) {
                configuration.load(langFile); // load from disk
            } else {
                configuration.load(new InputStreamReader(HCFPlugin.getInstance().getResource("lang.yml"))); // use defaults
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configuration.getKeys(false).forEach(key -> messages.put(key, configuration.getString(key).replace('&', '\u00A7')));

        try {
            configuration.save(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a message linked to the specified key
     *
     * @param key          The key linked to the message
     * @param placeholders The values for the placeholders that should be set in the message
     * @return The message linked to the specified key, or null if no message was found
     */
    public String getMessage(String key, String... placeholders) {
        String message = messages.get(key);

        if (message == null) {
            return null;
        }

        for (int i = 0; i < placeholders.length; i++) {
            message = message.replace("{" + i + "}", placeholders[i]);
        }

        return message;
    }

}
