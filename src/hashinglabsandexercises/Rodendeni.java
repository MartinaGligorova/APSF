package hashinglabsandexercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// MapEntry, SLLNode, CBHT

public class Rodendeni {


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        CBHT<Integer, String> tabela = new CBHT<>((int) (N / 0.5));


        String line;
        for (int i = 0; i < N; i++) {
            line = bf.readLine();
            String[] podniza = line.split(" ");
            // ime - 0, datum - 1
            String ime = podniza[0];
            String[] podniza2 = podniza[1].split("\\.");
            // den, mesec, godina
            int mesec = Integer.parseInt(podniza2[1]);
            tabela.insert2(mesec, ime);
        }

        int month = Integer.parseInt(bf.readLine());
        SLLNode<MapEntry<Integer, String>> pok = tabela.search(month);

        if(pok == null)
            System.out.println("Nema");

        while (pok!=null){
            System.out.print(pok.element.value + " ");
            pok = pok.succ;
        }

        bf.close();

    }
}
