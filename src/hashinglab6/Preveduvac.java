package hashinglab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static hashinglab6.OBHT.NONE;

public class Preveduvac implements Comparable<Preveduvac> {

    // def. na proizv. tip na kluch
    // redundant part
    String element;

    public Preveduvac(String element) {
        this.element = element;
    }

    public int hashCode() {
        return element.hashCode();
    }

    @Override
    public int compareTo(Preveduvac preveduvac) {
        return this.element.compareTo(preveduvac.element);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader readInp = new BufferedReader(new InputStreamReader(System.in));
        // Load factor = n/m


        // br. na poimi vo rechnik
        int N = Integer.parseInt(readInp.readLine());

        // or choose a prime number for m.

        OBHT<String, String> openBucketHT = new OBHT<>((int) (N / 0.5));

        // polni HT
        for (int i = 0; i < N; i++) {
            String chitajPar = readInp.readLine();
            String[] podniza = chitajPar.split(" ");

            for (int j = 0; j < podniza.length; j += 2) {
                // ang zbor - kluch, mkd zbor - value
                // j+=2 za da izleze od prv par od podniza i da go prochita sledniot
                // -> popolneta tabela so (key, val) t.e pr. (dog, kuche) entry
                openBucketHT.insert(podniza[j + 1], podniza[j]);
            }

        }
        // chitanje od vlez elementi koi ke gi prebaruvame vo rechnik - HT until "KRAJ"

        while (true) {

            String angZborVlez = readInp.readLine();
            String[] pom = angZborVlez.split(" ");
            // infinite loop until->
            if (Objects.equals(pom[0], "KRAJ")) break;

            for (int i = 0; i < pom.length; i++) {
                Object prebarajHT = openBucketHT.search(pom[0]);
                if (!(prebarajHT.equals(NONE))) {
                    // -1 ne e pronajen elementot
                    System.out.println(prebarajHT);
                } else {
                    System.out.println("/");
                }
            }
        }
    }
}
