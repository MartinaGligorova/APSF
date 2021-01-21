package hashinglabsandexercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lozinki {

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        CBHT<String, String> tabela = new CBHT<>(27);

        for (int i = 0; i < N; i++) {
            String line = bf.readLine();
            String[] podniza = line.split(" ");
            String username = podniza[0];
            String password = podniza[1];
            tabela.insert1(username, password);
            // ako value im e ista ovewrite inaku ne..
        }

        while (true) {
            String line = bf.readLine();
            while (!line.equals("KRAJ")) {
                String[] podn = line.split(" ");
                String korisnickoime = podn[0];
                String pass = podn[1];
                SLLNode<MapEntry<String, String>> pokazuvac = tabela.search(korisnickoime);

                if (pokazuvac == null){
                    System.out.println("Nenajaven");
                    line = bf.readLine();
                    continue;
                }



                if (pokazuvac.element.value.equals(pass)) {
                    System.out.println("Najaven");
                    //line = bf.readLine();
                    break;

                } else {
                    System.out.println("Nenajaven");
                    line = bf.readLine();

                }
            }
            break;
        }
    }
}
