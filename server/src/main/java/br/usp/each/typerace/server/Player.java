package br.usp.each.typerace.server;

import java.util.Set;

public class Player {
    private String id;
    private int correctWords;
    private int wrongWords;
    private Set<String> words;

    public Player(String id) {
        this.id = id;
        this.correctWords = 0;
        this.wrongWords = 0;
    }

    public String getId() {
        return id;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public void incrementCorrectWords() {
        this.correctWords += 1;
    }

    public int getWrongWords() {
        return wrongWords;
    }

    public void incrementWrongWords() {
        this.wrongWords += 1;
    }

    public boolean checkWord(String answer) {
        if (words.contains(answer)) {
            incrementCorrectWords();
            words.remove(answer);
            return true;
        } else {
            incrementWrongWords();
            return false;
        }
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }

    public String toString(int index) {
        String leftAlignFormat = "|%-7d|%-15s|%-7s|%-5s|%n";
        return String.format(leftAlignFormat, index, id, correctWords, wrongWords);
    }

    public String getAvaiableWords() {
        StringBuilder avaiableWords = new StringBuilder();

        avaiableWords.append(String.format(("+-------------------+%n")));
        avaiableWords.append(String.format(("|Palavras Restantes |%n")));
        avaiableWords.append(String.format(("+-------------------+%n")));
        String leftAlignFormat = "|%-19s|%n";

        for (String w : words) {
            avaiableWords.append(String.format(leftAlignFormat, w));
        }
        avaiableWords.append(String.format(("+-------------------+%n")));
        return avaiableWords.toString();
    }

}
