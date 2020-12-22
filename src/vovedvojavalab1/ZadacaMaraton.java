package vovedvojavalab1;

import java.util.Scanner;

interface IMaraton {

    Atleticar najdobroVreme();

    int atleticariOd(String s);
}

class Atleticar {

    private String ime;
    private int vozrast;
    private String pol;
    private double vremeNaIstrch;
    private String zemjaPoteklo;

    public Atleticar() {
    } // default constructor

    public Atleticar(String ime, String pol, int vozrast, double vremeNaIstrch, String zemjaPoteklo) {
        this.ime = ime;
        this.pol = pol;
        this.vozrast = vozrast;
        this.vremeNaIstrch = vremeNaIstrch;
        this.zemjaPoteklo = zemjaPoteklo;
    }

    public double getVremeNaIstrch() {
        return vremeNaIstrch;
    }

    public String getZemjaPoteklo() {
        return zemjaPoteklo;
    }

    @Override
    public String toString() {
        return ime + "\n" + vozrast + "\n" + zemjaPoteklo + "\n" + vremeNaIstrch + "\n";
    }
}

class Maraton implements IMaraton {

    private String mestoNaOdrz;
    private int godinaNaOdrzNaMaraton;
    private Atleticar[] atleticari;

    public Maraton() {
    }

    public Maraton(String mestoNaOdrz, int godinaNaOdrzNaMaraton, Atleticar[] atleticari) {
        this.mestoNaOdrz = mestoNaOdrz;
        this.godinaNaOdrzNaMaraton = godinaNaOdrzNaMaraton;
        this.atleticari = atleticari;
    }

    @Override
    public String toString() {

        String a = mestoNaOdrz + "\n" + godinaNaOdrzNaMaraton + "\n";
        String b = "";
        for (int i = 0; i < atleticari.length; i++)
            b += atleticari[i].toString();
        return a + b;
    }

    @Override
    public Atleticar najdobroVreme() {
        int k = 0;
        double min = atleticari[0].getVremeNaIstrch();
        for (int i = 1; i <= atleticari.length - 1; i++) {
            if (atleticari[i].getVremeNaIstrch() < min) {
                min = atleticari[i].getVremeNaIstrch();
                k = i;
            }
        }
        return atleticari[k];
    }

    @Override
    public int atleticariOd(String s) {
        int broj = 0;
        for (int i = 0; i <= atleticari.length - 1; i++) {
            if (atleticari[i].getZemjaPoteklo().equals(s)) {
                broj++;
            }
        }
        return broj;
    }
}

public class ZadacaMaraton {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        Atleticar[] atleticari = new Atleticar[n];

        String ime;
        String pol;
        int vozrast;
        double vreme;
        String zemja;

        input.nextLine();

        for (int i = 0; i < n; i++) {
            ime = input.nextLine();
            pol = input.nextLine();
            vozrast = input.nextInt();
            vreme = input.nextDouble();
            input.nextLine();
            zemja = input.nextLine();
            atleticari[i] = new Atleticar(ime, pol, vozrast, vreme, zemja);

        }

        String mesto;
        int godina;
        String zemjaP;
        mesto = input.nextLine();
        godina = input.nextInt();
        input.nextLine();

        Maraton m1 = new Maraton(mesto, godina, atleticari);
        System.out.print(m1.toString());

        zemjaP = input.nextLine();
        System.out.println("Prvo mesto: " + m1.najdobroVreme().toString());
        System.out.println("Ima vkupno " + m1.atleticariOd(zemjaP) + " atleticar/i od " + zemjaP);
    }
}
