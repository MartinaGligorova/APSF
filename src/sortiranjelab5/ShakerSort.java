package sortiranjelab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShakerSort {


    public static <Integer extends Comparable<? super Integer>> void shakerSort(Integer[] array, int n) {

        int begin = 0;
        int end = array.length - 1;
        Integer temp;
        boolean flipped = false;


        // varijacija na bubble sort


        for (int i = begin; i <= end; i++) {
            // turni najmal na pocetok
            flipped = false;
            for (int j = array.length - 1; j > i; j--) {
                // turni najmal na pocetok
                if (array[j - 1].compareTo(array[j]) > 0) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                    flipped = true;
                }
            }

            for (int k = 0; k < array.length; k++) {
                System.out.print(array[k] + " ");
            }
            System.out.println();

            // turni najgolem na kraj

            for (int l = 1; l <= end; l++) {
                if (array[l - 1].compareTo(array[l]) > 0) {
                    temp = array[l - 1];
                    array[l - 1] = array[l];
                    array[l] = temp;
                    flipped = true;
                }
            }

            for (int k = 0; k < array.length; k++) {
                System.out.print(array[k] + " ");
            }
            System.out.println();
            if (!flipped) break;
        }
    }

    public static void main(String[] args) throws IOException {

        int i;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        int n = Integer.parseInt(s);


        s = bf.readLine();
        String[] pom = s.split(" ");
        Integer[] array = new Integer[n];


        for (i = 0; i < n; i++) {
            array[i] = Integer.parseInt(pom[i]);
        }

        shakerSort(array, n);

    }
}