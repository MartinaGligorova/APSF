package vovedvojavalab1;

import java.util.Scanner;

//вашиот код
abstract class Patuvanje {

    private String imeAgencija;
    private int cenaPatuvanje;

    public Patuvanje() {
    }

    public Patuvanje(String imeAgencija, int cenaPatuvanje) {
        this.imeAgencija = imeAgencija;
        this.cenaPatuvanje = cenaPatuvanje;
    }

    public String getImeAgencija() {
        return imeAgencija;
    }

    public int getCenaPatuvanje() {
        return cenaPatuvanje;
    }

    public abstract int vratiVremeVoDenovi();


    public static int vratiMinCena(Patuvanje[] niza, int n, Patuvanje zaSporedba) {

        int minCena = 0;
        for (int i = 0; i < n; i++) {
            if (niza[i].vratiVremeVoDenovi() > zaSporedba.vratiVremeVoDenovi()) {
                minCena = niza[i].getCenaPatuvanje();
                break;
            }
        }
        if (minCena != 0) {
            for (int i = 0; i < n; i++) {
                if (niza[i].vratiVremeVoDenovi() > zaSporedba.vratiVremeVoDenovi() && minCena > niza[i].getCenaPatuvanje()) {
                    minCena = niza[i].getCenaPatuvanje();
                }
            }
        }
        return minCena;
    }
}

class PraznicnoPatuvanje extends Patuvanje {

    private int pochetenDatum;
    private int mesecPocheten;
    private int kraenDatum;
    private int mesecKraen;


    public PraznicnoPatuvanje() {
    } // default

    public PraznicnoPatuvanje(String imeAgencija, int cenaPatuvanje, int pochetenDatum, int mesecPocheten,
                              int kraenDatum, int mesecKraen) {
        super(imeAgencija, cenaPatuvanje);

        try {
            if (mesecPocheten > mesecKraen)
                throw new Exception("Iskluchok za mesec");
            else if (mesecPocheten == mesecKraen && kraenDatum < pochetenDatum) {
                throw new Exception("Iskluchok za den");
            } else {
                this.pochetenDatum = pochetenDatum;
                this.mesecPocheten = mesecPocheten;
                this.kraenDatum = kraenDatum;
                this.mesecKraen = mesecKraen;
            }
        } catch (Exception e) {
            this.pochetenDatum = kraenDatum;
            this.mesecPocheten = mesecKraen;
            this.kraenDatum = pochetenDatum;
            this.mesecKraen = mesecPocheten;
            System.out.println("Iskluchok");
        }
    }

    public int getMesecPocheten() {
        return mesecPocheten;
    }

    @Override
    public int vratiVremeVoDenovi() {
        if (mesecPocheten == mesecKraen) {
            return kraenDatum - pochetenDatum;
        } else {
            return (30 - pochetenDatum) + ((mesecKraen - mesecPocheten - 1) * 30) + kraenDatum;
        }
    }
}

class GodishenOdmor extends Patuvanje {

    private int denoviNaTraenje;

    public GodishenOdmor() {
    }

    public GodishenOdmor(String imeAgencija, int cenaPatuvanje, int denoviNaTraenje) {
        super(imeAgencija, cenaPatuvanje - 1000);

        this.denoviNaTraenje = denoviNaTraenje;
    }

    @Override
    public int vratiVremeVoDenovi() { //definiranje metod
        return denoviNaTraenje;
    }
}

public class Test {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        Patuvanje[] nizaPatuvanje = new Patuvanje[n];

        for (int i = 0; i < n; i++) {
            int tip = in.nextInt();
            if (tip == 0) {
                String ime = in.next();
                int cena = in.nextInt();
                int vreme = in.nextInt();
                nizaPatuvanje[i] = new GodishenOdmor(ime, cena, vreme);
            } else {
                String ime = in.next();
                int cena = in.nextInt();
                int pocD = in.nextInt();
                int pocM = in.nextInt();
                int krajD = in.nextInt();
                int krajM = in.nextInt();
                nizaPatuvanje[i] = new PraznicnoPatuvanje(ime, cena, pocD, pocM, krajD, krajM);

            }
        }

        // решение на барање 1

        for (int i = 0; i < n; i++) {
            if (nizaPatuvanje[i] instanceof PraznicnoPatuvanje) { //samo za elementite od tip PP
                if (((PraznicnoPatuvanje) nizaPatuvanje[i]).getMesecPocheten() == 6) {
                    System.out.print(nizaPatuvanje[i].getImeAgencija() + " ");
                }
            }
        }
        System.out.println();

        // решение на барање 2

        float sumaNaVremetraenja = 0;
        for (int i = 0; i < n; i++) {
            sumaNaVremetraenja += nizaPatuvanje[i].vratiVremeVoDenovi();
        }
        float prosek = sumaNaVremetraenja / n;
        System.out.printf("%.1f\n", prosek);

        //решение на барање 3

        String ime = in.next();
        int cena = in.nextInt();
        int vremetraenje = in.nextInt();
        GodishenOdmor odmor = new GodishenOdmor(ime, cena, vremetraenje);

        //решение на барање 4

        int minCena = Patuvanje.vratiMinCena(nizaPatuvanje, n, odmor);
        System.out.println(minCena);
    }
}
