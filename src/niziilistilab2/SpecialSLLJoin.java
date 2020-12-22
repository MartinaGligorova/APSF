package niziilistilab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

class SLLNode1<E> {
    protected E element;
    protected SLLNode1<E> succ;

    public SLLNode1(E elem, SLLNode1<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class SLL1<E extends Comparable<E>> {
    private SLLNode1<E> first;

    public SLL1() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            SLLNode1<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode1<E> tmp = first;
            ret += tmp + "->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode1<E> ins = new SLLNode1<E>(o, first);

        first = ins;
    }

    public void insertAfter(E o, SLLNode1<E> node) {
        if (node != null) {
            SLLNode1<E> ins = new SLLNode1<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }

    public void insertBefore(E o, SLLNode1<E> before) {

        if (first != null) {
            SLLNode1<E> tmp = first;
            if (first == before) {
                this.insertFirst(o);
                return;
            }
            //ako first!=before
            while (tmp.succ != before)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                SLLNode1<E> ins = new SLLNode1<E>(o, before);
                tmp.succ = ins;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
    }

    public void insertLast(E o) {
        if (first != null) {
            SLLNode1<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode1<E> ins = new SLLNode1<E>(o, null);
            tmp.succ = ins;
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode1<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode1<E> node) {
        if (first != null) {
            SLLNode1<E> tmp = first;
            if (first == node) {
                return this.deleteFirst();
            }
            while (tmp.succ != node && tmp.succ.succ != null)
                tmp = tmp.succ;
            if (tmp.succ == node) {
                tmp.succ = tmp.succ.succ;
                return node.element;
            } else {
                System.out.println("Elementot ne postoi vo listata");
                return null;
            }
        } else {
            System.out.println("Listata e prazna");
            return null;
        }

    }

    public SLLNode1<E> getFirst() {
        return first;
    }

    public SLLNode1<E> find(E o) {
        if (first != null) {
            SLLNode1<E> tmp = first;
            while (tmp.element != o && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element == o) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return first;
    }

    public Iterator<E> iterator() {
        // Return an iterator that visits all elements of this list, in left-to-right order.
        return new LRIterator<E>();
    }

    // //////////Inner class ////////////

    private class LRIterator<E> implements Iterator<E> {

        private SLLNode1<E> place, curr;

        private LRIterator() {
            place = (SLLNode1<E>) first;
            curr = null;
        }

        public boolean hasNext() {

            return (place != null);
        }

        public E next() {
            if (place == null)
                throw new NoSuchElementException();
            E nextElem = place.element;
            curr = place;
            place = place.succ;
            return nextElem;
        }

        public void remove() {
            //Not implemented
        }
    }

    public void mirror() {
        //procedure
        if (first != null) {
            SLLNode1<E> tmp = first;
            SLLNode1<E> newsucc = null;
            SLLNode1<E> next;

            while (tmp != null) {
                next = tmp.succ;
                tmp.succ = newsucc;
                newsucc = tmp;
                tmp = next;

            }
            first = newsucc;
        }
    }


    public void merge(SLL1<E> in) {
        if (first != null) {
            SLLNode1<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            tmp.succ = in.getFirst();
        } else {
            first = in.getFirst();
        }
    }

}

public class SpecialSLLJoin<E extends Comparable<E>> {

    private static SLL1<Integer> specialJoin(SLL1<Integer> lista1, SLL1<Integer> lista2) {
        SLL1<Integer> spoeni = new SLL1<>();
        SLLNode1<Integer> tmp1 = lista1.getFirst();
        SLLNode1<Integer> tmp2 = lista2.getFirst();
        while (tmp1 != null && tmp1.succ != null && tmp2 != null && tmp2.succ != null) {
            spoeni.insertLast(tmp1.element);
            tmp1 = tmp1.succ;
            spoeni.insertLast(tmp1.element);
            tmp1 = tmp1.succ;
            spoeni.insertLast(tmp2.element);
            tmp2 = tmp2.succ;
            spoeni.insertLast(tmp2.element);
            tmp2 = tmp2.succ;
        }

        if (tmp1 != null) {
            while (tmp1 != null) {
                spoeni.insertLast(tmp1.element);
                tmp1 = tmp1.succ;
            }
        }

        if (tmp2 != null) {
            while (tmp2 != null) {
                spoeni.insertLast(tmp2.element);
                tmp2 = tmp2.succ;
            }
        }
        return spoeni;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        int N = Integer.parseInt(s);
        SLL1<Integer> lista1 = new SLL1<>();
        s = bf.readLine();
        String[] podniza = s.split(" ");
        for (int i = 0; i < N; i++) {

            lista1.insertLast(Integer.parseInt(podniza[i]));
        }

        s = bf.readLine();
        N = Integer.parseInt(s);
        SLL1<Integer> lista2 = new SLL1<>();
        s = bf.readLine();
        podniza = s.split(" ");
        for (int i = 0; i < N; i++) {
            lista2.insertLast(Integer.parseInt(podniza[i]));
        }


        SLL1<Integer> rez = specialJoin(lista1, lista2);
        Iterator<Integer> izmini = rez.iterator();
        while (izmini.hasNext()) {
            System.out.print(izmini.next());
            if (izmini.hasNext()) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}