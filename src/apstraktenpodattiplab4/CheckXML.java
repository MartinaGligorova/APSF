package apstraktenpodattiplab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;



public class CheckXML {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int n = Integer.parseInt(s);
        String[] redovi = new String[n];

        for (int i = 0; i < n; i++)
            redovi[i] = br.readLine();


        int valid = 1;
        // Vasiot kod tuka
        // Moze da koristite dopolnitelni funkcii ako vi se potrebni

        LinkedStack<String> linkedSt = new LinkedStack<>();
        for (int i = 0; i < redovi.length; i++) {
            String element = redovi[i];
            for (int j = 0; j < element.length(); j++) {
                char c = element.charAt(j);

                if (c != '[') continue;
                if (c == '[' && element.charAt(j + 1) != '/') {
                    linkedSt.push(element);
                } else if (c == '[' && element.charAt(j + 1) == '/') {
                    String dzirniOdStek = linkedSt.peek();

                    if (dzirniOdStek.charAt(j) == c && dzirniOdStek.charAt(j + 1) != element.charAt(j + 1) &&
                            dzirniOdStek.charAt(j + 1) == element.charAt(j + 2)
                            && dzirniOdStek.charAt(dzirniOdStek.length() - 1) == element.charAt(element.length() - 1)
                            && (dzirniOdStek.length() + 1 == element.length())) {
                        linkedSt.pop();
                    } else
                        valid = 0;
                    break;
                }
            }
        }

        int rez;
        switch (valid) {
            case 0:
                rez = valid;
                System.out.println(rez);
                break;
            case 1:
                rez = valid;
                System.out.println(rez);
                break;
        }
        br.close();
    }
}