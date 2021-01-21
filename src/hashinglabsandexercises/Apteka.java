package hashinglabsandexercises;

// MapEntry, SLLNode, CBHT

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Apteka implements Comparable<Apteka> {

    String imeLek;
    String pozneg;
    int cena;
    // zaliha value?


    public Apteka(String _imeLek, String pozneg, int cena) {
        this.imeLek = _imeLek.toUpperCase();
        this.pozneg = pozneg;
        this.cena = cena;

    }

    public Apteka(String imeNaLek) {
        this.imeLek = imeNaLek;
    }

    public int hashCode() {
        return (29 * (29 * (29 * 0 + imeLek.charAt(0)) + imeLek.charAt(1)) + imeLek.charAt(2)) % 102780;
    }

    public String toString() {
        return imeLek + " " + pozneg + " " + cena;
    }

    @Override
    public boolean equals(Object that) {
        Apteka other = (Apteka) that;
        // modify podocna
        return this.imeLek.toUpperCase().compareTo(other.imeLek.toUpperCase()) == 0;
    }


    @Override
    public int compareTo(Apteka apteka) {
        // klucot se sporeduva spored ime
        return this.imeLek.toUpperCase().compareTo(apteka.imeLek.toUpperCase());
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        CBHT<Apteka, Integer> tabela = new CBHT<>((int) (N / 0.5));
        Apteka apteka;
        String line;

        for (int i = 0; i < N; i++) {
            line = bf.readLine();
            String[] podniza = line.split(" ");
            String imeNaLek = podniza[0];
            int pozneglista = Integer.parseInt(podniza[1]);
            String pozneg;
            if (pozneglista == 1) {
                pozneg = "POZ";
            } else {
                pozneg = "NEG";
            }
            int cena = Integer.parseInt(podniza[2]);
            apteka = new Apteka(imeNaLek, pozneg, cena);

            tabela.insert(apteka, Integer.parseInt(podniza[3]));
        }

        // proverka dali lek postoi vo tabela
        while (true) {

            line = bf.readLine();
            int kolicina = Integer.parseInt(bf.readLine());
            while (!line.equals("KRAJ")) {
                apteka = new Apteka(line.toUpperCase());
                SLLNode<MapEntry<Apteka, Integer>> pokazuvac = tabela.search(apteka);
                if (pokazuvac == null) {
                    System.out.println("Nema takov lek");
                    line = bf.readLine();
                    kolicina = Integer.parseInt(bf.readLine());
                    continue;
                }

                if (kolicina > pokazuvac.element.value) {
                    // ispecati go sepak lekot

                    System.out.print(pokazuvac.element.key.imeLek + "\n" + pokazuvac.element.key.pozneg + "\n" + pokazuvac.element.key.cena + "\n" +
                            pokazuvac.element.value + "\n");
                    System.out.println("Nema dovolno lekovi");
                    line = bf.readLine();
                    if (line.equals("KRAJ")) continue;

                    kolicina = Integer.parseInt(bf.readLine());
                    continue;


                }

                System.out.print(pokazuvac.element.key.imeLek + "\n" + pokazuvac.element.key.pozneg + "\n" + pokazuvac.element.key.cena + "\n" +
                        pokazuvac.element.value + "\n");
                System.out.println("Napravena naracka");
                pokazuvac.element.value = Math.abs(pokazuvac.element.value - kolicina);
                line = bf.readLine();
                if (line.equals("KRAJ")) continue;
                else {
                    kolicina = Integer.parseInt(bf.readLine());
                    continue;
                }
            }
            break;

        }

    }
}
