package pw.cinque.hcf.impl;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pw.cinque.hcf.Faction;
import pw.cinque.hcf.FactionPlayer;
import pw.cinque.hcf.FactionRole;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class FactionImpl implements Faction {

    @Getter
    private final String name;

    private Set<FactionPlayer> members = new HashSet<>();

    public Set<FactionPlayer> getMembers() {
        return ImmutableSet.copyOf(members);
    }

    public void addMember(FactionPlayer player) {
        this.members.add(player);
        player.setFaction(this);
        player.setRole(FactionRole.MEMBER);
    }

    public void removeMember(FactionPlayer player) {
        this.members.remove(player);
        player.setFaction(null);
        player.setRole(null);
    }

}
