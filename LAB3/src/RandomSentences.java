/*

 * UoPeople - CS1103 - Java Programming AY2021 - T5 - Unit 2 Lab 3 Part 1

 *

 * This program produces random sentences based on the recursive syntax in natural language.

 *

 * Instructions for this program:

 *

 *

 * <sentence> ::= <simple_sentence> [ <conjunction> <sentence> ]

 *

 * <simple_sentence> ::= <noun_phrase> <verb_phrase>

 *

 * <noun_phrase> ::= <proper_noun> |

 *  <determiner> [ <adjective> ]. <common_noun> [ who <verb_phrase> ]

 *

 * <verb_phrase> ::= <intransitive_verb> |

 * <transitive_verb> <noun_phrase> |

 * is <adjective> |

 * believes that <simple_sentence>

 *

 * <conjunction> ::= and | or | but | because

 *

 * <proper_noun> ::= Fred | Jane | Richard Nixon | Miss America

 *

 * <common_noun> ::= man | woman | fish | elephant | unicorn

 *

 * <determiner> ::= a | the | every | some

 *

 * <adjective> ::= big | tiny | pretty | bald

 *

 * <intransitive_verb> ::= runs | jumps | talks | sleeps

 *

 * <transitive_verb> ::= loves | hates | sees | knows | looks for | finds

 *

 *

 */



import java.util.Random;



public class RandomSentences {

    static final String[] conjunctions = { "and", "or", "but", "because" };



    static final String[] properNouns = { "Fred", "Jane", "Richard Nixon", "Miss America" };



    static final String[] commonNouns = { "man", "woman", "fish", "elephant", "unicorn" };



    static final String[] determiners = { "a", "the", "every", "some" };



    static final String[] adjectives = { "big", "tiny", "pretty", "bald" };



    static final String[] intransitiveVerbs = { "runs", "jumps", "talks", "sleeps" };



    static final String[] transitiveVerbs = { "loves", "hates", "sees", "knows", "looks for", "finds" };



    static Random rGen = new Random();



    public static void main(String[] args) {

        while (true) {

            sentence();

            System.out.println(".\n\n");

            try {

                Thread.sleep(2500);

            } catch (InterruptedException e) {

            }



        }

    }



    private static void randomItem(String[] listOfStrings) {

        int index = rGen.nextInt(listOfStrings.length);

        System.out.print(listOfStrings[index]);

    }



    private static void sentence() {

        simpleSentence();

        if (Math.random() > 0.2) {

            randomItem(conjunctions);

            System.out.print(" ");

            sentence();

        }



    }



    private static void simpleSentence() {

        nounPhrase();

        System.out.print(" ");

        verbPhrase();

    }



    private static void nounPhrase() {



        if (Math.random() > 0.50) {

            randomItem(properNouns);

            System.out.print(" ");

        } else {

            randomItem(determiners);

            System.out.print(" ");

            if (Math.random() > 0.75) {

                randomItem(adjectives);

                System.out.print(" ");

            }

            System.out.print(" ");

            randomItem(commonNouns);

            System.out.print(" ");

            if (Math.random() > 0.25) {

                System.out.print("who ");

                verbPhrase();

            }

        }

    }



    private static void verbPhrase() {

        if (Math.random() < 0.25) {

            randomItem(intransitiveVerbs);

            System.out.print(" ");

        } else if (Math.random() < 0.50) {

            randomItem(transitiveVerbs);

            System.out.print(" ");

            nounPhrase();

        } else if (Math.random() < 0.75) {

            System.out.print("is ");

            randomItem(adjectives);

            System.out.print(" ");

        } else {

            System.out.print(" believes that ");

            simpleSentence();

        }

    }

}