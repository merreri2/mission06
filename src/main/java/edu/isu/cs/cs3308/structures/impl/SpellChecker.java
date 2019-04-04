package edu.isu.cs.cs3308.structures.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpellChecker implements edu.isu.cs.cs3308.SpellChecker {
    /**
     * Checks the spelling of the given string.
     *
     * @param s The string to check the spelling of
     * @return A list of alternatives, if the list is length 1 containing the same value as s, then the provided word was correctly spelled. Else it was not.
     */
    public List<String> check(String s){

        int lines = 0;
        List<String> toReturn = new ArrayList<>();
        //
        try {
            File f = new File("data/en-US.dic");
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(f));
            lineNumberReader.skip(Long.MAX_VALUE);
            lines = lineNumberReader.getLineNumber();
            lineNumberReader.close();
        } catch (IOException e){
            System.out.println("An IO Exception has occurred.");
        }
        HashSet<String> dictionary = new HashSet<>(lines);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("data/en-US.dic"));
            String line = reader.readLine();
            while (line != null){
                dictionary.add(line);
                line = reader.readLine();
            }
        } catch (IOException e){
            System.out.println("An IO Exception has occurred.");
        }

        if (dictionary.contains(s)){
            toReturn.add(s);
        } else{
            Iterator<String> it = dictionary.iterator();
            String wordFromDictionary = it.next();

            while (it.hasNext()){
                if (calculateDistance(s, wordFromDictionary) < 2){
                    toReturn.add(wordFromDictionary);

                }
                wordFromDictionary = it.next();
            }
        }

        return toReturn;
    }
    //Taken from  https://github.com/crwohlfeil/damerau-levenshtein/blob/master/src/main/java/com/codeweasel/DamerauLevenshtein.java
    /**
     * Calculates the string distance between source and target strings using
     * the Damerau-Levenshtein algorithm. The distance is case-sensitive.
     *
     * @param source The source String.
     * @param target The target String.
     * @return The distance between source and target strings.
     * @throws IllegalArgumentException If either source or target is null.
     */
    public static int calculateDistance(String source, String target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength == 0) return targetLength;
        if (targetLength == 0) return sourceLength;
        int[][] dist = new int[sourceLength + 1][targetLength + 1];
        for (int i = 0; i < sourceLength + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < targetLength + 1; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i < sourceLength + 1; i++) {
            for (int j = 1; j < targetLength + 1; j++) {
                int cost = source.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1;
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
                if (i > 1 &&
                        j > 1 &&
                        source.charAt(i - 1) == target.charAt(j - 2) &&
                        source.charAt(i - 2) == target.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        return dist[sourceLength][targetLength];
    }
}
