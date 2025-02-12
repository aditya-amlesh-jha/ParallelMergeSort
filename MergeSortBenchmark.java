import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MergeSortBenchmark {

    private static final int SIZE = 100_000_000;

    public static void sequentialSort(int[] array) {
        Arrays.sort(array);
    }

    public static void parallelMergeSort(int[] array) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        MergeSortTask task = new MergeSortTask(array, 0, array.length);
        pool.invoke(task);
    }

    public static void main(String[] args) {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = (int) (Math.random() * 1_000_000);
        }

        // Benchmark sequential sort
        int[] sequentialArray = Arrays.copyOf(array, array.length);
        long startTime = System.nanoTime();
        sequentialSort(sequentialArray);
        long endTime = System.nanoTime();
        System.out.println("Sequential Sort Time: " + (endTime - startTime) / 1_000_000_000.0 + " s");

        // Benchmark parallel merge sort
        int[] parallelArray = Arrays.copyOf(array, array.length);
        startTime = System.nanoTime();
        parallelMergeSort(parallelArray);
        endTime = System.nanoTime();
        System.out.println("Parallel Merge Sort Time: " + (endTime - startTime) / 1_000_000_000.0 + " s");
    }
}
