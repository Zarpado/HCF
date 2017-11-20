package pw.cinque.hcf;

import com.google.common.collect.MapMaker;
import lombok.Getter;
import org.bukkit.entity.Player;
import pw.cinque.hcf.impl.FactionPlayerImpl;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    @Getter
    private static final PlayerManager instance = new PlayerManager();

    private Map<UUID, FactionPlayer> players = new MapMaker().weakValues().makeMap();

    private PlayerManager() {
    }

    /**
     * Gets the {@link FactionPlayer} for a certain {@link Player}
     *
     * @param player The {@link Player}
     * @return The {@link FactionPlayer}
     */
    public FactionPlayer getFactionPlayer(Player player) {
        return getFactionPlayer(player.getUniqueId(), player.getName());
    }

    /**
     * Gets the {@link FactionPlayer} for a a certain UUID and username
     *
     * @param uniqueId The player's UUID
     * @param name     The player's username
     * @return The {@link FactionPlayer}
     */
    public FactionPlayer getFactionPlayer(UUID uniqueId, String name) {
        return players.computeIfAbsent(uniqueId, key -> new FactionPlayerImpl(uniqueId, name));
    }

}
