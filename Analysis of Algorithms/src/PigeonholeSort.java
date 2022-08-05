import java.util.*;
public class PigeonholeSort {
    public static void pigeonholeSort(int array[]) {
        int size = array.length;
        int min = array[0];
        int max = array[0];
        int range, i, j, index;

        // Finding max and min values
        for (int k : array) {
            if (k > max)
                max = k;
            if (k < min)
                min = k;
        }
        range = max - min + 1;
        // Creating new int array named pigeon_hole the same size as of the range
        int[] pigeon_hole = new int[range];
        Arrays.fill(pigeon_hole, 0);

        for (i = 0; i < size; i++)
            pigeon_hole[array[i] - min]++;

        index = 0;
        for (j = 0; j < range; j++){
            while (pigeon_hole[j]-- > 0){
                array[index++] = j + min;
            }
        }
    }
}
