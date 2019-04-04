package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.HashSet;
import edu.isu.cs.cs3308.structures.impl.SpellChecker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Driver {

    public static void main(String[] args) throws IOException {
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
                        List<String> l = new ArrayList<>();
                        SpellChecker sc = new SpellChecker();
                        l = sc.check(sentToCheck[i].toLowerCase());
                        Iterator<String> it = l.iterator();
                        if (l.size() == 1 && l.contains(sentToCheck[i].toLowerCase())){
                            //Do nothing
                        } else if (l.size() > 5) {
                            results += "Misspelled word: " + sentToCheck[i] + " May I suggest: \r\n";
                            for (int j = 0; j < 5; j ++){
                                results += it.next() + "\r\n";
                            }
                        } else {
                            results += "Misspelled word: " + sentToCheck[i] + " May I suggest: \r\n";
                            while (it.hasNext()){
                                results += it.next() + "\r\n";
                            }
                        }
                    }

                    if (!results.equals("")) {
                        System.out.print(results);
                    } else {
                        System.out.print("No misspellings found!\r\n");
                    }
                }
            }

    }
}
