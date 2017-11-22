package pw.cinque.hcf.impl;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pw.cinque.hcf.Faction;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.HCFPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

@RequiredArgsConstructor
public class FactionImpl implements Faction {

    @Getter
    private final String name;

    private Set<FactionPlayer> members = new HashSet<>();
    private Map<FactionPlayer, Long> invitedPlayers = new WeakHashMap<>();

    public Set<FactionPlayer> getMembers() {
        return ImmutableSet.copyOf(members);
    }

    public void addMember(FactionPlayer player) {
        if (invitedPlayers.containsKey(player)) {
            this.invitedPlayers.remove(player);
        }

        this.members.add(player);
        player.setFaction(this);
    }

    public void removeMember(FactionPlayer player) {
        this.members.remove(player);
        player.setFaction(null);
        player.setRole(null);
    }

    public void invitePlayer(FactionPlayer player) {
        int expirationTime = HCFPlugin.getSettings().getValue("invite-expiration-time");
        this.invitedPlayers.put(player, System.currentTimeMillis() + expirationTime * 1000L);
    }

    public boolean isInvited(FactionPlayer player) {
        return invitedPlayers.getOrDefault(player, 0L) > System.currentTimeMillis();
    }

}
