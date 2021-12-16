package br.usp.each.typerace.server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeRacerSession {
    private Set<Player> players;
    private List<String> words;

    public TypeRacerSession() {
        this.players = new HashSet<Player>();
    }

    public void addPlayerToSession(String playerId) {
        this.players.add(new Player(playerId));
    }

    public void removePlayerFromSessionById(String playerId) {
        this.players.removeIf(p -> p.getId() == playerId);
    }

}
