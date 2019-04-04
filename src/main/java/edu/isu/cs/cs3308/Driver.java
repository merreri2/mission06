package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.HashSet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {

    public static void main(String[] args) throws IOException {

        File f = new File("data/en-US.dic");
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(f));
        lineNumberReader.skip(Long.MAX_VALUE);
        int lines = lineNumberReader.getLineNumber();
        lineNumberReader.close();

        HashSet<String> dictionary = new HashSet<>(lines);

        BufferedReader reader;


            reader = new BufferedReader(new FileReader(f));
            String l = reader.readLine();
            while (l != null){
                dictionary.add(l);
                l = reader.readLine();
            }
            reader.close();

            boolean loop = true;

            while (loop){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String str;
                System.out.println("Enter a string to spell check.  Enter '0' to quit.");
                str = br.readLine();

                if (str.equals("0")){
                    loop = false;
                } else {
                    String[] sentToCheck = str.split(" ");
                    String results = "";
                    for (int i = 0; i < sentToCheck.length; i++){
                        if (!dictionary.contains(sentToCheck[i])){
                            results += "Misspelled word: " + sentToCheck[i] + "\r\n";
                        }
                    }

                    System.out.print(results);
                }
            }

    }
}
