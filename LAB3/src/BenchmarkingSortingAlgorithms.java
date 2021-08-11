import java.util.Arrays;
class BenchmarkingSortingAlgorithms {
    private static int maxLoc = 0;
    public static void main(String[] args) {
        int Size=10000; // Array Size
        /** Create two arrays of type int[]
         * Both arrays should be the same size,
         * and the size should be given by a constant in the program so that you can change it easily*/
        int[] firstArray = new int[Size]; // First Array
        int[] secondArray = new int[Size]; // Second Array
        /*  Class Constructor*/
        for (int i = 0; i < firstArray.length; i++) {
            /**The arrays should have identical contents, with the same random numbers in both arrays.
             * To generate random integers with a wide range of sizes, we use (int)(Integer.MAX_VALUE * Math.random())   */
            firstArray[i]=(int)(Integer.MAX_VALUE * Math.random());
            secondArray[i]=firstArray[i];
        }
        /** Getting the run time of 1st code segment using SelectionSort*/
        long startTime1stArray = System.currentTimeMillis();
        selectionSort(firstArray); // Do sorting firstArray with SelectionSort
        long runTime1stArray = System.currentTimeMillis() - startTime1stArray; //Calculate time to run the SelectionSort
        /** Getting the run time of 2nd code segment using Arrays.sort */
        long startTime2ndArray = System.currentTimeMillis();
        Arrays.sort(secondArray); // Do sorting secondArray with Arrays.sort
        long runTime2ndArray = System.currentTimeMillis() - startTime2ndArray; //Calculate time to run the Arrays.sort
        /** Print results*/
        System.out.println("SelectionSort time(sec):"+runTime1stArray/1000.0); // Using SelectionSort
        System.out.println("Arrays Sort time(sec):"+runTime2ndArray/1000.0); // Using Arrays Sort
    }
    /** Added the sorting method to the program; Copied copy it from Section 7.4*/
    static void selectionSort(int[] A) {
        // Sort A into increasing order, using selection sort
        for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
            // Find the largest item among A[0], A[1], ...,
            // A[lastPlace], and move it into position lastPlace
            // by swapping it with the number that is currently
            // in position lastPlace.
            int maxLoc = 0; // Location of largest item seen so far.
            for (int j = 1; j <= lastPlace; j++) {
                if (A[j] > A[maxLoc]) {
                    // Since A[j] is bigger than the maximum we’ve seen
                    // so far, j is the new location of the maximum value
                    // we’ve seen so far.
                    maxLoc = j;
                }
            }
            int temp = A[maxLoc]; // Swap largest item with A[lastPlace].
            A[maxLoc] = A[lastPlace];
            A[lastPlace] = temp;
        } // end of for loop
    }
}