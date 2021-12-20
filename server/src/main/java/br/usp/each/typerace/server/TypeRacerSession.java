package br.usp.each.typerace.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeRacerSession {
    private Map<String, Player> players;
    private Set<String> words;
    private boolean gameStarted;
    private static final int maxScore = 3;
    private static long startGameTime;
    private static long endGameTime;

    public Set<String> getWords() {
        return words;
    }

    public TypeRacerSession() {
        this.players = new HashMap<String, Player>();
        this.words = WordList.getWordList(maxScore);
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

    public boolean verifyAnswer(String answer, String playerId) {
        return players.get(playerId).checkWord(answer);
    }

    public boolean isThereAWinner() {
        for (Player p : players.values()) {
            if (p.getCorrectWords() == maxScore) {
                return true;
            }
        }
        return false;
    }

    public String getPlayerAvaiableWords(String playerId) {
        return players.get(playerId).getAvaiableWords();
    }

    public void startGameSession() {
        setGameStarted(true);
        startGameTime = System.currentTimeMillis();
    }

    public void endGameSession() {
        setGameStarted(false);
        endGameTime = System.currentTimeMillis();
    }

    public long getGameTime() {
        return endGameTime - startGameTime;
    }

    public String getScoreBoard() {
        List<Player> sortedPlayers = new ArrayList<Player>(players.values());
        Collections.sort(sortedPlayers, new PlayerComparator());

        StringBuilder scoreBoard = new StringBuilder();
        scoreBoard.append(String.format(("+-------+---------------+-------+-----+%n")));
        scoreBoard.append(String.format(("|             PLACAR FINAL            |%n")));
        scoreBoard.append(String.format(("+-------+---------------+-------+-----+%n")));
        scoreBoard.append(String.format(("|Posicao|     Nome      |Acertos|Erros|%n")));
        scoreBoard.append(String.format(("+-------+---------------+-------+-----+%n")));

        int i = 1;
        for (Player p : sortedPlayers) {
            scoreBoard.append(p.toString(i++));
        }
        scoreBoard.append(String.format(("+-------+---------------+-------+-----+%n")));

        long now = getGameTime();
        long minutos = now / 60000;
        long segundos = (now % 60000) / 1000;
        scoreBoard
                .append(String.format("|%-37s|%n", "Tempo total: " + minutos + " minutos e " + segundos + " segundos"));
        scoreBoard.append(String.format(("+-------------------------------------+%n")));
        return scoreBoard.toString();
    }

    public String getAvaiableWords() {
        StringBuilder avaiableWords = new StringBuilder();

        avaiableWords.append(String.format(("+-------------------+%n")));
        avaiableWords.append(String.format(("|Palavras da Partida|%n")));
        avaiableWords.append(String.format(("+-------------------+%n")));
        String leftAlignFormat = "|%-19s|%n";

        for (String w : words) {
            avaiableWords.append(String.format(leftAlignFormat, w));
        }
        avaiableWords.append(String.format(("+-------------------+%n")));
        return avaiableWords.toString();
    }
}
