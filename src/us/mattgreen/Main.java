package us.mattgreen;

import java.util.*;

/**
 * This class is the driver for the Collections package.
 * It creates a HashMap from a csv file, converts it to a LinkedList, then sorts the list by value (descending).
 * It then creates a LinkedHashMap from the LinkedList, then creates a Set and an Iterator.
 * Finally, it prints the top 100 most-used words, and the number of words used exactly once.
 * Created by jsadi on 9/15/2018.
 * @author Jordan Sadi
 * @version 2018 0915 .3
 */
public class Main {

    private final static FileInput indata = new FileInput("the_book.csv");
    private final static Map<String, Integer> map = new HashMap<String, Integer>();

    public static void main(String[] args) {
        String line;
        String[] words;
        Object wordFound;

        while ((line = indata.fileReadLine()) != null) {
            line=line.replace(",","").replace(".","")
                    .replace(";","").replace(":","")
                    .replace("'","").replace("\"","")
                    .replace("-","").replace("!","")
                    .replace("#","").replace("(","")
                    .replace(")","").replace("?","")
                    .replace("_"," ").replace("?","")
                    .toLowerCase().trim();
            words = line.split(" ");
            for (String s : words) {
                if (s.length() > 0) {
                    wordFound = map.get(s);
                    if (wordFound == null) {
                        map.put(s, new Integer(1));
                    }
                    else {
                        map.put(s, map.get(s) + 1);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        Set entries = sortedMap.entrySet();
        Iterator i = entries.iterator();
        int counter = 0;

        System.out.println("Top 100 words:");

        while(i.hasNext() && counter < 100) {
            Map.Entry entry = (Map.Entry)i.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
            counter++;
        }

        counter = 0;
        while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            int value = (int)entry.getValue();
            if (value == 1) {
                counter++;
            }
        }

        System.out.println("Number of words used only once: " + counter);
    }
}