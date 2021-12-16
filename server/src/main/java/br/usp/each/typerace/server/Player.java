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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
