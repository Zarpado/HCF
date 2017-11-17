package pw.cinque.hcf.faction;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FactionManager {

    @Getter
    private static final FactionManager instance = new FactionManager();

    private Set<Faction> factions = new HashSet<>();

    public void loadFactions() {
        // TODO: load factions from disk
    }

    public void saveFactions() {
        // TODO: save factions to disk
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
