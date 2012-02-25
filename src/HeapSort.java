
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A few factoids about HeapSort:
 *
 * 1) Its runtime is O(n log n). That is also its guaranteed worst-case run
 * time. Therefore, for large data sets it is less prone to security risks than
 * QuickSort, which has a worst-case run time of O(n^2). Though for most cases,
 * QuickSort is faster. 2) It runs in-place, requiring no extra space to run it.
 * 3) It is not a stable sort.
 */
/**
 * @author Roger
 *
 */
public class HeapSort {

    String swap = null;

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String[] files = {"lincoln2.txt", "lincoln.txt", "twain2.txt",
            "twain3.txt", "twain.txt", "eliot.txt", "tolstoy.txt"};

        HeapSort sort = new HeapSort();

        SimpleDateFormat format =
                new SimpleDateFormat("EEE MMM dd HH:mm:ss-SSS zzz yyyy");
        String[] sortTest = null;

        System.out.println("---------TEST HEAP SORT ------------");
        for (int x = 0; x < files.length; x++) {
            String[] testCase = MergeTest.readFile(files[x]);


            long start = System.currentTimeMillis();
            Date now = new Date(start);
            System.out.println("File: " + files[x]);
            System.out.println("Total words: " + testCase.length);
            System.out.println("begin test: " + format.format(now));


            sortTest = sort.heapSort(testCase);
            long end = System.currentTimeMillis();
            now = new Date(end);
            System.out.println("end test: " + format.format(now));
            System.out.println("total time: " + (end - start));
            System.out.println("--------------------------\n\n");

        }

        //for (int i = 0; i < sortTest.length; i++) {
        //    System.out.println(sortTest[i]);
        //}

    }

    private String[] heapSort(String[] list) {
        heapify(list);

        int end = list.length - 1;
        while (end > 0) {
            list = swap(list, 0, end);
            end--;
            siftDown(list, 0, end);

        }
        return list;
    }

    private String[] heapify(String[] list) {
        int start = (list.length) / 2;
        while (start >= 0) {
            siftDown(list, start, list.length);
            start--;
        }
        return list;
    }

    private String[] siftDown(String[] list, int start, int end) {
        String tmp;
        int child;
        for (tmp = list[start]; leftChild(start) < end; start = child) {
            child = leftChild(start);
            if (child != end - 1 && list[child].compareTo(list[child + 1]) < 0) {
                child++;
            }
            if (tmp.compareTo(list[child]) < 0) {
                list[start] = list[child];
            } else {
                break;
            }
        }

        list[start] = tmp;
        return list;

    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private String[] swap(String[] list, int index1, int index2) {
        this.swap = list[index1];
        list[index1] = list[index2];
        list[index2] = swap;
        return list;
    }
}
