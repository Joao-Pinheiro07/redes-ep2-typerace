package br.usp.each.typerace.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TypeRacerSession {
    private List<Player> players;
    private Set<String> words;
    private boolean gameStarted;
    private static final int maxScore = 10;

    public Set<String> getWords() {
        return words;
    }

    public TypeRacerSession() {
        this.players = new ArrayList<Player>();
        this.words = WordList.getWordList();
        this.gameStarted = false;
    }

    public void addPlayerToSession(String playerId) {
        this.players.add(new Player(playerId));
    }

    public void removePlayerFromSessionById(String playerId) {
        this.players.removeIf(p -> p.getId() == playerId);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void loadWordListToAllPlayers() {
        for (Player p : players) {
            p.setWords(words);
        }
    }

    public long changeGameSession(boolean status) {
        setGameStarted(status);
        return System.currentTimeMillis();
    }

    public boolean isThereAWinner() {
        for (Player p : players) {
            if (p.getCorrectWords() == maxScore) {
                System.out.println(p.getId() + "ganhou!");
                return true;
            }
        }
        return false;
    }
}
