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

class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K, E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Napishete ja vie HASH FUNKCIJATA
        return Math.abs(key.hashCode()) % buckets.length;
    }


    public SLLNode<MapEntry<K, E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {        // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
    }

    // modified - added method 2
    public void insert2(K key, E val) {        // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (val.equals(((MapEntry<K, E>) curr.element).value)) {
                // Make newEntry replace the existing entry ...
                // dokolku values se ednakvi i.e iminjata
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        if (buckets[b] == null) {
            buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
        } else {
            SLLNode<MapEntry<K, E>> pok = buckets[b];
            while (pok.succ != null) {
                pok = pok.succ;
            }
            // pok.succ = null
            pok.succ = new SLLNode<>(newEntry, pok.succ);
        }

    }

    // modified - added method
    public void insert1(K key, E val) {        // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (val.equals(((MapEntry<K, E>) curr.element).value)) {
                // Make newEntry replace the existing entry ...
                // dokolku values == togash overwrite
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
    }


    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}


public class SimpleEmailSystem implements Comparable<SimpleEmailSystem> {


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
        return (naslov + datumVreme).hashCode();

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

        CBHT<SimpleEmailSystem, Integer> tabela = new CBHT<>((int) (N / 0.5));
        for (int i = 0; i < N; i++) {

            String line = bf.readLine();
            String[] podniza = line.split(" ");
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

            String line = bf.readLine();
            String[] podniza = line.split(" ");
            komanda = podniza[0];
            SLLNode<MapEntry<SimpleEmailSystem, Integer>> pokazuvac1 = tabela.search(new SimpleEmailSystem(podniza[1], podniza[2] + " " + podniza[3]));
            // prebaraj vo tabela dali postoi elementot
            if (pokazuvac1 == null) {
                continue;
                // ne postoi takva poraka vo sistemot prodolzi da citas komandi
            }

            // pronajden jazol - pristapi kluch i value na MapEntry-to
            kluch2 = pokazuvac1.element.key;
            brojKomandi = pokazuvac1.element.value;


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
            tabela.insert(kluch2, brojKomandi);
        }

        // brojot na poraki za koi sakame da ispecatime kategorija i broj na komandi
        int P = Integer.parseInt(bf.readLine());
        SimpleEmailSystem obj;
        for (int k = 0; k < P; k++) {
            String line = bf.readLine();
            String[] podniza = line.split(" ");
            String naslovVlez = podniza[0];
            String datumVremeVlez = podniza[1] + " " + podniza[2];

            obj = new SimpleEmailSystem(naslovVlez, datumVremeVlez);

            SLLNode<MapEntry<SimpleEmailSystem, Integer>> najdi = tabela.search(obj);
            if (najdi == null)
                continue;

            System.out.println(najdi.element.key.kategorija + " " + najdi.element.value);
        }
    }
}

enum Category {
    READ, UNREAD, TRASH;
}
