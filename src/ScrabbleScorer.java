import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


/**
 * @author 26prakash
 * @version 1.30.2023
 */

public class ScrabbleScorer {
    private HashMap<Character, Integer> alpha = new HashMap<Character, Integer>();
    private String alpha1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<ArrayList<String>> dictionary;

    /**
     *ScrabbleScorer constructor
     * A simple constructor with all of the variables needed.
     */
    public ScrabbleScorer() {
        // constructor?
        alpha.put('A', 1);
        alpha.put('B', 3);
        alpha.put('C', 3);
        alpha.put('D', 2);
        alpha.put('E', 1);
        alpha.put('F', 4);
        alpha.put('G', 2);
        alpha.put('H', 4);
        alpha.put('I', 1);
        alpha.put('J', 8);
        alpha.put('K', 5);
        alpha.put('L', 1);
        alpha.put('M', 3);
        alpha.put('N', 1);
        alpha.put('O', 1);
        alpha.put('P', 3);
        alpha.put('Q', 10);
        alpha.put('R', 1);
        alpha.put('S', 1);
        alpha.put('T', 1);
        alpha.put('U', 1);
        alpha.put('V', 4);
        alpha.put('W', 4);
        alpha.put('X', 8);
        alpha.put('Y', 4);
        alpha.put('Z', 10);

        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++) {
            dictionary.add(new ArrayList<String>());
        }
    }

    /**
     * buildDictionary method of the scrabble scorer class
     * Builds buckets with every word which starts with every letter in the alphabet
     */
    public void buildDictionary() {
        try{
            Scanner in = new Scanner(new File("SCRABBLE_WORDS.txt"));
            while(in.hasNext()) {
                String word = in.nextLine();
                dictionary.get(alpha1.indexOf(word.substring(0,1))).add(word);
            }

            in.close();
            for(int i = 0; i < dictionary.size(); i++) {
                Collections.sort(dictionary.get(i));
            }

        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * isValidWord method of the scrabble scorer class
     * Uses the SCRABBLE_WORDS buckets to create an efficient search and validates whether the input is on the list
     * @param word the word that is being validated
     * @return true or false, depending on whether the word is valid
     */
    public boolean isValidWord(String word) {
        word = word.toUpperCase();
        //bucket
        int bucket = 0;
        for(int i = 0; i < 26; i++) {
            if(word.charAt(0) == alpha1.charAt(i)) {
                bucket = i;

            }
        }

        if(Collections.binarySearch(dictionary.get(bucket), word) < 0) {
            return false;
        }
        return true;
    }

    /**
     * getWordScore method of the scrabble scorer class
     * Takes any word and scores it with a hashmap
     * @param word the word that it scores
     * @return score, which is an integer that stores the total points of the word.
     */
    public int getWordScore(String word) {
        int score = 0;
        word = word.toUpperCase();

        for(int i = word.length()-1; i >= 0; i--) {
            score += alpha.get(word.charAt(i));
        }

        return score;
    }

    /**
     * Main method of the scrabble scorer class
     * @param args command line arguments, if needed
     */
    public static void main(String[] args) {
        System.out.println("* Welcome to the Scrabble Word Scorer app *");
        ScrabbleScorer yo = new ScrabbleScorer();
        yo.buildDictionary();

        while(true) {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter a word to score or 0 to quit: ");
            String userVar = in.nextLine();
            if(yo.isValidWord(userVar)) {
                System.out.println(userVar + " = " + yo.getWordScore(userVar) + " points");
            }
            else {
                if(userVar.equals("0")) {
                    System.out.println("Exiting the program thanks for playing");
                    break;
                } else {
                    System.out.println(userVar + " is not a valid word in the dictionary");
                }
            }
        }

    }
}
