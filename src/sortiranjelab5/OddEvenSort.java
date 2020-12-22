package sortiranjelab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OddEvenSort {

    public static <T extends Comparable<? super T>> void insertionSort(T[] array) {

        T key;
        int begin = 0;
        int end = array.length - 1;
        int i;

        for (int index = begin + 1; index <= end; index++) {
            key = array[index];
            i = index - 1;
            while (i >= begin && array[i].compareTo(key) > 0) {
                array[i + 1] = array[i];
                i = i - 1;
            }
            array[i + 1] = key;
        }
    }

    public static void oddEvenSort(Integer[] a, int n) {
        // Vasiot kod tuka

        insertionSort(a);


        Integer[] temp = new Integer[n];
        int begin = 0;
        int end = a.length - 1;
        int kOdKraj = n - 1;
        int kodStart = 0;

        for (int i = begin; i <= end; i++) {
            if (a[i] % 2 == 0) {

                temp[kOdKraj] = a[i];
                kOdKraj--;

            } else if (a[i] % 2 != 0) {

                temp[kodStart] = a[i];
                kodStart++;
            }
        }

        for (int j = 0; j < temp.length; j++) {
            a[j] = temp[j];
        }

    }

    public static void main(String[] args) throws IOException {
        int i;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s = stdin.readLine();
        int n = Integer.parseInt(s);


        s = stdin.readLine();
        String[] pom = s.split(" ");
        Integer[] a = new Integer[n];
        for (i = 0; i < n; i++) {
            a[i] = Integer.parseInt(pom[i]);
        }

        oddEvenSort(a, n);

        for (i = 0; i < n; i++)
            System.out.print(a[i] + " ");


    }
}