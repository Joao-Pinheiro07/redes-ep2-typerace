package br.usp.each.typerace.server;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        int corrects = p1.getCorrectWords() - p2.getCorrectWords();
        if (corrects > 0)
            return -1;
        if (corrects < 0)
            return 1;
        if (corrects == 0) {
            int wrongs = p1.getWrongWords() - p2.getWrongWords();
            if (wrongs > 0)
                return 1;
            if (wrongs < 0)
                return -1;
            if (wrongs == 0)
                return 0;
        }
        return 0;

    }
}