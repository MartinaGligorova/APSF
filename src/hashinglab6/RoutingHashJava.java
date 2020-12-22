package hashinglab6;

// class MapEntry
// class SLLNode -> CBHT e niza od SLL's
// class CBHT

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RoutingHashJava implements Comparable<RoutingHashJava> {

    String element;

    public RoutingHashJava(String element) {
        this.element = element;
    }

    public int hashCode() {
        return element.hashCode();
    }

    @Override
    public int compareTo(RoutingHashJava odVlez) {
        return this.element.compareTo(odVlez.element);
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());

        // za m od chas po av. notes kako da se napishe -> (int (N/0.5))

        CBHT<String, String> tabela = new CBHT<>(19);

        String line;
        String value;
        int brojac;

        for (int i = 0; i < N; i++) {
            line = bf.readLine();
            String[] pom = line.split(" ");
            line = pom[0];
            // popolni ja hash table
            value = bf.readLine();
            String[] pom1 = value.split(",");


            // sredi kako ke se chita value - izvadi gi poslednite 8bits t.e val posle poslednata tocka
            for (int j = 0; j < pom1.length; j++) {


                for (int z = pom1[j].length() - 1; z >= 0; z--) {
                    brojac = 0;
                    // char c = value.charAt(z);

                    // gi otkinuvame poslednite 8 bita od value
                    if (pom1[j].charAt(z) != '.') {
                        brojac++;
                        // zameni string vo pom1[j] so string so nova dolzhina
                        pom1[j] = pom1[j].substring(0, pom1[j].length() - brojac);
                    } else if (pom1[j].charAt(z) == '.') {
                        tabela.insert(line, pom1[j]);
                        break;
                    }

                }

            }

        }

        // System.out.println(tabela);
        int M = Integer.parseInt(bf.readLine());

        for (int i = 0; i < M; i++) {
            String ruter = bf.readLine();
            String[] podniza = ruter.split(" ");
            // novObjekt = podniza[0];
            // novObjekt = new RoutingHashJava(podniza[0]);
            ruter = podniza[0];
            String mrezhaDoKojaImaPristap = bf.readLine();
            String[] podniza1 = mrezhaDoKojaImaPristap.split(" ");


            // trim na mrezhata
            // brojac = 0;

            // sredi kako ke se chita value - izvadi gi poslednite 8bits t.e val posle poslednata tocka
            for (int j = 0; j < podniza1.length; j++) {

                for (int z = mrezhaDoKojaImaPristap.length() - 1; z >= 0; z--) {


                    // gi otkinuvame poslednite 8 bita od value
                    if (mrezhaDoKojaImaPristap.charAt(z) != '.') {
                        brojac = 0;
                        brojac++;

                        mrezhaDoKojaImaPristap = mrezhaDoKojaImaPristap.substring(0, mrezhaDoKojaImaPristap.length() - brojac);
                    } else if (mrezhaDoKojaImaPristap.charAt(z) == '.') {
                        break;
                    }
                }
            }
            // zavrshi trim

            SLLNode<MapEntry<String, String>> proveriKluch = tabela.search(ruter);


            if (proveriKluch != null) {

                while (proveriKluch.succ != null) {

                    if (!(proveriKluch.element.value.equals(mrezhaDoKojaImaPristap))) {
                        proveriKluch = proveriKluch.succ;
                    } else {
                        System.out.println("postoi");
                        break;
                    }
                }
                if (proveriKluch.succ == null) {
                    if (proveriKluch.element.value.equals(mrezhaDoKojaImaPristap)) {
                        System.out.println("postoi");
                    } else {
                        System.out.println("ne postoi");
                    }
                }
            }
            // proveriKluch == null
            else {
                System.out.println("ne postoi");
            }
        }
    }
}