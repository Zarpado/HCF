package pw.cinque.hcf;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FactionManager {

    @Getter
    private static final FactionManager instance = new FactionManager();

    private Set<Faction> factions = new HashSet<>();

    private FactionManager() {
    }

    void loadFactions(File file) {
        if (!file.exists()) {
            HCFPlugin.getInstance().getLogger().info("Factions file doesn't exist, no factions loaded.");
            return;
        }

        YamlConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            HCFPlugin.getInstance().getLogger().severe("Failed to load factions from disk, disabling...");
            Bukkit.getPluginManager().disablePlugin(HCFPlugin.getInstance());
            return;
        }

        configuration.getKeys(false).forEach(factionName -> {

            Faction faction = new Faction(factionName);

            ConfigurationSection factionSection = configuration.getConfigurationSection(factionName);
            ConfigurationSection membersSection = factionSection.getConfigurationSection("members");

            membersSection.getKeys(false).forEach(uuid -> {

                UUID uniqueId = UUID.fromString(uuid);
                String name = membersSection.getString(uuid);

                faction.addMember(FactionPlayer.fromOfflinePlayer(uniqueId, name));

            });

            this.factions.add(faction);

        });

        HCFPlugin.getInstance().getLogger().info("Loaded " + factions.size() + " factions from disk.");
    }

    void saveFactions(File file) {
        YamlConfiguration configuration = new YamlConfiguration();

        this.factions.forEach(faction -> {

            ConfigurationSection factionSection = configuration.createSection(faction.getName());
            ConfigurationSection membersSection = factionSection.createSection("members");

            faction.getMembers().forEach(member -> membersSection.set(member.getUniqueId().toString(), member.getName()));

        });

        try {
            configuration.save(file);
            HCFPlugin.getInstance().getLogger().info("Saved " + factions.size() + " factions to disk.");
        } catch (IOException e) {
            e.printStackTrace();
            HCFPlugin.getInstance().getLogger().warning("Failed to save factions to disk!");
        }
    }

    /**
     * Gets all {@link Faction}'s
     *
     * @return A set containing all {@link Faction}'s
     */
    public Set<Faction> getAllFactions() {
        return Collections.unmodifiableSet(factions);
    }

    /**
     * Retrieves a {@link Faction} using it's name
     *
     * @param name Name of the faction to get
     * @return The {@link Faction} with the specified name, or null if no faction was found
     */
    public Faction getFactionByName(String name) {
        return factions.stream().filter(faction -> faction.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Registers a {@link Faction}
     *
     * @param faction Faction to register
     */
    public void register(Faction faction) {
        this.factions.add(faction);
    }

    /**
     * Unregisters a {@link Faction}
     *
     * @param faction Faction to unregister
     */
    public void unregister(Faction faction) {
        this.factions.remove(faction);
    }

}
