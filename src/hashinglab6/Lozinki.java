package hashinglab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// MapEntry class
// KAJ CBHT  kaj insert metodot e smeneto za staticko rutiranje - samo smeni nazad vo zavisnost so koja class rab.


public class Lozinki implements Comparable<Lozinki> {

    // redundant part

    String element;


    public Lozinki(String element) {
        this.element = element;
    }

    public int hashCode() {
        return element.charAt(0) - 'a';
    }

    @Override
    public int compareTo(Lozinki lozinki) {
        return this.element.compareTo(lozinki.element);
    }

    @Override
    public boolean equals(Object that) {
        Lozinki other = (Lozinki) that;
        if (other == null || !(other instanceof Lozinki))
            return false;
        else {
            return this.compareTo(other) == 0;
        }
    }


    public static void main(String[] args) throws IOException {
        // main e daden
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // citame strings od vlez
        int N = Integer.parseInt(br.readLine());
        CBHT<String, String> table = new CBHT<>(27);

        for (int i = 1; i <= N; i++) {
            // N korisnicki iminja i lozinki koi treba da se vnesat vo CBHT
            String imelozinka = br.readLine();
            String[] pom1 = imelozinka.split(" ");
            for (int j = 0; j < pom1.length; j += 2) {
                table.insert(pom1[j + 1], pom1[j]);
                // kluchot e banana value e ana pr.
            }
        }
        System.out.println();

        int brojac = 0;


        // System.out.print(table);

        // obidi za najava

        for (; ; ) {

            String imelozina = br.readLine();
            if (imelozina == null)
                break;
            // chitame od vlez
            String[] pom = imelozina.split(" ");

            for (int j = 0; j < pom.length; j += 2) {

                if (pom[j].equals("KRAJ")) break;
                SLLNode<MapEntry<String, String>> kluch = table.search(pom[j + 1]);
                // spored value od vlez prebaruvame
                // pokazuva kon jazol <kluch, element> pr. <banana, ana>

                if (kluch == null) {
                    System.out.println("Nenajaven");

                } else if (kluch.element.key.equals(pom[j + 1]) && kluch.element.value.equals(pom[j])) {
                    brojac++;
                    if (brojac > 1) {
                        break;
                    } else {
                        System.out.println("Najaven");
                    }


                } else if (!(kluch.element.key.equals(pom[j + 1]))) {
                    System.out.println("Nenajaven");

                } else if (!(kluch.element.value.equals(pom[j]))) {
                    System.out.println("Nenajaven");


                }
            }
        }
    }
}
