package hashinglabsandexercises;
// MapEntry, SLLNode, CBHT...promeni metod za insert

import java.text.DecimalFormat;
import java.util.Scanner;

public class PM10 {


    public static void main(String[] args) {
        //BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        CBHT<String, Double> tabela = new CBHT<>(300);

        for (int i = 0; i < N; i++) {
            String naselba = input.next();
            double chestichki = input.nextDouble();


            tabela.insert1(naselba, chestichki);
            // metodot insert modify
        }

        String naselbaAvg = input.next();

        SLLNode<MapEntry<String, Double>> pokazuvac = tabela.search(naselbaAvg);
        if (pokazuvac == null) {
            System.out.println("Nema");
        }
        int brojac = 0;
        double suma = 0;
        while (pokazuvac != null) {
            suma += pokazuvac.element.value;
            brojac++;
            pokazuvac = pokazuvac.succ;
        }
        double avg = Math.abs(suma / brojac);
        DecimalFormat df = new DecimalFormat("######.##");
        avg = Double.parseDouble(df.format(avg));
        System.out.println(avg);



    }
}
