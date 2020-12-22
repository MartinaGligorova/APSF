package apstraktenpodattiplab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

class Gragjanin<E extends Comparable<E>> {
    private String imePrezime;
    private int lkarta, pasos, vozacka;

    public Gragjanin() {
    }

    public Gragjanin(String imePrezime, int lkarta, int pasos, int vozacka) {
        this.imePrezime = imePrezime;
        this.lkarta = lkarta;
        this.pasos = pasos;
        this.vozacka = vozacka;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public int getLkarta() {
        return lkarta;
    }

    public int getPasos() {
        return pasos;
    }

    public int getVozacka() {
        return vozacka;
    }


}

interface Queue<E> {

    // Elementi na redicata se objekti od proizvolen tip.

    // Metodi za pristap:

    public boolean isEmpty();
    // Vrakja true ako i samo ako redicata e prazena.

    public int size();
    // Ja vrakja dolzinata na redicata.

    public E peek();
    // Go vrakja elementot na vrvot t.e. pocetokot od redicata.

    // Metodi za transformacija:

    public void clear();
    // Ja prazni redicata.

    public void enqueue(E x);
    // Go dodava x na kraj od redicata.

    public E dequeue();
    // Go otstranuva i vrakja pochetniot element na redicata.

}


class LinkedQueue<E> implements Queue<E> {

    SLLNode<E> front, rear;
    int length;

    // Konstruktor ...

    public LinkedQueue() {
        clear();
    }

    public boolean isEmpty() {
        // Vrakja true ako i samo ako redicata e prazena.
        return (length == 0);
    }

    public int size() {
        // Ja vrakja dolzinata na redicata.
        return length;
    }

    public E peek() {
        // Go vrakja elementot na vrvot t.e. pocetokot od redicata.
        if (front == null)
            throw new NoSuchElementException();
        return front.element;
    }

    public void clear() {
        // Ja prazni redicata.
        front = rear = null;
        length = 0;
    }

    public void enqueue(E x) {
        // Go dodava x na kraj od redicata.
        SLLNode<E> latest = new SLLNode<E>(x, null);
        if (rear != null) {
            rear.succ = latest;
            rear = latest;
        } else
            front = rear = latest;
        length++;
    }

    public E dequeue() {
        // Go otstranuva i vrakja pochetniot element na redicata.
        if (front != null) {
            E frontmost = front.element;
            front = front.succ;
            if (front == null) rear = null;
            length--;
            return frontmost;
        } else
            throw new NoSuchElementException();
    }

    public String toString() {

        String pecati = new String();
        if (front != null) {
            SLLNode<E> tmp = front;
            pecati += tmp + "\n";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                pecati += tmp + "\n";
            }
        }
        return pecati;
    }

}


public class MVR {

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        LinkedQueue<Gragjanin> redicaLicnaKarta = new LinkedQueue<>();
        LinkedQueue<Gragjanin> redicaPasosh = new LinkedQueue<>();
        LinkedQueue<Gragjanin> redicaVozacka = new LinkedQueue<>();
        LinkedQueue<Gragjanin> rezultat = new LinkedQueue<>();


        for (int i = 1; i <= N; i++) {
            String imePrezime = bf.readLine();

            int lkart = Integer.parseInt(bf.readLine());
            int pasos = Integer.parseInt(bf.readLine());
            int vozacka = Integer.parseInt(bf.readLine());
            Gragjanin covek = new Gragjanin(imePrezime, lkart, pasos, vozacka);

            if (covek.getLkarta() == 1) {
                redicaLicnaKarta.enqueue(covek);
            }
            if (covek.getPasos() == 1 && covek.getLkarta() == 0) {
                redicaPasosh.enqueue(covek);
            }
            if (covek.getVozacka() == 1 && covek.getPasos() == 0 && covek.getLkarta() == 0) {
                redicaVozacka.enqueue(covek);
            }

        }
        Gragjanin dzirni = new Gragjanin();
        while (!redicaLicnaKarta.isEmpty()) {
            dzirni = redicaLicnaKarta.peek();
            if (dzirni.getPasos() == 0 && dzirni.getVozacka() == 0) {
                redicaLicnaKarta.dequeue();

                System.out.println(dzirni.getImePrezime());
            }
            if (dzirni.getPasos() == 1 && dzirni.getVozacka() == 1) {
                redicaPasosh.enqueue(redicaLicnaKarta.dequeue());
            }
            if (dzirni.getPasos() == 1 && dzirni.getVozacka() == 0) {
                redicaPasosh.enqueue(redicaLicnaKarta.dequeue());

            }
            if (dzirni.getPasos() == 0 && dzirni.getVozacka() == 1) {
                redicaVozacka.enqueue(redicaLicnaKarta.dequeue());
            }
        }
        while (!redicaPasosh.isEmpty()) {
            dzirni = redicaPasosh.peek();
            if (dzirni.getVozacka() == 0) {
                redicaPasosh.dequeue();

                System.out.println(dzirni.getImePrezime());
            }
            if (dzirni.getVozacka() == 1) {
                redicaVozacka.enqueue(redicaPasosh.dequeue());
            }
        }
        while (!redicaVozacka.isEmpty()) {
            dzirni = redicaVozacka.peek();
            redicaVozacka.dequeue();

            System.out.println(dzirni.getImePrezime());
        }
    }
}