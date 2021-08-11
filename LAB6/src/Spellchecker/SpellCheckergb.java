package Spellchecker;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

public class SpellCheckergb {

    public static void main(String[] args) {

        System.out.println("Choose the file to be used as a dictionary.\n");
        Collection<String> dictionary = new HashSet<>();
        dictionary = createDictionary();

        if (dictionary.size() == 0) {
            System.out.println("No Dictionary exists, GoodBye.");
            System.exit(0);
        }

        System.out.println("Please choose file to spell check. \n");
        File file = getUserFile();

        if (file != null) {
            System.out.println("Please check this list of potential spelling mistakes. \n");
            spellCheck(file, dictionary);
        }
        else {
            System.out.println("No File Found to check, Goodbye");
            System.exit(1);
        }
    }

    private static Collection<String> createDictionary(){
        Collection<String> dict = new HashSet<>();

        File file = getUserFile();

        if (file != null) {
            try {
                Scanner fileIn = new Scanner(file);

                while (fileIn.hasNext()) {
                    String word = fileIn.next();
                    word = word.toLowerCase();
                    dict.add(word);
                }
                fileIn.close();
            }

         catch (FileNotFoundException e) {
             System.out.println("Can't find dictionary file.  No words "
                     + "added to dictionary.");
         }
        }
        return dict;
    }

    private static void spellCheck(File f, Collection<String> dict) {

        Collection<String> wordsToOutput = new HashSet<>();

        try {
            Scanner fin = new Scanner(f);
            fin.useDelimiter("[^a-zA-Z]+");

            while (fin.hasNext()) {
                String word = fin.next();
                word = word.toLowerCase();

                if (!dict.contains(word)) {
                    if (!wordsToOutput.contains(word)) {
                        Suggestions(word, dict);
                        wordsToOutput.add(word);
                    }
                }
            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file to spell check words.");
        }
    }

    private static void Suggestions(String wrongWord, Collection<String> dict) {

        TreeSet<String> suggestions = new TreeSet<>();

        suggestions.addAll(corrections(wrongWord, dict));

        if (suggestions.size() == 0) {
            System.out.println(wrongWord + ": (no suggestions)");
        } else {


            System.out.print(wrongWord + ": ");
            String firstWord = suggestions.first();
            System.out.print(firstWord);
            for (String word : suggestions.tailSet(firstWord, false)) {
                System.out.print(", " + word);
            }
            System.out.println();
        }
    }

    private static Collection<String> corrections(String wrongWord, Collection<String> dict) {

        Collection<String> corrections = new TreeSet<>();
        String deletedLetters;
        String changedLetters;
        String insertedLetters;
        String swappedLetters;
        String spaceInserted;

        for (int i = 0; i < wrongWord.length(); i++) {

            if (i < wrongWord.length() - 1) {
                char[] c = wrongWord.toCharArray();
                char temp = c[i];
                c[i] = c[i + 1];
                c[i + 1] = temp;

                swappedLetters = new String(c);

                if (dict.contains(swappedLetters)) {
                    corrections.add(swappedLetters);
                }
            }

            if (dict.contains(wrongWord.substring(0,  i)) && dict.contains(wrongWord.substring(i))) {
                spaceInserted = wrongWord.substring(0, i) + ' ' + wrongWord.substring(i);
                corrections.add(spaceInserted);
            }

            for (char ch = 'a'; ch <= 'z'; ch++) {

                deletedLetters = wrongWord.substring(0, i) + wrongWord.substring(i + 1);
                if (dict.contains(deletedLetters)) {
                    corrections.add(deletedLetters);
                }

                changedLetters = wrongWord.substring(0, i) + ch + wrongWord.substring(i + 1);
                if (dict.contains(changedLetters)) {
                    corrections.add(changedLetters);
                }

                insertedLetters = wrongWord.substring(0, i) + ch + wrongWord.substring(i);
                if (dict.contains(insertedLetters)) {
                    corrections.add(insertedLetters);

                } else if (dict.contains(wrongWord + ch)) {
                    corrections.add(wrongWord + ch);
                }
            }
        }

        return corrections;
    }

    static File getUserFile(){
        JFileChooser customFile = new JFileChooser();
        customFile.setDialogTitle("Please Enter File Name");
        int option = customFile.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION){
            return null;
        }
        else{
            return customFile.getSelectedFile();
        }
    }

}
