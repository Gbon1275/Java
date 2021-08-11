package Spellchecker;

import java.util.Scanner;

import java.util.Iterator;

import java.util.TreeSet;

import javax.swing.JFileChooser;

import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;

class SpellChecker {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner dictionaryFile = null;
        try {
            File fileIn = new File("src/words.txt");
            dictionaryFile = new Scanner(fileIn);
            dictionaryFile.useDelimiter("[^a-zA-Z]+");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HashSet<String> dictHashSet = new HashSet();

        while(dictionaryFile.hasNext()) {
            String word = dictionaryFile.next();
            word = word.toLowerCase();
            dictHashSet.add(word);
        }
        System.out.print(dictHashSet.size());

        Scanner wordsFile = null;
        try {
            File fileIn = getInputFileNameFromUser();
            wordsFile = new Scanner(fileIn);
            wordsFile.useDelimiter("[^a-zA-Z]+");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(wordsFile.hasNext()) {
            String word = wordsFile.next();
            if (!dictHashSet.contains(word)) {
                TreeSet correctionsTreeSet = corrections(word, dictHashSet);
                Iterator iter = correctionsTreeSet.iterator();
                System.out.print("\n" + word + ": ");
                if (correctionsTreeSet.isEmpty()) {
                    System.out.print("(no suggestions)");
                }
                while(iter.hasNext()) {
                    System.out.print(iter.next());
                    if (iter.hasNext()) {
                        System.out.print(", ");
                    }
                }
            }
        }

    }

    static TreeSet corrections(String badWord, HashSet dictionary) {
        TreeSet correctionsTreeSet = new TreeSet();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Deleting and changing every one character from the word
        String tempWord = null; // the used variable to store the word with 1 deleted character
        for (int i=0; i<badWord.length(); i++) {
            // deleting every letter of the misspelled word
            tempWord = badWord.substring(0, i) + badWord.substring(i+1);
            if (dictionary.contains(tempWord)){
                correctionsTreeSet.add(tempWord);
            }

            for (char ch : chars) {
                // changing every letter of the misspelled word
                tempWord = badWord.substring(0, i) + ch + badWord.substring(i+1);
                if (dictionary.contains(tempWord)) {
                    correctionsTreeSet.add(tempWord);
                }

                // inserting any letter at any point of the misspelled word
                tempWord = badWord.substring(0, i) + ch + badWord.substring(i);
                if (dictionary.contains(tempWord)) {
                    correctionsTreeSet.add(tempWord);
                }
            }

            // swapping every two neighboring characters in the misspelled word
            if (i!=badWord.length()-1) {
                tempWord = badWord;
                char[] tempWordArray = tempWord.toCharArray();
                char tempChar = tempWordArray[i];
                tempWordArray[i] = tempWordArray[i+1];
                tempWordArray[i+1] = tempChar;
                tempWord = String.valueOf(tempWordArray);

                if (dictionary.contains(tempWord)) {
                    correctionsTreeSet.add(tempWord);
                }
            }


            // Adding a space at any point of the misspelled word and checking if the two resulting words are in the dictionary
            String wordOne = badWord.substring(0, i);
            String wordTwo = badWord.substring(i);
            tempWord = wordOne + " " + wordTwo;
            if (dictionary.contains(wordOne) && dictionary.contains(wordTwo)) {
                correctionsTreeSet.add(tempWord);
            }



        }

        return correctionsTreeSet;
    }


    /**
     * Lets the user select an input file using a standard file
     * selection dialog box.  If the user cancels the dialog
     * without selecting a file, the return value is null.
     */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();
    }

}
