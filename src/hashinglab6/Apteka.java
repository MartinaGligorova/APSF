package hashinglab6;
/*
MapEntry
CBHT - niza od SLL's - reverse previous changes made in the CBHT class.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Apteka implements Comparable<Apteka> {

    private String ime;
    char sym1, sym2, sym3;

    public Apteka() {
    }

    public Apteka(String ime) {
        sym1 = ime.charAt(0);
        sym2 = ime.charAt(1);
        sym3 = ime.charAt(2);
        this.ime = ime;
    }


    public int hashCode() {
        return (29 * (29 * (29 * 0 + ASCII(sym1)) + ASCII(sym2)) + ASCII(sym3)) % 102780;

    }

    public int ASCII(char c) {
        return (int) c;
    }

    @Override
    public int compareTo(Apteka lekOdVlez) {
        // maybe spored ime vrshime sporedba samo
        return this.ime.compareToIgnoreCase(lekOdVlez.ime);
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        // or Apteka, String
        CBHT<String, String> cbTabela = new CBHT<>((int) (N / 0.5));
        Apteka objectAp = new Apteka();

        // popolni ja hash tabelata
        for (int i = 0; i < N; i++) {

            String line = bf.readLine();


            String[] podniza = line.split(" ");
            objectAp.ime = podniza[0];
            for (int j = 1; j < podniza.length; j += 3) {

                // cbTabela.insert(objectAp.ime, pom);
                cbTabela.insert(objectAp.ime, podniza[j] + " " + podniza[j + 1] + " " + podniza[j + 2]);


            }
        }


        for (; ; ) {

            String lekOdVlez = bf.readLine();
            if (lekOdVlez.equalsIgnoreCase("KRAJ")) break;
            String lek;
            char proveriPrv = lekOdVlez.charAt(0);
            char proveriVtor = lekOdVlez.charAt(1);

            if (Character.isLowerCase(proveriPrv)) {
                lek = lekOdVlez.toUpperCase();
            } else if (Character.isUpperCase(proveriPrv) && Character.isLowerCase(proveriVtor)) {
                lek = lekOdVlez.toLowerCase();
            } else {
                lek = lekOdVlez;
            }

            int kolichina = Integer.parseInt(bf.readLine());


            String[] podniza = lek.split(" ");
            Apteka objekt = new Apteka();
            objekt.ime = podniza[0];


            SLLNode<MapEntry<String, String>> pok = cbTabela.search(objekt.ime);

            if (pok != null) {

                // Print medicine name in uppercase letters

                String imeNaLek = pok.element.key.toUpperCase();
                System.out.println(imeNaLek);

                // store (1/0) 1 - positive list, 0 - negative list

                char cuvajPN = pok.element.value.charAt(0);
                String cena = "";

                if (pok.element.value.charAt(0) == '0') {
                    System.out.println("NEG");
                } else {
                    System.out.println("POZ");
                }

                // extracting price of products

                for (int j = 2; j < pok.element.value.length(); j++) {
                    char c = pok.element.value.charAt(j);
                    if (Character.isWhitespace(c)) break;
                    System.out.print(c);

                    // cuvaj cena

                    cena += String.valueOf(c);

                }

                System.out.print("\n");

                String s = "";
                int brojac = 0;


                // extracting amount of products in storage.
                char c = pok.element.value.charAt(pok.element.value.length() - 1);
                if (Character.isWhitespace(c)) break;
                while (!(Character.isWhitespace(c))) {

                    s += (String.valueOf(c));
                    brojac++;

                    c = pok.element.value.charAt(pok.element.value.length() - 1 - brojac);

                }
                StringBuilder obj = new StringBuilder();
                obj.append(s);
                obj = obj.reverse();

                int value = Integer.parseInt(String.valueOf(obj));

                if (kolichina > value) {
                    System.out.println(value);
                    System.out.println("Nema dovolno lekovi");

                } else {
                    System.out.println(value);
                    value = value - kolichina;
                    String nov = String.valueOf(value);
                    cbTabela.insert(objekt.ime, cuvajPN + " " + cena + " " + nov);
                    System.out.println("Napravena naracka");

                }
            } else {
                // ne postoi lek
                System.out.println("Nema takov lek");
            }
        }
    }
}
