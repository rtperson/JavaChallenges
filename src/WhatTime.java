
public class WhatTime {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long answer = 0;
        long term1 = 1L;
        long term2 = 2L;
        long fibo = term1 + term2;
        for (int count = 0; count < 31; count++) {
            if (count == 0) {
                System.out.println("fibo[" + (count+1) + "]: " + term1);
            } else if (count == 1) {
                System.out.println("fibo[" + (count+1) + "]: " + term2);
            } else {
                term1 = fibo;
                fibo += term2;
                term2 = term1;
                System.out.println("fibo[" + (count+1) + "]: " + fibo);
                if (fibo % 2 == 0) answer += fibo;
            }
        }
        System.out.println("answer: " + answer);
    }

}
