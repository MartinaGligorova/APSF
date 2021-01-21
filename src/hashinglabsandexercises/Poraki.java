package hashinglabsandexercises;
// MapEntry, SLLNode, CBHT

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

enum Status {
    READ, UNREAD, TRASH;
}

public class Poraki implements Comparable<Poraki> {

    String naslov;
    String datumVreme;
    Status _status;

    public Poraki(String naslov, String datumVreme, Status _status) {
        this.naslov = naslov;
        this.datumVreme = datumVreme;
        this._status = _status;
    }

    public Poraki(String naslov, String datumVreme) {
        this.naslov = naslov;
        this.datumVreme = datumVreme;
    }

    public int hashCode() {
        // do koficka na unikatna poraka preku naslov+datumVreme toa e key..
        return (naslov + datumVreme).hashCode();
    }

    public String toString() {
        return naslov + " " + datumVreme + " " + _status;
    }

    @Override
    public int compareTo(Poraki por) {
        if (this.datumVreme.compareTo(por.datumVreme) == 0 && this._status.compareTo(por._status) == 0) {
            // unikatno e sostojbata i vremeto vo koe pristignala - vo isto vreme ne smee so ist naslov
            return 0;
        } else
            return 1;
    }


    @Override
    public boolean equals(Object that) {
        // kako gi sporeduvas klucevite samo so datumVreme i naslov sto sozdrzat?
        Poraki obj = (Poraki) that; // kastiras obj. od vlez kon tip Poraki
        return this.naslov.equals(((Poraki) that).naslov) && this.datumVreme.equals(((Poraki) that).datumVreme);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        CBHT<Poraki, Integer> tabela = new CBHT<>(20);

        Poraki poraka;

        for (int i = 0; i < N; i++) {
            String line = bf.readLine();
            String[] podniza = line.split(" ");

            String naslov = podniza[0];
            String datumVr = podniza[1] + " " + podniza[2];

            // toString metodot bese valuable za kako se reprezentira objektite vo String format hmmmmm ->
            // zza vo HT da ima -> String kako key namesto mush

            poraka = new Poraki(naslov, datumVr, Status.valueOf(podniza[3]));

            tabela.insert(poraka, 0);
        }
        // sledno broj na poraki na koi ke im se promeni kategorijata - + brojot na komandi sto gi imame
        int M = Integer.parseInt(bf.readLine());
        for (int j = 0; j < M; j++) {
            String line = bf.readLine();
            String[] podniza = line.split(" ");

            String naslov = podniza[0];
            String datumVreme = podniza[1] + " " + podniza[2];
            poraka = new Poraki(naslov, datumVreme);

            SLLNode<MapEntry<Poraki, Integer>> pokazuvac = tabela.search(poraka);
            //pokazuvac.element.key._status = Status.valueOf(podniza[3]);
            // smeni go statusot na elementot
            //pokazuvac.element.value++; // increment value i.e broj na operacii izvrisheni
            if (pokazuvac == null) {
                continue;
            }

            if (podniza[3].equals("DELETE")) {
                pokazuvac.element.key._status = Status.TRASH;
                pokazuvac.element.value++;
            } else if (podniza[3].equals("UNREAD")) {
                pokazuvac.element.key._status = Status.UNREAD;
                pokazuvac.element.value++;
            } else if (podniza[3].equals("READ")) {
                pokazuvac.element.key._status = Status.READ;
                pokazuvac.element.value++;
            } else if (podniza[3].equals("RECOVER")) {
                pokazuvac.element.key._status = Status.READ;
                pokazuvac.element.value++;
            } else {
                continue;
            }
        }

        // sledno brojot na poraki za koi sakame da go znaeme statusot i broj na promeni
        int H = Integer.parseInt(bf.readLine());
        for (int i = 0; i < H; i++) {
            String line = bf.readLine();
            String[] podn = line.split(" ");
            String naslov = podn[0];
            String dateTime = podn[1] + " " + podn[2];
            poraka = new Poraki(naslov, dateTime);
            SLLNode<MapEntry<Poraki, Integer>> pokazuv = tabela.search(poraka);
            if(pokazuv == null)
                continue;

            System.out.println(pokazuv.element.key._status + " " + pokazuv.element.value);
        }

    }
}
