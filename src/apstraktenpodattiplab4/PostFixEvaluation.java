package apstraktenpodattiplab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

interface Stack<E> {

    // Elementi na stekot se objekti od proizvolen tip.

    // Metodi za pristap:

    public boolean isEmpty();
    // Vrakja true ako i samo ako stekot e prazen.

    public E peek();
    // Go vrakja elementot na vrvot od stekot.

    // Metodi za transformacija:

    public void clear();
    // Go prazni stekot.

    public void push(E x);
    // Go dodava x na vrvot na stekot.

    public E pop();
    // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
}

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class LinkedStack<E> implements Stack<E> {


    private SLLNode<E> top;
    private int size;

    public LinkedStack() {
        // Konstrukcija na nov, prazen stek.
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        // Vrakja true ako i samo ako stekot e prazen.
        return (top == null);
    }

    public E peek() {
        // Go vrakja elementot na vrvot od stekot.
        if (top == null)
            throw new NoSuchElementException();
        return top.element;
    }

    public void clear() {
        // Go prazni stekot.
        top = null;
        size = 0;
    }

    public void push(E x) {
        // Go dodava x na vrvot na stekot.
        top = new SLLNode<E>(x, top);
        size++;
    }

    public E pop() {

        if (top == null)
            throw new NoSuchElementException();
        E topElem = top.element;
        size--;
        top = top.succ;
        return topElem;
    }
}


public class PostFixEvaluation {

    public static int evaluirajIzraz(String izraz) {
        LinkedStack<Integer> ls = new LinkedStack<>();
        for (int i = 0; i < izraz.length(); i++) {
            char c = izraz.charAt(i);
            if (Character.isWhitespace(c)) continue;
            if (Character.isDigit(c)) {
                int num = 0;

                while (Character.isDigit(c)) {
                    num = num * 10 + (c - '0');
                    i++;
                    c = izraz.charAt(i);
                }
                i--;
                ls.push(num);
            } else {
                int broj2 = ls.pop();
                int broj1 = ls.pop();
                int rezultat;

                switch (c) {


                    case '+':
                        rezultat = broj1 + broj2;
                        ls.push(rezultat);
                        break;

                    case '-':
                        rezultat = Math.abs(broj1 - broj2);
                        ls.push(rezultat);
                        break;

                    case '/':
                        try {
                            if (broj1 == 0 || broj2 == 0)
                                throw new ArithmeticException();
                            rezultat = broj1 / broj2;
                        } catch (ArithmeticException a) {
                            rezultat = 0;
                        }
                        ls.push(rezultat);
                        break;

                    case '*':
                        rezultat = broj1 * broj2;
                        ls.push(rezultat);
                        break;

                }
            }
        }
        return ls.pop();
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expression = br.readLine();

        System.out.println(evaluirajIzraz(expression));


        br.close();
    }
}
