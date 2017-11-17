package pw.cinque.hcf.config;

import org.bukkit.ChatColor;

import java.io.File;

public class Lang extends Config {

    public Lang(File file) {
        super(file);
    }

    public String getMessage(String key, String... placeholders) {
        String message = getValue(key);

        if (message == null) {
            return null;
        }

        for (int i = 0; i < placeholders.length; i++) {
            message = message.replace("{" + i + "}", placeholders[i]);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
