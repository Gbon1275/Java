// This program is created to make random sentences according to the rules of the assignment

public class SimpleRandomSentences {
    static final String[] adjective = {"big", "tiny", "pretty", "bald"};
    static final String[] conjunction = {"and", "or", "but", "because"};
    static final String[] common_noun = {"man", "woman", "fish", "elephant", "unicorn"};
    static final String[] determiner = {"a", "the", "every", "some"};
    static final String[] intransitive_verb = {"runs", "jumps", "talks", "sleeps"};
    static final String[] proper_noun= {"Fred", "Jane", "Richard Nixon", "Miss America"};
    static final String[] transitive_verb = {"loves", "hates", "sees", "knows", "looks for", "finds"};

    public static void main(String[] args) { // this will print random sentences every 5 seconds
        while (true) {
            sentence();
            System.out.println(".\n\n");
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e){ // please click terminate (red button on the same row as console) to end program
            }
        }
    }
    static void sentence() { // create random sentences
        noun(); // random noun
        verb(); // random verb
        if (Math.random() > 0.50) { // 50% continue with another random word
            System.out.println("" + random(conjunction));
            sentence();
        }
    }
    static void noun() {
        if (Math.random() > 0.50) { // 50% continue with another random word
            System.out.println("" + random(proper_noun));
        }
        else {
            System.out.println("" + random(determiner));
            if(Math.random() > 0.25) { // 75% continue with another random word
                System.out.println("who");
                verb();
            }
        }

    }
    static void verb() {
        if (Math.random() > 0.75) {
            System.out.println("" + random(intransitive_verb));
        }
        else if (Math.random() > 0.50) {
            System.out.println("" + random(transitive_verb));
            noun();
        }
        else if (Math.random() > 0.25) {
            System.out.println("" + random(adjective));
        }
        else {
            System.out.println("believes that");
            noun();
            verb();
        }
    }
    static String random(String[] listOfStrings){
        return listOfStrings[(int)(Math.random()*listOfStrings.length)];
    }
}