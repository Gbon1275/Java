public class main {

    public static void main(String[]args) {
        System.out.print("What's your name?");
        String name = TextIO.getln();
        System.out.println("Pleased to meet you" + name);
        System.out.println("Please do this math problem");
        AdditionProblem AdditionProblem = new AdditionProblem();
        System.out.print(AdditionProblem.getProblem());
        int Answer = TextIO.getlnInt();
        int RealAnswer = AdditionProblem.getAnswer();
        if ( Answer == AdditionProblem.getAnswer()) {
            System.out.println("Correct Well done");
        }
        else {
            System.out.println("Wrong The Answer is " + AdditionProblem.getAnswer());
        }
    }
}
