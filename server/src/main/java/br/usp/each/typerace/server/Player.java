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

    public void checkWord(String anwser) {
        if (words.contains(anwser)) {
            incrementCorrectWords();
            words.remove(anwser);
        } else {
            incrementWrongWords();
        }
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }

}
