package pw.cinque.hcf;

import com.google.common.collect.MapMaker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FactionPlayer {

    private static Map<UUID, FactionPlayer> players = new MapMaker().weakValues().makeMap();

    @Getter
    private final UUID uniqueId;
    @Getter
    private final String name;
    @Setter(value = AccessLevel.PACKAGE)
    private Faction faction;
    @Setter
    private FactionRole role;

    /**
     * Gets the {@link FactionPlayer} for a certain {@link Player}
     *
     * @param player The {@link Player}
     * @return The {@link FactionPlayer}
     */
    public static FactionPlayer fromPlayer(Player player) {
        return fromOfflinePlayer(player.getUniqueId(), player.getName());
    }

    /**
     * Gets the {@link FactionPlayer} for a a certain UUID and username
     *
     * @param uniqueId The player's UUID
     * @param name     The player's username
     * @return The {@link FactionPlayer}
     */
    public static FactionPlayer fromOfflinePlayer(UUID uniqueId, String name) {
        if (!players.containsKey(uniqueId)) {
            FactionPlayer fplayer = new FactionPlayer(uniqueId, name);
            players.put(uniqueId, fplayer);

            return fplayer;
        }

        return players.get(uniqueId);
    }

    /**
     * Gets the {@link Player} for this player
     *
     * @return The {@link Player}, or null if the player is offline
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    /**
     * Gets the player's faction
     *
     * @return The {@link Faction} the player is currently in, or null if the player isn't a member of any faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Gets the player's role
     *
     * @return The {@link FactionRole} of the player inside his faction, or null if the player isn't a member of any faction
     */
    public FactionRole getRole() {
        return role;
    }

}
