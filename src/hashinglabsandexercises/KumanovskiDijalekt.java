package hashinglabsandexercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class KumanovskiDijalekt {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        //Vasiot kod tuka

        CBHT<String, String> tabela = new CBHT<>(31);
        String dijalekt;
        String mkd;
        String line;

        for (int i = 0; i < N; i++) {
            line = br.readLine();
            String[] podniza = line.split(" ");

            dijalekt = podniza[0];
            mkd = podniza[1];

            tabela.insert(dijalekt, mkd);

        }


        String tekst = br.readLine();
        String ret = "";
        String nov = "";
        StringBuilder sb = new StringBuilder();
        String[] podniza = tekst.split(" ");
        for (int j = 0; j < podniza.length; j++) {
            if (podniza[j].charAt(podniza[j].length() - 1) == '.' || podniza[j].charAt(podniza[j].length() - 1) == '?' ||
                    podniza[j].charAt(podniza[j].length() - 1) == ',' || podniza[j].charAt(podniza[j].length() - 1) == '!') {
                sb.append(podniza[j]);
                nov = podniza[j];
                sb.deleteCharAt(sb.length() - 1);
                podniza[j] = sb.toString();
                sb.delete(0, sb.length());

            }
            SLLNode<MapEntry<String, String>> pok = null;
            // dokolku prvata bukva na elementot e so golema bukva
            if (Character.isUpperCase(podniza[j].charAt(0))) {
                String bla = podniza[j];
                podniza[j] = podniza[j].toLowerCase();
                pok = tabela.search(podniza[j]);
                if (pok == null) {
                    podniza[j] = bla;
                    bla = "";
                } else {
                    String hm = pok.element.value;
                    hm = hm.substring(0, 1).toUpperCase() + hm.substring(1);
                    sb.append(hm);
                    podniza[j] = sb.toString();
                    ret += podniza[j] + " ";
                    sb.delete(0, sb.length());
                    continue;
                }
            }

            pok = tabela.search(podniza[j]);


            if (pok == null) {
                if (nov != "") {
                    if (nov.charAt(nov.length() - 1) == '.' || nov.charAt(nov.length() - 1) == ',' || nov.charAt(nov.length() - 1) == '?'
                            || nov.charAt(nov.length() - 1) == '!') {
                        ret += podniza[j] + nov.charAt(nov.length() - 1) + " ";
                        nov = "";
                        continue;
                    }
                } else {
                    ret += podniza[j] + " ";
                    continue;
                }

                // ako elementot ne se naogja vo rechnikot


            }

            if (nov != "") {
                if (nov.charAt(nov.length() - 1) == '.' || nov.charAt(nov.length() - 1) == ',' || nov.charAt(nov.length() - 1) == '?'
                        || nov.charAt(nov.length() - 1) == '!') {
                    ret += pok.element.value + nov.charAt(nov.length() - 1) + " ";
                    nov = "";
                }
            } else {
                ret += pok.element.value + " ";

            }
        }

        System.out.println(ret);




    }


}
