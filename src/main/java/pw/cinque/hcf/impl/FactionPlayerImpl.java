package pw.cinque.hcf.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pw.cinque.hcf.Faction;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class FactionPlayerImpl implements FactionPlayer {

    private final UUID uniqueId;
    private final String name;
    private Faction faction;
    private FactionRole role;

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

}
