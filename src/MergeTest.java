import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MergeTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        SimpleDateFormat format =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss-SSS zzz yyyy");
        
        String[] testCase = readLincoln();
       
/*        String[] testCase = {"aa",
                "baa",
                "quickly",
                "monitor",
                "frontage",
                "beast",
                "feast",
                "obsequious",
                "obstetrics",
                "alphabet",
                "gnomic",
                "triumph"
        }; */
        long start = System.currentTimeMillis();
        Date now = new Date(start);
        System.out.println("Total words: " + testCase.length);
        System.out.println("begin test: " + format.format(now));
        MergeTest merge = new MergeTest();
        String[] sortTest = merge.mergeSort(testCase);
        long end = System.currentTimeMillis();
        now = new Date(end);
        System.out.println("end test: " + format.format(now));
        System.out.println("total time: " + (end - start));
/*        System.out.print("before: {");
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
            sorted = merge(left,right);
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
    
    private static String[] readLincoln() throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader("twain.txt"));
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
