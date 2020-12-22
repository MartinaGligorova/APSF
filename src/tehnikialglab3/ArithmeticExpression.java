package tehnikialglab3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArithmeticExpression {

    static int operacija(int a, int b, char znak) {
        if (znak == '+') return a + b;
        else return a - b;
    }

    static int presmetaj(char c[], int l, int r) {
        int rezultat = 0;
        int brojac = 0;

        if (l == r) {
            rezultat = (c[l] - '0');
            // return rezultat;
        }
        if (c.length == 5) {
            rezultat = operacija(c[l + 1] - '0', c[l + 3] - '0', c[l + 2]); //'n'-'0'=n
            return rezultat;
        } else {
            for (int i = l + 1; i < r; i++) {
                if (c[i] == '(') brojac++;
                if (c[i] == ')') brojac--;
                if (brojac == 0) {
                    rezultat = operacija(presmetaj(c, l + 1, i), presmetaj(c, i + 2, r - 1), c[i + 1]);
                    return rezultat;
                }
            }
        }
        return rezultat;
    }

    public static void main(String[] args) throws Exception {
        int i, j, k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String expression = br.readLine();
        char exp[] = expression.toCharArray();

        int rez = presmetaj(exp, 0, exp.length - 1);
        System.out.println(rez);

        br.close();
    }
}