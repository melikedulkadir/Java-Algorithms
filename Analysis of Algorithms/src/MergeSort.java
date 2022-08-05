public class MergeSort {
    public static int[] mergeSort(int[] array) {
        int n = array.length;
        if (n < 2) {
            return array;
        }
        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = array[i];
        }
        for (int i = mid; i < n; i++) {
            right[i - mid] = array[i];
        }
        left = mergeSort(left);
        right= mergeSort(right);
        return merge(left, right);
    }
    // Function for merged array after dividing
    public static int[] merge(int[] left, int[] right) {
        int left_size = left.length;
        int right_size = right.length;
        int[] sorted_array = new int[left_size + right_size];
        int i = 0, j = 0, k = 0;

        // Comparing the elements in the right and left arrays and adding them to the new array
        while (i < left_size && j < right_size) {
            if (left[i] < right[j])
                sorted_array[k++] = left[i++];
            else
                sorted_array[k++] = right[j++];
        }

        // Add remaining elements of left array
        while (i < left_size)
            sorted_array[k++] = left[i++];

        // Add remaining elements of right array
        while (j < right_size)
            sorted_array[k++] = right[j++];

        return sorted_array;
    }
}
