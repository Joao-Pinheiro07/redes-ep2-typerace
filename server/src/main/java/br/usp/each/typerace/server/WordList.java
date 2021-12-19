package br.usp.each.typerace.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordList {
    public static HashSet<String> getWordList() {
        // Set<Integer> indexes = getRandomIndexes();
        // Set<String> wordList = new HashSet<String>();

        // List<String> allLines = null;
        // try {
        // allLines =
        // Files.readAllLines(Paths.get("server/src/main/java/br/usp/each/typerace/server/words.txt"));
        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }

        // for (int index : indexes) {
        // wordList.add(allLines.get(index));
        // }
        return (new HashSet<String>(Arrays.asList("C", "python", "Java", "protest", "illustrious",
                "abacata", "gato", "cao", "passaro", "camelo")));
    }

    private static Set<Integer> getRandomIndexes() {
        Random randNum = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 15) {
            set.add(randNum.nextInt(1000) + 1);
        }
        return set;
    }
}
