package Spellchecker;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

public class Dict {

    public static void main(String[] args) throws FileNotFoundException,InputMismatchException{

        Scanner fileIn;
        Scanner customFile;

        try{

            fileIn = new Scanner(new File("/words.txt"));

            HashSet<String> hash = new HashSet<>();

            while (fileIn.hasNext()) {
                String ns = fileIn.next();
                hash.add(ns.toLowerCase());

            }

            customFile = new Scanner(getUserFile());
            customFile.useDelimiter("[^a-zA-Z]+");

            while (customFile.hasNext()){
                String customString = customFile.next();
                String customString2 = customString.toLowerCase();
                if (!hash.contains(customString2)){
                    System.out.println(customString2 + ":" + corrections(customString2,hash));
                }
            }

            fileIn.close();
            customFile.close();
        }
        catch(IOException e){
            System.out.println("File Not Found");
        }
        catch(java.util.InputMismatchException e){
            System.out.println("Input Mismatch");
        }
    }

    static TreeSet<String> corrections(String wrongWord,HashSet<String> dictionary){

        TreeSet<String> tree = new TreeSet<>();

        for (int i = 0; i < wrongWord.length(); i++){
            String st = wrongWord.substring(0, i) + wrongWord.substring(i + 1);
            if (dictionary.contains(st)) {
                tree.add(st);
            }
        }

        for (int i = 0; i < wrongWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++){
                String st = wrongWord.substring(0,i) + ch + wrongWord.substring(i);
                if (dictionary.contains(st)){
                    tree.add(st);
                }
            }
        }

        for (int i = 1;i < wrongWord.length(); i++) {
            String stInput = wrongWord.substring(0,i) + "" + wrongWord.substring(i);

            String tempStr;

            StringTokenizer tempWords = new StringTokenizer(stInput);

            while (tempWords.hasMoreTokens()) {
                String Word1 = tempWords.nextToken();
                String Word2 = tempWords.nextToken();

                if (dictionary.contains(Word1) && dictionary.contains(Word2)) {
                    tempStr = Word1 + " " + Word2;
                } else {
                    break;
                }
                tree.add(tempStr);
            }
        }

        if (tree.isEmpty()){
            tree.add("no suggestion");
        }
        return tree;
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
