public class RecursiveSyntax {

    static String[] conjunction;
    static String[] proper_noun;
    static String[] common_noun;
    static String[] determiner;
    static String[] adjective;
    static String[] intransitive_verb;
    static String[] transitive_verb;

    // you can add and remove syntax here. 
    static {
        conjunction = new String[]{"and", "or", "but", "because"};
        proper_noun = new String[]{"Fred", "Jane", "Richard Nixon", "Miss America"};
        common_noun = new String[]{"man", "woman", "fish", "elephant",
                "unicorn"};
        determiner = new String[]{"a", "the", "every", "some"};
        adjective = new String[]{"big", "tiny", "pretty", "bald"};
        intransitive_verb = new String[]{"runs", "jumps", "talks", "sleeps"};
        transitive_verb = new String[]{"loves", "hates", "sees", "knows", "looks for", "finds"};
    }

    public static void main(String[] args) {
        while (true) {
            newSentence();
            System.out.println(".\n\n");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
            }
        }
    }
    static void newSentence() {
        getNoun();
        getVerb();
        if (Math.random() > 0.75) {
            System.out.print(" " + randomItem(conjunction));
            newSentence();
        }
    }
    static void getNoun() {
        if (Math.random() > 0.75)
            System.out.print(" " + randomItem(proper_noun));
        else
        {
            System.out.print(" " + randomItem(determiner));
            if (Math.random() > 0.5)
                System.out.print(" " + randomItem(adjective)+".");
            System.out.print(" " + randomItem(common_noun));
            if (Math.random() > 0.5){
                System.out.print(" who" );
                getVerb();
            }
        }
    }
    static void getVerb() {
        if (Math.random() > 0.75)
            System.out.print(" " + randomItem(intransitive_verb));
        else if (Math.random() > 0.50) {
            System.out.print(" " + randomItem(transitive_verb));
            getNoun();
        }
        else if (Math.random() > 0.25)
            System.out.print(" is " + randomItem(adjective));
        else {
            System.out.print(" believes that");
            getNoun();
            getVerb();
        }
    }
    static String randomItem(String[] listOfStrings){
        return listOfStrings[(int)(Math.random()*listOfStrings.length)];
    }
}

