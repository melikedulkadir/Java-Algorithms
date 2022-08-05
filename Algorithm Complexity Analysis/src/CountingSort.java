public class CountingSort {
    public static void countSort(int array[]) {
        int size = array.length;
        int[] output = new int[size + 1];
        int max = array[0];

        // Find the max element of the array
        for (int i = 1; i < size; i++) {
            if (array[i] > max)
                max = array[i];
        }
        // Creating and initializeing count array
        int[] count = new int[max + 1];
        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }
        // Adding count of each element in the array to the count array
        for (int i = 0; i < size; i++) {
            count[array[i]]++;
        }
        // Adding the cummulative count of each element
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }
        // Finding the index of each element and placing it in an output array
        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }
        System.arraycopy(output, 0, array, 0, size);
    }
}
