import java.util.Arrays;

    public class Sorting {
        private static final int NUMBER_OF_ITEMS_IN_ARRAY = 1000;

        public static void main(String[] args) {
            // create arrays
            int[] originalArray = generateArrayOfRandomIntegerNumbers();
            int[] controlArray = originalArray.clone();

            // test execution time for sorting with insertionSort
            long startTime = System.currentTimeMillis();
            insertionSort(originalArray);
            long runTime = System.currentTimeMillis() - startTime;

            System.out.printf(
                    "To sort an array with %d elements with insertion sort it took %d milliseconds\n",
                    NUMBER_OF_ITEMS_IN_ARRAY,
                    runTime
            );

            // test execution time for sorting with Arrays.sort()
            startTime = System.currentTimeMillis();
            Arrays.sort(controlArray);
            runTime = System.currentTimeMillis() - startTime;

            System.out.printf(
                    "To sort an array with %d elements Arrays.sort() it took %d milliseconds\n",
                    NUMBER_OF_ITEMS_IN_ARRAY,
                    runTime
            );
        }

        /**
         * Generate and array of random integers
         *
         * @return int[]
         */
        private static int[] generateArrayOfRandomIntegerNumbers() {
            int[] numbers = new int[NUMBER_OF_ITEMS_IN_ARRAY];
            for (int i = 0; i < NUMBER_OF_ITEMS_IN_ARRAY; i++) {
                numbers[i] = (int)(Integer.MAX_VALUE * Math.random());
            }

            return numbers;
        }

        /**
         * Insertion sort method copied form the textbook (p. 362)
         *
         * @param int[]
         */
        private static void insertionSort(int[] A) {
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
    }
