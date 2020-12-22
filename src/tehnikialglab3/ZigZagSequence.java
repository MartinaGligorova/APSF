package tehnikialglab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZigZagSequence {

    static int najdiNajdolgaCikCak(int a[]) {
        // Vasiot kod tuka

        //int rezultatDolzhina = 0;
        int brojac = 1;
        int brojacPlus = 0;
        int privremenBrojac = 0;
        if (a.length == 1 && a[0] != 0) {
            return 1;
        }
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] >= 0 && a[i + 1] >= 0 || a[i] <= 0 && a[i + 1] <= 0) {
                privremenBrojac = 1;
                brojac = 1;

            } else if (a[i] >= 0 && a[i + 1] < 0 || a[i] < 0 && a[i + 1] >= 0) {
                brojac++;
                if (brojac > brojacPlus)
                    brojacPlus = brojac;

            }

        }
        if (brojacPlus > privremenBrojac) {
            return brojacPlus;
        } else return privremenBrojac;

    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        int a[] = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(bf.readLine());
        }

        int rezultat = najdiNajdolgaCikCak(a);
        System.out.println(rezultat);

        bf.close();
    }
}