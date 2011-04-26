import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 */

/**
 * @author rturnau
 *
 */
public class MyQuickSort {

	/**
	 * @param args
	 */
    public static void main(String[] args) throws IOException {
        SimpleDateFormat format =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss-SSS zzz yyyy");
        
        String[] testCase = readLincoln();
        
        //String[] testCase = readEvilArray(210202);
       
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
        }; 
*/
        FileWriter fw = new FileWriter("output.txt");
        fw.write("before: {");
        for (int x = 0; x < testCase.length; x++) {
        	fw.write(testCase[x] + " " + "\n");
        }
        fw.write("} \n");
        long start = System.currentTimeMillis();
        Date now = new Date(start);
        
        System.out.println("Total words: " + testCase.length);
        System.out.println("begin test: " + format.format(now));
        MyQuickSort qSort = new MyQuickSort();
        String[] sortTest = qSort.quickSort(testCase, 0, testCase.length-1);
        long end = System.currentTimeMillis();
        now = new Date(end);
        System.out.println("end test: " + format.format(now));
        System.out.println("total time: " + (end - start));

        
        
        fw.write("after: {");
        for (int x = 0; x < sortTest.length; x++) {
        	fw.write(sortTest[x] + " \n");
        }
        fw.write("} \n");


    }
    
    private String[] quickSort(String[] list, int low, int high) {
    	if (high <= low) return list;
    	int pivot =  partition(list, low, high);
    	quickSort(list, low, pivot-1);
    	quickSort(list, pivot+1, high);
    	
    	return list;
    }
    
    private int partition(String[] list, int low, int high) {
    	int i = low - 1;
    	int j = high;
    	while (i < high) {
    		while (list[++i].compareTo(list[high]) < 0) ; // do nothing
    		while (list[high].compareTo(list[--j]) < 0) {
    			if (j == low) break;
    		}
			if (i >= j) break;
			swap(list, i, j);
    	}
    	swap(list, i, high);
    	return i;
    }
    
    private void swap(String[] list, int i, int j) {
    	String swap = list[i];
    	list[i] = list[j];
    	list[j] = swap;
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
    
    /**
     * Create a sizeable array of words that are very alike -- a blatant
     * attempt to trigger the worst-case O(n^2) scenario with QuickSort.
     * NOTE: Doesn't work. It isn't evil enough. A true evil array must
     * be already sorted in reverse order. And even then you can defeat it
     * with a truly random partitioning strategy.
     * @param numWords
     * @return
     * @throws IOException
     */
    private static String[] readEvilArray(int numWords) throws IOException {
    	List<String> testList = new ArrayList<String>();
    	
    	int mod = 0;
    	String addWord = null;
    	for (int i = 0; i < numWords; i++) {
    		mod = i % 5;
    		
    		switch (mod) {
    		case 0:
    			addWord = "this";
    			break;
    		case 1:
    			addWord = "these";
    			break;
    		case 2:
    			addWord = "the";
    			break;
    		case 3:
    			addWord = "those";
    			break;
    		case 4: 
    			addWord = "thing";
    			break;
    		}
    		testList.add(addWord);
    	}
    	
    	return testList.toArray(new String[testList.size()]);
    }

}
