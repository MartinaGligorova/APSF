package niziilistilab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

class SLL<E extends Comparable<E>> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            SLLNode<E> tmp = first;
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
            SLLNode<E> tmp = first;
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
        SLLNode<E> ins = new SLLNode<E>(o, first);

        first = ins;
    }

    public void insertAfter(E o, SLLNode<E> node) {
        if (node != null) {
            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }

    public void insertBefore(E o, SLLNode<E> before) {

        if (first != null) {
            SLLNode<E> tmp = first;
            if (first == before) {
                this.insertFirst(o);
                return;
            }
            //ako first!=before
            while (tmp.succ != before)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                SLLNode<E> ins = new SLLNode<E>(o, before);
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
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode<E> ins = new SLLNode<E>(o, null);
            tmp.succ = ins;
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
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

    public SLLNode<E> getFirst() {
        return first;
    }

    public SLLNode<E> find(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
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

        private SLLNode<E> place, curr;

        private LRIterator() {
            place = (SLLNode<E>) first;
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
            SLLNode<E> tmp = first;
            SLLNode<E> newsucc = null;
            SLLNode<E> next;

            while (tmp != null) {
                next = tmp.succ;
                tmp.succ = newsucc;
                newsucc = tmp;
                tmp = next;

            }
            first = newsucc;
        }
    }


    public void merge(SLL<E> in) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            tmp.succ = in.getFirst();
        } else {
            first = in.getFirst();
        }
    }


    public SLL<E> joinLists(SLL<E> lista2) {
        SLLNode<E> prvJazol = this.getFirst();
        SLLNode<E> vtorJazol = lista2.getFirst();
        SLL<E> rezultat = new SLL<>();

        while (prvJazol != null && vtorJazol != null) {
            if (prvJazol.element.compareTo(vtorJazol.element) < 0) {
                rezultat.insertLast(prvJazol.element);
                prvJazol = prvJazol.succ;
            } else if (prvJazol.element.compareTo(vtorJazol.element) > 0) {
                rezultat.insertLast(vtorJazol.element);
                vtorJazol = vtorJazol.succ;
            } else if (prvJazol.element.compareTo(vtorJazol.element) == 0) {
                rezultat.insertLast(prvJazol.element);
                rezultat.insertLast(vtorJazol.element);
                prvJazol = prvJazol.succ;
                vtorJazol = vtorJazol.succ;
            }
        }

        if (prvJazol != null) {
            while (prvJazol != null) {
                rezultat.insertLast(prvJazol.element);
                prvJazol = prvJazol.succ;
            }
        }

        if (vtorJazol != null) {
            while (vtorJazol != null) {
                rezultat.insertLast(vtorJazol.element);
                vtorJazol = vtorJazol.succ;
            }
        }
        SLLNode<E> jazol = rezultat.getFirst();
        while (jazol.succ != null) {
            if (jazol.element.compareTo(jazol.succ.element) != 0) {
                jazol = jazol.succ;
            } else if (jazol.element.compareTo(jazol.succ.element) == 0 && jazol.succ.succ != null) {
                jazol.succ = jazol.succ.succ;
            } else if (jazol.element.compareTo(jazol.succ.element) == 0 && jazol.succ.succ == null) {
                jazol.succ = null;
            }
        }
        return rezultat;
    }


}


public class SLLJoinLists<E> {


    public static void main(String[] args) throws IOException {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        SLL<Integer> lista1 = new SLL<>();
        String s = stdin.readLine();
        int N = Integer.parseInt(s);
        s = stdin.readLine();
        String[] pomniza = s.split(" ");
        for (int i = 0; i < N; i++) {
            lista1.insertLast(Integer.parseInt(pomniza[i]));
        }

        SLL<Integer> lista2 = new SLL<>();
        s = stdin.readLine();
        N = Integer.parseInt(s);
        s = stdin.readLine();
        pomniza = s.split(" ");
        for (int i = 0; i < N; i++) {
            lista2.insertLast(Integer.parseInt(pomniza[i]));
        }
        SLL<Integer> spoeni = lista1.joinLists(lista2);
        Iterator<Integer> it = spoeni.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");

        }
        System.out.println();
    }
}