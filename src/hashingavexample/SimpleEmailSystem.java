package hashingavexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


// class MapEntry
class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry(K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo(K that) {
        // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
        MapEntry<K, E> other = (MapEntry<K, E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString() {
        return "<" + key + "," + value + ">";
    }
}

// class CBHT - niza od listi - koficka implementirana so ednostr. povrzana lista
// SLLNode class za def. na jazli vo SLL - import it
class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}



public class SimpleEmailSystem implements Comparable<SimpleEmailSystem> {
    // key od tip SimpleEmailSystem - sporedlivi megju sebe - kako e pretstaven kluchot?

    // klucot e sostaven od naslov, datum i vreme na poraka

    String naslov;
    String datumVreme;
    Category kategorija;



    public SimpleEmailSystem(String naslovVlez, String datumVremeVlez) {
        this.naslov = naslovVlez;
        this.datumVreme = datumVremeVlez;
    }

    // konstructor
    public SimpleEmailSystem(String naslov, String datumVreme, Category _kategorija) {
        this.naslov = naslov;
        this.datumVreme = datumVreme;
        this.kategorija = _kategorija;
    }



    public int hashCode() {
        // hesh funkcija spored sto ke go mapirash klucot vo celobrojna vrednost - creativity
        // mozes del od klucot...mozes celiot kluc(za da izbegnes premn kolizii)
        // zashteda na vreme

        return (naslov + datumVreme).hashCode();
        // znaci translacijata vo koficka - indeks od nizata e spored naslovot idatumot i vremeto NE I KATEGORIJATA!!
    }


    @Override
    public int compareTo(SimpleEmailSystem objInput) {
        // kako se sporeduvaat keys? / naslov i datum i vreme e klucot, kategorija e samo dodadeno za pristap
        if (this.kategorija.equals(objInput.kategorija) && this.datumVreme.equals(objInput.datumVreme)) {
            return 0;
        } else
            return 1;
    }

    public String toString() {
        return naslov + " " + datumVreme + " " + kategorija;
    }

    @Override
    public boolean equals(Object that) {
        SimpleEmailSystem other = (SimpleEmailSystem) that;
        return ((this.naslov.equals(other.naslov)) && (this.datumVreme.equals(other.datumVreme)));
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // broj na poraki vo sistemot
        int N = Integer.parseInt(bf.readLine());
        SimpleEmailSystem kluch;
        // izgradi CBHT - citaj od vlez i vmetnuvaj vo niza od listi
        CBHT<SimpleEmailSystem, Integer> tabela = new CBHT<>((int) (N / 0.5));
        // znaci klucot e od tip SimpleEmailSystem - prof stavil i category vo kluchot kako unique identifier na porakata, a
        // brojot na izvrsheni komandi da e value
        for (int i = 0; i < N; i++) {
            // citaj od vlez
            String line = bf.readLine();
            // podeli go input vo podniza od strings naslov, datum, vreme, kategorija
            String[] podniza = line.split(" ");
            // ova se strings nasiot key e SimpleEmailSystem objc. pa kreiraj takov objekt
            String naslov = podniza[0];
            String datumVreme = podniza[1] + " " + podniza[2];
            kluch = new SimpleEmailSystem(naslov, datumVreme, Category.valueOf(podniza[3]));
            tabela.insert(kluch, 0);
            // inicijalno site poraki imaat 0 komandi izvrsheni - popolni ja tabelata
        }

        int H = Integer.parseInt(bf.readLine());
        // broj na komandi od vlez
        int brojKomandi; // brojach
        String komanda;

        SimpleEmailSystem kluch2;
        for (int j = 0; j < H; j++) {
            // procitaj ja komandata
            String line = bf.readLine();
            String[] podniza = line.split(" ");
            komanda = podniza[0];
            // najdi go elementot vo HT - ako postoi pred da izvrsis komandi

            // 2 stringa na vlez prima naslov i datumiVreme
            SLLNode<MapEntry<SimpleEmailSystem, Integer>> pokazuvac1 = tabela.search(new SimpleEmailSystem(podniza[1], podniza[2] + " " + podniza[3]));
            // prebaraj vo tabela dali postoi elementot
            if (pokazuvac1 == null) {
                continue;
                // ne postoi takva poraka vo sistemot prodolzi da citas komandi
            }

            // pronajden jazol - pristapi kluch i value na MapEntry-to
            kluch2 = pokazuvac1.element.key; // zemi kluchot celosen so category od toj jazol VO HT (kaj sto ima i kategorii)
            brojKomandi = pokazuvac1.element.value; // zemi go brojot na komandi vo toj jazol


            // dokolku postoi procitaj komanda -> i update value + kategorija (staj ja kategorijata kako del od string)
            if (komanda.equals("UNREAD_MESSAGE") && kluch2.kategorija == Category.READ) {
                // smeni kategorija na kluchot - znachi si pristapuvas do kluchevi etc
                kluch2.kategorija = Category.UNREAD;
            } else if (komanda.equals("READ_MESSAGE") && kluch2.kategorija == Category.UNREAD) {
                kluch2.kategorija = Category.READ;
            } else if (komanda.equals("DELETE_MESSAGE") && kluch2.kategorija == Category.READ || kluch2.kategorija == Category.UNREAD) {
                kluch2.kategorija = Category.TRASH;
            } else if (komanda.equals("RESTORE_MESSAGE") && kluch2.kategorija == Category.TRASH) {
                kluch2.kategorija = Category.READ;
            } else
                // ako e nekoja dr komanda
                continue;

            brojKomandi++;
            // value e integer taka da vnesi go noviot broj na komandi
            tabela.insert(kluch2, brojKomandi);
        }

        // sledno brojot na poraki za koi sakame da ispecatime kategorija i broj na komandi - value od HT
        int P = Integer.parseInt(bf.readLine());
        SimpleEmailSystem obj;
        for (int k = 0; k < P; k++) {
            String line = bf.readLine();
            String[] podniza = line.split(" ");
            // heshiranjeto - translacija od kluc vo index od niza vo hesh tabela se vrsese samo preku naslov i datumvreme
            String naslovVlez = podniza[0];
            String datumVremeVlez = podniza[1] + " " + podniza[2];

            obj = new SimpleEmailSystem(naslovVlez, datumVremeVlez);

            SLLNode<MapEntry<SimpleEmailSystem, Integer>> najdi = tabela.search(obj); // spored kluch
            // hash funkcija od klucot - naslovot i datum i vreme --> koficka
            if(najdi == null)
                continue;

            System.out.println(najdi.element.key.kategorija + " " + najdi.element.value);
        }
    }
}

enum Category {
    // klasa - sodrzhi konstanti
    READ, UNREAD, TRASH;
}
