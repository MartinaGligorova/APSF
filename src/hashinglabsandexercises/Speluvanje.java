package hashinglabsandexercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class Zbor implements Comparable<Zbor> {
    String zbor;

    public Zbor(String zbor) {
        this.zbor = zbor;
    }

    @Override
    public boolean equals(Object obj) {
        Zbor pom = (Zbor) obj;
        return this.zbor.equals(pom.zbor);
    }

    @Override
    public int hashCode() {
        /*
         *
         * Vie ja kreirate hesh funkcijata
         *
         */
        return zbor.hashCode();
    }

    @Override
    public String toString() {
        return zbor;
    }

    @Override
    public int compareTo(Zbor arg0) {
        return zbor.compareTo(arg0.zbor);
    }
}

public class Speluvanje {
    public static void main(String[] args) throws IOException {
        OBHT<Zbor, String> tabela;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        //---Vie odluchete za goleminata na hesh tabelata----
        tabela = new OBHT<Zbor, String>((int) (N / 0.5));

        /*
         *
         * Vashiot kod tuka....
         *
         */

        String line;
        Zbor zborObj;
        // za prviot test primer - recnik od ang zborovi
        for (int i = 0; i < N; i++) {
            line = br.readLine();
            zborObj = new Zbor(line);
            tabela.insert(zborObj, line);
            // istoto za value pecati go
        }
        // recnik lowercase?
        StringBuilder sb = new StringBuilder();
        String vlez = br.readLine();
        String nov1 = "";
        String nov = "";
        String[] podniza = vlez.split(" ");
        for (int i = 0; i < podniza.length; i++) {
            if (podniza[i].charAt(podniza[i].length() - 1) == '.' || podniza[i].charAt(podniza[i].length() - 1) == '!' ||
                    podniza[i].charAt(podniza[i].length() - 1) == '?' || podniza[i].charAt(podniza[i].length() - 1) == ',') {
                sb.append(podniza[i]);
                // nov1 = podniza[i];
                sb = sb.deleteCharAt(sb.length() - 1);
                podniza[i] = sb.toString();
                sb.delete(0, sb.length());
            }

            String str = podniza[i];


            zborObj = new Zbor(podniza[i].toLowerCase());
            int indexKoficka = tabela.search1(zborObj);
            if (indexKoficka != OBHT.NONE) {
                if (i != podniza.length - 1) {
                    continue;
                }
                if (nov.equals("")) {
                    System.out.println("Bravo");
                } else
                    continue;

            } else {
                nov = str;
                if (nov.equals("")) {
                    System.out.println("Bravo");
                } else
                    System.out.println(str);
                continue;
            }
        }
    }
}