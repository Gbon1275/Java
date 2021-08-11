import java.util.Arrays;
import java.util.Random;


/*

  With N=100000 then
  insertionSort Time spend in ms: 5145
  Arrays.sort() Time spend in ms: 96


   With N=10000 then
  insertionSort Time spend in ms: 84
	Arrays.sort() Time spend in ms: 13

   With N=1000 then
	insertionSort Time spend in ms: 5
	Arrays.sort() Time spend in ms: 2

  */
public class sortTime {

    static void insertionSort(int[] A) {

        // Sort the array A into increasing order.

        int itemsSorted; // Number of items that have been sorted so far.

        for (itemsSorted = 1; itemsSorted < A.length; itemsSorted++) {
            // Assume that items A[0], A[1], ... A[itemsSorted-1]
            // have already been sorted.  Insert A[itemsSorted]
            // into the sorted part of the list.

            int temp = A[itemsSorted];  // The item to be inserted.
            int loc = itemsSorted - 1;  // Start at end of list.

            while (loc >= 0 && A[loc] > temp) {
                A[loc + 1] = A[loc]; // Bump item from A[loc] up to loc+1.
                loc = loc - 1;       // Go on to next location.
            }

            A[loc + 1] = temp; // Put temp in last vacated space.
        }
    }


    public static void main(String[] args) {
        int N = 1000;


        int[] arrayA;
        arrayA = new int[N];
        int[] arrayB;
        arrayB = new int[N];


        //Fill the arrays with random integers.
        //The arrays should have identical contents, with the same random numbers in both arrays.
        for(int i=0; i<N; i++){
            int num=(int)(Math.random()*Integer.MAX_VALUE);
            arrayA[i] = num;
            arrayB[i] = num;

        }


        long startTime = System.currentTimeMillis();
        insertionSort(arrayA);
        long runTime = System.currentTimeMillis() - startTime;
//        System.out.println(Arrays.toString(arrayA));
        System.out.println("insertionSort Time spend in ms: " + runTime);


        long startTime2 = System.currentTimeMillis();
        Arrays.sort(arrayB);
        long runTime2 = System.currentTimeMillis() - startTime2;
//      System.out.println(Arrays.toString(arrayB));
        System.out.println("Arrays.sort() Time spend in ms: " + runTime2);


    }

}