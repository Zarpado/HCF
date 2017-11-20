package pw.cinque.hcf;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface FactionPlayer {

    /**
     * Gets the unique identifier of the player
     *
     * @return The {@link UUID} of the associated {@link Player}
     */
    UUID getUniqueId();

    /**
     * Gets the player's name
     *
     * @return The name of the associated {@link Player}
     */
    String getName();

    /**
     * Gets the {@link Player} for this player
     *
     * @return The {@link Player}, or null if the player is offline
     */
    Player getPlayer();

    /**
     * Gets the player's faction
     *
     * @return The {@link Faction} the player is currently in, or null if the player isn't a member of any faction
     */
    Faction getFaction();

    /**
     * Sets the player's faction
     *
     * @param faction The new {@link Faction} to put the player in
     */
    void setFaction(Faction faction);

    /**
     * Gets the player's role
     *
     * @return The {@link FactionRole} of the player inside his faction, or null if the player isn't a member of any faction
     */
    FactionRole getRole();

    /**
     * Sets the player's role
     *
     * @param role The new {@link FactionRole} to add to the player
     */
    void setRole(FactionRole role);

}
