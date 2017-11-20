package pw.cinque.hcf;

import java.util.Set;

public interface Faction {

    /**
     * Gets the faction's name
     *
     * @return The name of the {@link Faction}
     */
    String getName();

    /**
     * Gets all members in a faction
     *
     * @return A set containing all {@link FactionPlayer}'s in this faction
     */
    Set<FactionPlayer> getMembers();

    /**
     * Adds a player to the faction
     *
     * @param player The {@link FactionPlayer} to add to the faction
     */
    void addMember(FactionPlayer player);

    /**
     * Removes a player from the faction
     *
     * @param player The {@link FactionPlayer} to remove from the faction
     */
    void removeMember(FactionPlayer player);

}
