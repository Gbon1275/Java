//Algo Benchmark for 1000 random integers using InsertionSort: 2ms
//Algo Benchmark for 1000 random integers using ArraySort: 0ms
//Algo Benchmark for 10000 random integers using InsertionSort: 25ms
//Algo Benchmark for 10000 random integers using ArraySort: 1ms
//Algo Benchmark for 100000 random integers using InsertionSort: 673ms
//Algo Benchmark for 100000 random integers using ArraySort: 2ms

import java.util.Arrays;

public class algoBenchmarking {

    private static int maxArraySize = 1000;
    public static int[]Array1 = new int[maxArraySize];
    public static int[]Array2 = null;

    static void insertionSort(int[] A) {
    // Sort the array A into increasing order.
        int itemsSorted; // Number of items that have been sorted so far.
        for (itemsSorted = 1; itemsSorted < A.length; itemsSorted++) {
        // Assume that items A[0], A[1], ... A[itemsSorted-1]
        // have already been sorted. Insert A[itemsSorted]
            // into the sorted part of the list.
            int temp = A[itemsSorted]; // The item to be inserted.
            int loc = itemsSorted - 1; // Start at end of list.
            while (loc >= 0 && A[loc] > temp) {
                A[loc + 1] = A[loc]; // Bump item from A[loc] up to loc+1.
                loc = loc - 1; // Go on to next location.
            }
            A[loc + 1] = temp; // Put temp in last vacated space.
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < Array1.length; i++){
            Array1[i]=(int)(Integer.MAX_VALUE * Math.random());

        }
        Array2 = Array1;
        long StartTime1 = System.currentTimeMillis();
        insertionSort(Array1);
        long RunTime1 = System.currentTimeMillis() - StartTime1;

        long StartTime2 = System.currentTimeMillis();
        Arrays.sort(Array2);
        long RunTime2 = System.currentTimeMillis() - StartTime2;

        System.out.println("Algo Benchmark for InsertionSort: " + RunTime1 + "ms");
        System.out.println("Algo Benchmark for ArraySort: " + RunTime2 + "ms");
    }


}
