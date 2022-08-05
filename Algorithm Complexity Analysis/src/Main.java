import com.sun.xml.internal.ws.api.BindingIDFactory;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import sun.security.util.ArrayUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String args[]) throws IOException {

        ArrayList<Integer> flowDuration = new ArrayList<Integer>();
        BufferedReader br = new BufferedReader(new FileReader("TrafficFlowDataset.csv"));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(",");
            if (!cols[7].equals(" Flow Duration"))
                flowDuration.add(Integer.parseInt(cols[7]));
        }
        br.close();
        Collections.shuffle(flowDuration);



        // Creating new arrays for each Sort Algorithm and each input size
        int[] first_insertionInput = new int[512];
        int[] first_mergeInput = new int[512];
        int[] first_pigeonholeInput = new int[512];
        int[] first_countingInput = new int[512];

        int[] second_insertionInput = new int[1024];
        int[] second_mergeInput = new int[1024];
        int[] second_pigeonholeInput = new int[1024];
        int[] second_countingInput = new int[1024];

        int[] third_insertionInput = new int[2048];
        int[] third_mergeInput = new int[2048];
        int[] third_pigeonholeInput = new int[2048];
        int[] third_countingInput = new int[2048];

        int[] fourth_insertionInput = new int[4096];
        int[] fourth_mergeInput = new int[4096];
        int[] fourth_pigeonholeInput = new int[4096];
        int[] fourth_countingInput = new int[4096];

        int[] fifth_insertionInput = new int[8192];
        int[] fifth_mergeInput = new int[8192];
        int[] fifth_pigeonholeInput = new int[8192];
        int[] fifth_countingInput = new int[8192];

        int[] sixth_insertionInput = new int[16384];
        int[] sixth_mergeInput = new int[16384];
        int[] sixth_pigeonholeInput = new int[16384];
        int[] sixth_countingInput = new int[16384];

        int[] seventh_insertionInput = new int[32768];
        int[] seventh_mergeInput = new int[32768];
        int[] seventh_pigeonholeInput = new int[32768];
        int[] seventh_countingInput = new int[32768];

        int[] eighth_insertionInput = new int[65536];
        int[] eighth_mergeInput = new int[65536];
        int[] eighth_pigeonholeInput = new int[65536];
        int[] eighth_countingInput = new int[65536];

        int[] ninth_insertionInput = new int[131072];
        int[] ninth_mergeInput = new int[131072];
        int[] ninth_pigeonholeInput = new int[131072];
        int[] ninth_countingInput = new int[131072];

        int[] tenth_insertionInput = new int[251282];
        int[] tenth_mergeInput = new int[251282];
        int[] tenth_pigeonholeInput = new int[251282];
        int[] tenth_countingInput = new int[251282];

        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Creating Y axis data according to the 4 sorting algorithm
        double[][] yAxis = new double[4][10];
        yAxis[0] = new double[10];
        yAxis[1] = new double[10];
        yAxis[2] = new double[10];
        yAxis[3] = new double[10];

        // Fillings arrays with different numbers of inputs
        for (int i = 0; i < flowDuration.size(); i++) {
            if (i < 512) {
                first_insertionInput[i] = flowDuration.get(i);
                first_mergeInput[i] = flowDuration.get(i);
                first_pigeonholeInput[i] = flowDuration.get(i);
                first_countingInput[i] = flowDuration.get(i);
            }
            if (i < 1024) {
                second_insertionInput[i] = flowDuration.get(i);
                second_mergeInput[i] = flowDuration.get(i);
                second_pigeonholeInput[i] = flowDuration.get(i);
                second_countingInput[i] = flowDuration.get(i);
            }
            if (i < 2048) {
                third_insertionInput[i] = flowDuration.get(i);
                third_mergeInput[i] = flowDuration.get(i);
                third_pigeonholeInput[i] = flowDuration.get(i);
                third_countingInput[i] = flowDuration.get(i);
            }
            if (i < 4096) {
                fourth_insertionInput[i] = flowDuration.get(i);
                fourth_mergeInput[i] = flowDuration.get(i);
                fourth_pigeonholeInput[i] = flowDuration.get(i);
                fourth_countingInput[i] = flowDuration.get(i);
            }
            if (i < 8192) {
                fifth_insertionInput[i] = flowDuration.get(i);
                fifth_mergeInput[i] = flowDuration.get(i);
                fifth_pigeonholeInput[i] = flowDuration.get(i);
                fifth_countingInput[i] = flowDuration.get(i);
            }
            if (i < 16384) {
                sixth_insertionInput[i] = flowDuration.get(i);
                sixth_mergeInput[i] = flowDuration.get(i);
                sixth_pigeonholeInput[i] = flowDuration.get(i);
                sixth_countingInput[i] = flowDuration.get(i);
            }
            if (i < 32768) {
                seventh_insertionInput[i] = flowDuration.get(i);
                seventh_mergeInput[i] = flowDuration.get(i);
                seventh_pigeonholeInput[i] = flowDuration.get(i);
                seventh_countingInput[i] = flowDuration.get(i);
            }
            if (i < 65536) {
                eighth_insertionInput[i] = flowDuration.get(i);
                eighth_mergeInput[i] = flowDuration.get(i);
                eighth_pigeonholeInput[i] = flowDuration.get(i);
                eighth_countingInput[i] = flowDuration.get(i);
            }
            if (i < 131072) {
                ninth_insertionInput[i] = flowDuration.get(i);
                ninth_mergeInput[i] = flowDuration.get(i);
                ninth_pigeonholeInput[i] = flowDuration.get(i);
                ninth_countingInput[i] = flowDuration.get(i);
            }
            if (i < 251282) {
                tenth_insertionInput[i] = flowDuration.get(i);
                tenth_mergeInput[i] = flowDuration.get(i);
                tenth_pigeonholeInput[i] = flowDuration.get(i);
                tenth_countingInput[i] = flowDuration.get(i);
            } else break;
        }

        // Arrays of arrays for parameter of experiment method
        int[][] insertionSort_arrays = {first_insertionInput, second_insertionInput, third_insertionInput, fourth_insertionInput, fifth_insertionInput, sixth_insertionInput, seventh_insertionInput, eighth_insertionInput, ninth_insertionInput, tenth_insertionInput};
        int[][] mergeSort_arrays = {first_mergeInput, second_mergeInput, third_mergeInput, fourth_mergeInput, fifth_mergeInput, sixth_mergeInput, seventh_mergeInput, eighth_mergeInput, ninth_mergeInput, tenth_mergeInput};
        int[][] pigeonholeSort_arrays = {first_pigeonholeInput, second_pigeonholeInput, third_pigeonholeInput, fourth_pigeonholeInput, fifth_pigeonholeInput, sixth_pigeonholeInput, seventh_pigeonholeInput, eighth_pigeonholeInput, ninth_pigeonholeInput, tenth_pigeonholeInput};
        int[][] countingSort_arrays = {first_countingInput, second_countingInput, third_countingInput, fourth_countingInput, fifth_countingInput, sixth_countingInput, seventh_countingInput, eighth_countingInput, ninth_countingInput, tenth_countingInput};

        // Experiments on the Given Random Data
        System.out.println("Tests on Random Data");
        experiment("Tests on Random Data",inputAxis,yAxis,insertionSort_arrays,mergeSort_arrays,pigeonholeSort_arrays,countingSort_arrays);

        System.out.println("Tests on Sorted Data");
        // Experiments on the Sorted Data
        experiment("Tests on Sorted Data",inputAxis,yAxis,insertionSort_arrays,mergeSort_arrays,pigeonholeSort_arrays,countingSort_arrays);

        for (int i = 0; i < insertionSort_arrays.length; i++) {
            int[] reverse_arr = new int[insertionSort_arrays[i].length];
            int j = insertionSort_arrays[i].length;
            for (int k = 0; i < j; k++) {
                reverse_arr[j - 1] = insertionSort_arrays[i][k];
                j = j - 1;
            }
            insertionSort_arrays[i] = reverse_arr;
        }
        for (int i = 0; i < mergeSort_arrays.length; i++) {
            int[] reverse_arr = new int[mergeSort_arrays[i].length];
            int j = mergeSort_arrays[i].length;
            for (int k = 0; i < j; k++) {
                reverse_arr[j - 1] = mergeSort_arrays[i][k];
                j = j - 1;
            }
            mergeSort_arrays[i] = reverse_arr;
        }

        for (int i = 0; i < pigeonholeSort_arrays.length; i++) {
            int[] reverse_arr = new int[pigeonholeSort_arrays[i].length];
            int j = pigeonholeSort_arrays[i].length;
            for (int k = 0; i < j; k++) {
                reverse_arr[j - 1] = pigeonholeSort_arrays[i][k];
                j = j - 1;
            }
            pigeonholeSort_arrays[i] = reverse_arr;
        }

        for (int i = 0; i < countingSort_arrays.length; i++) {
            int[] reverse_arr = new int[countingSort_arrays[i].length];
            int j = countingSort_arrays[i].length;
            for (int k = 0; i < j; k++) {
                reverse_arr[j - 1] = countingSort_arrays[i][k];
                j = j - 1;
            }
            countingSort_arrays[i] = reverse_arr;
        }

        System.out.println("Tests on Reverse Sorted Data");
        // Experiments on the Reversely Sorted Data
        experiment("Tests on Reverse Sorted Data", inputAxis, yAxis, insertionSort_arrays, mergeSort_arrays, pigeonholeSort_arrays, countingSort_arrays);
    }

    // Function for see the results for varying input sizes on different types of data
    public static void experiment(String experiment_title,int[] inputAxis, double[][] yAxis,int[][] insertionSort_arrays,int[][] mergeSort_arrays,int[][] pigeonholeSort_arrays,int[][] countingSort_arrays) throws IOException {
        // Adding running times to the yAxis column[0] for insertion sort algorithm
        for(int i=0; i<10; i++){
            ArrayList<Double> running_times = new ArrayList<>();
            for(int j=0; j<10; j++){
                int[] copiedArray = Arrays.copyOf(insertionSort_arrays[i], insertionSort_arrays[i].length);
                Stopwatch st1 = new Stopwatch();
                InsertionSort.insertionSort(insertionSort_arrays[i]);
                running_times.add(st1.elapsedTimeMillis());
                insertionSort_arrays[i] = copiedArray;
            }
            InsertionSort.insertionSort(insertionSort_arrays[i]);
            double average = 0;
            for (double running_time:running_times){
                average+=running_time;
            }
            average = average/10;
            yAxis[0][i] = average;
            System.out.println(i+1 + ". Input size(Insertion): " +insertionSort_arrays[i].length + " Time (milliseconds) = " + yAxis[0][i]);
        }
        // Adding running times to the yAxis column[1] for merge sort algorithm
        for(int i=0; i<10; i++){
            ArrayList<Double> running_times = new ArrayList<>();
            for(int j=0; j<10; j++){
                int[] copiedArray = Arrays.copyOf(mergeSort_arrays[i], mergeSort_arrays[i].length);
                Stopwatch st1 = new Stopwatch();
                MergeSort.mergeSort(mergeSort_arrays[i]);
                running_times.add(st1.elapsedTimeMillis());
                mergeSort_arrays[i] = copiedArray;
            }
            mergeSort_arrays[i] = MergeSort.mergeSort(mergeSort_arrays[i]);
            double average = 0;
            for (double running_time:running_times){
                average+=running_time;
            }
            average = average/10;
            yAxis[1][i] = average;
            System.out.println(i+1 + ". Input size(Merge): " +mergeSort_arrays[i].length + " Time (milliseconds) = " + yAxis[1][i]);
        }
        // Adding running times to the yAxis column[2] for pigeonhole sort algorithm
        for(int i=0; i<10; i++){
            ArrayList<Double> running_times = new ArrayList<>();
            for(int j=0; j<10; j++){
                int[] copiedArray = Arrays.copyOf(pigeonholeSort_arrays[i], pigeonholeSort_arrays[i].length);
                Stopwatch st1 = new Stopwatch();
                PigeonholeSort.pigeonholeSort(pigeonholeSort_arrays[i]);
                running_times.add(st1.elapsedTimeMillis());
                pigeonholeSort_arrays[i] = copiedArray;
            }
            PigeonholeSort.pigeonholeSort(pigeonholeSort_arrays[i]);
            double average = 0;
            for (double running_time:running_times){
                average+=running_time;
            }
            average = average/10;
            yAxis[2][i] = average;
            System.out.println(i+1 + ". Input size(Pigeonhole): " +pigeonholeSort_arrays[i].length + " Time (milliseconds) = " + yAxis[2][i]);
        }
        // Adding running times to the yAxis column[3] for counting sort algorithm
        for(int i=0; i<10; i++){
            ArrayList<Double> running_times = new ArrayList<>();
            for(int j=0; j<10; j++){
                int[] copiedArray = Arrays.copyOf(countingSort_arrays[i], countingSort_arrays[i].length);
                Stopwatch st1 = new Stopwatch();
                CountingSort.countSort(countingSort_arrays[i]);
                running_times.add(st1.elapsedTimeMillis());
                countingSort_arrays[i] = copiedArray;
            }
            CountingSort.countSort(countingSort_arrays[i]);
            double average = 0;
            for (double running_time:running_times){
                average+=running_time;
            }
            average = average/10;
            yAxis[3][i] = average;
            System.out.println(i+1 + ". Input size(Counting): " + countingSort_arrays[i].length + " Time (milliseconds) = " + yAxis[3][i]);
        }

        // Save the char as .png and show it
        showAndSaveChart(experiment_title, inputAxis, yAxis);

    }
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Sample Data 1: Insertion", doubleX, yAxis[0]);
        chart.addSeries("Sample Data 2: Merge", doubleX, yAxis[1]);
        chart.addSeries("Sample Data 3: Pigeonhole", doubleX, yAxis[2]);
        chart.addSeries("Sample Data 4: Counting", doubleX, yAxis[3]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
