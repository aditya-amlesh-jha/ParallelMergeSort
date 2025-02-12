import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortTask extends RecursiveTask<int[]> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int[] compute(){
        if(end - start <= THRESHOLD){
            int[] subArray = Arrays.copyOfRange(array, start, end);
            Arrays.sort(subArray);
            return subArray;
        }

        int mid = start + (end - start)/2;

        MergeSortTask leftTask = new MergeSortTask(array, start, mid);
        MergeSortTask rightTask = new MergeSortTask(array, mid + 1, end);

        leftTask.fork();
        rightTask.fork();

        int[] leftArr = leftTask.join();
        int[] rightArr = rightTask.join();

        return merge(leftArr, rightArr);
    }

    private static int[] merge(int[] left, int[] right){
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
        return result;
    }
}
