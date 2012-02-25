import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A few factoids about MergeSort: 
 * 1) while it gets better performance on the modern PC (because it takes advantage of caching
 *      better than, say, HeapSort)
 * 2) It requires more space than HeapSort -- HeapSort runs in place, while MergeSort requires
 *      extra memory space to hold the recursive calls. So if you're short on memory, 
 *      go for HeapSort
 * 3) MergeSort is stable -- the order of the list never changes once it's set.
 * 4) MergeSort is very easy to run in parallel, for obvious reasons.
 * 5) If you need to chew through very large files, you can use MergeSort to write out 
 *      temporary results to a file and it will work fine. HeapSort, by comparison,
 *      relies strongly on random access and so doesn't operate as well on disk storage media.
 * @author Roger
 *
 */
public class MergeTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String[] files = { "lincoln2.txt", "lincoln.txt", "twain2.txt", 
                "twain3.txt", "twain.txt", "eliot.txt", "tolstoy.txt" };
        
        SimpleDateFormat format =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss-SSS zzz yyyy");
        
/*        String[] testCase = {"aa",
                                "baa",
                                "quickly",
                                "monitor",
                                "frontage",
                                "beast",
                                "feast",
                                "obsequious",
                                "obstetrics",
                                "feast",
                                "alphabet",
                                "gnomic",
                                "triumph"
                            };  
*/  
        System.out.println("---------TEST MERGE W ARRAY ------------");
        for (int x = 0; x < files.length; x++) {
            String[] testCase = readFile(files[x]);
            

            long start = System.currentTimeMillis();
            Date now = new Date(start);
            System.out.println("File: " + files[x]);
            System.out.println("Total words: " + testCase.length);
            System.out.println("begin test: " + format.format(now));
            MergeTest merge = new MergeTest();
            String[] sortTest = merge.mergeSort(testCase);
            long end = System.currentTimeMillis();
            now = new Date(end);
            System.out.println("end test: " + format.format(now));
            System.out.println("total time: " + (end - start));
            System.out.println("--------------------------\n\n");
        }
        


        
        /*
        System.out.print("\nbefore: {");
        for (int x = 0; x < testCase.length; x++) {
            System.out.println(testCase[x] + " ");
        }
        System.out.println("} ");
        
        System.out.print("after: {");
        for (int x = 0; x < sortTest.length; x++) {
            System.out.println(sortTest[x] + " ");
        }
        System.out.println("} ");  
*/
        System.exit(0);

    }
    
    public String[] mergeSort(String[] list) {
        String [] sorted = new String[list.length];
        if (list.length == 1) {
            sorted = list;
        } else {
            int mid = list.length/2;
            String[] left = null; 
            String[] right = null;
            if ((list.length % 2) == 0) {
                left = new String[list.length/2];
                right = new String[list.length/2];
            } else {  // 
                left = new String[list.length/2];
                right = new String[(list.length/2)+1];
            }
            int x=0;
            int y=0;
            for ( ; x < mid; x++) {
                left[x] = list[x];
            }
            for ( ; x < list.length; x++) {
                right[y++] = list[x];
            }
            left = mergeSort(left);
            right = mergeSort(right);
            //sorted = merge(left,right);
            sorted = mergeArray(left,right);
        }
        
        return sorted;
    }
    
    private String[] merge(String[] left, String[] right) {
        List<String> merged = new ArrayList<String>();
        List<String> lstLeft = new ArrayList<String>(Arrays.asList(left));
        List<String> lstRight = new ArrayList<String>(Arrays.asList(right));
        int comp = 0;
        while (!lstLeft.isEmpty() || !lstRight.isEmpty()) {
            if (lstLeft.isEmpty()) {
                merged.add(lstRight.remove(0));
            } else if (lstRight.isEmpty()) {
                merged.add(lstLeft.remove(0));
            } else { // they both have elements, compare them
                comp = lstLeft.get(0).compareTo(lstRight.get(0));
                if (comp > 0) {
                    merged.add(lstRight.remove(0));
                   
                } else if (comp < 0) {
                    merged.add(lstLeft.remove(0));
                } else { // they're equal -- just put one in
                    merged.add(lstLeft.remove(0));
                }
            }
            
        }
        return merged.toArray(new String[merged.size()]);
    }
    
    private String[] mergeArray(String[] left, String[] right) {
        String[] merged = new String[left.length+right.length];
        int lIndex = 0;
        int rIndex = 0;
        int mIndex = 0;
        int comp = 0;
        while (lIndex < left.length || rIndex < right.length) {
            if (lIndex == left.length) {
                merged[mIndex++] = right[rIndex++];
            } else if (rIndex == right.length) {
                merged[mIndex++] = left[lIndex++];
            } else {  // they both have elements. Compare them
                comp = left[lIndex].compareTo(right[rIndex]);
                if (comp > 0) {
                    merged[mIndex++] = right[rIndex++];
                } else if (comp < 0) {
                    merged[mIndex++] = left[lIndex++];
                } else { // they're equal
                    merged[mIndex++] = left[lIndex++];
                }
            }   
        }
        return merged;
    }
    
    public static String[] readFile(String filename) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(filename));
        List<String> testList = new ArrayList<String>();
        String[] sample = null;
        String l;
        while ((l = inputStream.readLine()) != null) {
            sample = l.split(" ");
            for (int x=0; x < sample.length; x++) {
                testList.add(sample[x]);
            }
        }
        inputStream.close();
        return testList.toArray(new String[testList.size()]);
    }
}
