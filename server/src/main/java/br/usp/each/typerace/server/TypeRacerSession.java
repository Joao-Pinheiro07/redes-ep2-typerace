package br.usp.each.typerace.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeRacerSession {
    private Map<String, Player> players;
    private Set<String> words;
    private boolean gameStarted;
    private static final int maxScore = 10;

    public Set<String> getWords() {
        return words;
    }

    public TypeRacerSession() {
        this.players = new HashMap<String, Player>();
        this.words = WordList.getWordList();
        this.gameStarted = false;
    }

    public void addPlayerToSession(String playerId) {
        this.players.put(playerId, new Player(playerId));

    }

    public void removePlayerFromSessionById(String playerId) {
        this.players.remove(playerId);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void loadWordListToAllPlayers() {
        for (Player p : players.values()) {
            p.setWords(new HashSet<String>(words));
        }
    }

    public long changeGameSession(boolean status) {
        setGameStarted(status);
        return System.currentTimeMillis();
    }

    public void verifyAnswer(String answer, String playerId) {
        Player player = players.get(playerId);
        player.checkWord(answer);
    }

    public boolean verifyAnswerAndCheckIfIsThereAWinner(String answer, String playerId) {
        Player player = players.get(playerId);
        player.checkWord(answer);
        if (player.getCorrectWords() == maxScore)
            return true;
        return false;

    }

    public boolean isThereAWinner() {
        for (Player p : players.values()) {
            if (p.getCorrectWords() == maxScore) {
                System.out.println(p.getId() + "ganhou!");
                return true;
            }
        }
        return false;
    }

    public String getPlayerAvaiableWords(String playerId) {
        return players.get(playerId).getWords().toString();
    }
}
