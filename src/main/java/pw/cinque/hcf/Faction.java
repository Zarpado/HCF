package pw.cinque.hcf;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Faction {

    @Getter
    private final String name;

    private Set<FactionPlayer> members = new HashSet<>();

    /**
     * Gets all members in a faction
     *
     * @return A set containing all {@link FactionPlayer}'s in this faction
     */
    public Set<FactionPlayer> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Adds a player to the faction
     *
     * @param player The {@link FactionPlayer} to add to the faction
     */
    public void addMember(FactionPlayer player) {
        this.members.add(player);
        player.setFaction(this);
        player.setRole(FactionRole.MEMBER);
    }

    /**
     * Removes a player from the faction
     *
     * @param player The {@link FactionPlayer} to remove from the faction
     */
    public void removeMember(FactionPlayer player) {
        this.members.remove(player);
        player.setFaction(null);
        player.setRole(null);
    }

}
