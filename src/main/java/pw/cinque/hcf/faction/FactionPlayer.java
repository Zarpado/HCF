package pw.cinque.hcf.faction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FactionPlayer {

    private static Map<Player, FactionPlayer> players = new WeakHashMap<>();

    @Getter
    private final UUID uniqueId;
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
        if (!players.containsKey(player)) {
            players.put(player, new FactionPlayer(player.getUniqueId()));
        }

        return players.get(player);
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
