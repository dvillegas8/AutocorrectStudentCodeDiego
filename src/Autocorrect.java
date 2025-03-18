import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Autocorrect
 * <p>
 * A command-line tool to suggest similar words when given one not in the dictionary.
 * </p>
 * @author Zach Blick
 * @author Diego Villegas
 */
public class Autocorrect {

    private String[] words;
    private int threshold;
    /**
     * Constucts an instance of the Autocorrect class.
     * @param words The dictionary of acceptable words.
     * @param threshold The maximum number of edits a suggestion can have.
     */
    public Autocorrect(String[] words, int threshold) {
        this.words = words;
        this.threshold = threshold;
    }

    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {
        String wordOne = "";
        ArrayList<String> goodWords = new ArrayList<String>();
        for(int i = 0; i < words.length; i++){
            wordOne = words[i];
            int[][] table = new int[wordOne.length() + 1][typed.length() + 1];
            // Add our base cases
            for(int j = 0; j < table[0].length; j++){
                table[0][j] = j;
            }
            for(int k = 0; k < table.length; k++){
                table[k][0] = k;
            }
            int editDistance = lev(wordOne, typed, table);
            if(editDistance <= threshold){
                goodWords.add(wordOne);
            }
        }
        return goodWords.toArray(new String[0]);

    }


    /**
     * Loads a dictionary of words from the provided textfiles in the dictionaries directory.
     * @param dictionary The name of the textfile, [dictionary].txt, in the dictionaries directory.
     * @return An array of Strings containing all words in alphabetical order.
     */
    private static String[] loadDictionary(String dictionary)  {
        try {
            String line;
            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
            line = dictReader.readLine();

            // Update instance variables with test data
            int n = Integer.parseInt(line);
            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                line = dictReader.readLine();
                words[i] = line;
            }
            return words;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int lev(String wordOne, String wordTwo, int[][] table){
        int minimum = 0;
        for(int i = 1; i < table.length; i++){
            for(int j = 1; j < table[0].length; j++){
                // If heads are the same, get tail a & tail b
                if(wordOne.charAt(i - 1) == wordTwo.charAt(j - 1)){
                    minimum = table[i - 1][j - 1];
                    table[i][j] = minimum;
                }
                // Other cases since heads aren't the same
                else{
                    // Addition case/ tail a & b
                    int minOne = table[i - 1][j];
                    // Deletion case/ a & tail b
                    int minTwo = table[i][j - 1];
                    // Substitution Case/ tail a & tail b
                    int minThree = table[i - 1][j - 1];
                    // Get the smallest and add one
                    minimum = Math.min(minOne, minTwo);
                    minimum = Math.min(minimum, minThree);
                    table[i][j] = minimum + 1;
                }
            }
        }
        return table[wordOne.length()][wordTwo.length()];
    }
}