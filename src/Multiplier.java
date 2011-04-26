import java.util.Scanner;


// the problem TMUL in SPOJ states that
// input can be up to 1000 digits in length --
// clearly more than a long can hold without 
// overflow. 
// need implementation of Schönhage-Strassen algorithm
// an FFT that takes O(n*logN*loglogN) complexity
// apparently Knuth vol. 2 describes one.
//.... or we just leave this one alone right now :)
public class Multiplier {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner (System.in);
        long n = sc.nextInt();
        long x;
        long y;
        for (int i=0; i<=n; i++) {
            x = sc.nextLong();
            y = sc.nextLong();
            long q = x * y;
            System.out.println(q);
        }
    }
}
