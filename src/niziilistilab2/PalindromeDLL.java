package niziilistilab2;

import java.util.Scanner;

class DLLNode1<E> {
    protected E element;
    protected DLLNode1<E> pred, succ;

    public DLLNode1(E elem, DLLNode1<E> pred, DLLNode1<E> succ) {
        this.element = elem;
        this.pred = pred;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return "<-" + element.toString() + "->";
    }
}

class DLL1<E> {
    private DLLNode1<E> first, last;

    public DLL1() {
        // Construct an empty SLL
        this.first = null;
        this.last = null;
    }

    public void deleteList() {
        first = null;
        last = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            DLLNode1<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    public void insertFirst(E o) {
        DLLNode1<E> ins = new DLLNode1<E>(o, null, first);
        if (first == null)
            last = ins;
        else
            first.pred = ins;
        first = ins;
    }

    public void insertLast(E o) {
        if (first == null)
            insertFirst(o);
        else {
            DLLNode1<E> ins = new DLLNode1<E>(o, last, null);
            last.succ = ins;
            last = ins;
        }
    }

    public void insertAfter(E o, DLLNode1<E> after) {
        if (after == last) {
            insertLast(o);
            return;
        }
        DLLNode1<E> ins = new DLLNode1<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }

    public void insertBefore(E o, DLLNode1<E> before) {
        if (before == first) {
            insertFirst(o);
            return;
        }
        DLLNode1<E> ins = new DLLNode1<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
    }

    public E deleteFirst() {
        if (first != null) {
            DLLNode1<E> tmp = first;
            first = first.succ;
            first.pred = null;
            if (first == null)
                last = null;
            return tmp.element;
        } else
            return null;
    }

    public E deleteLast() {
        if (first != null) {
            if (first.succ == null)
                return deleteFirst();
            else {
                DLLNode1<E> tmp = last;
                last = last.pred;
                last.succ = null;
                return tmp.element;
            }
        }
        // else throw Exception
        return null;
    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            DLLNode1<E> tmp = first;
            ret += tmp + "<->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "<->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public DLLNode1<E> getFirst() {
        return first;
    }

    public DLLNode1<E> getLast() {

        return last;
    }
}


public class PalindromeDLL<E extends Comparable<E>> {

    public static int proveriPalindrom(DLL1<Integer> lista) {
        int result = 1;
        DLLNode1<Integer> tmpPocetok = lista.getFirst();
        DLLNode1<Integer> tmpKraj = lista.getLast();
        if (tmpPocetok != null && tmpKraj != null) {
            while (tmpPocetok.succ != tmpKraj.pred) {
                if (tmpPocetok.element.compareTo(tmpKraj.element) == 0) {
                    tmpPocetok = tmpPocetok.succ;
                    tmpKraj = tmpKraj.pred;
                } else if (tmpPocetok.element.compareTo(tmpKraj.element) != 0) {
                    result = -1;
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner citajOdVlez = new Scanner(System.in);
        int N = citajOdVlez.nextInt();
        DLL1<Integer> palindrom = new DLL1<>();
        for (int i = 0; i < N; i++) {
            palindrom.insertLast(citajOdVlez.nextInt());
        }
        citajOdVlez.close();
        System.out.println(proveriPalindrom(palindrom));
    }
}
