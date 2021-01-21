package codeexercisesopstibinarnidrva;

// Opshto drvo - slozhena lista

/**
 * 2 interfaces
 * koi gi implementirame ponatamu so
 * cel implementiranje na mozhnite operacii
 * koi gi ovozmozhuvaat.
 */


import java.util.Iterator;
import java.util.NoSuchElementException;

interface Tree<E> {
    ////////////Accessors ////////////

    // pristap do koren na drvo
    public Node<E> root();

    // pristap do parent jazel na vlezen jazel
    public Node<E> parent(Node<E> node);

    // broj na deca jazli na vlezen parametar jazel
    public int childCount(Node<E> node);

    ////////////Transformers ////////////

    // element na vlez, nov jazol so taa vrednost, postavi root na tree
    public void makeRoot(E elem);

    // na odreden jazel dodadi mu dete jazol nov so vrednosta od vlez za element i vrati pokazuvac kon istiot
    public Node<E> addChild(Node<E> node, E elem);

    // otstrani jazol
    public void remove(Node<E> node);

    ////////////Iterator ////////////

    // iteriraj niz deca jazli na jazel od vlez t.e podlista na jazel
    public Iterator<E> children(Node<E> node);

}

interface Node<E> {

    /*
    Node interface-ot za operacii vrz obj. od tip Node
    - vrati vrednost na jazel
    - vmetni vrednost vo jazel
     */

    public E getElement();

    public void setElement(E elem);
}

// implementacija na Drvo kako Slozhena/Kompleksna lista

// SLLNode is the implementation of the Node interface
class SLLNode<P> implements Node<P> {

    // kompleksen jazol vo strukt. Drvo - Slozhena lista

    // Holds the links to the needed nodes
    /*
    - parent relacija - "e dete na"
    - sibling relacija - "e sibling na"
    - firstChild relacija - "e roditel na (prvo od decata)"
     */
    SLLNode<P> parent, sibling, firstChild;

    // Hold the data
    P element;

    public SLLNode(P o) {
        // jazol bez links
        element = o;
        parent = sibling = firstChild = null;
    }

    // Node interface methods
    public P getElement() {
        return element;
    }

    public void setElement(P o) {
        element = o;
    }

}

class SLLTree<E> implements Tree<E> {

    // koren - ne sodrzhi parent link
    protected SLLNode<E> root;


    // prazno drvo - constructor
    public SLLTree() {
        root = null;
    }


    @Override
    public Node<E> root() {
        // jazel root
        return root;
    }

    @Override
    // Tree.Node ako Node e vgnezden interface na Tree
    public Node<E> parent(Node<E> node) {
        // roditel jazol na node od vlez
        return ((SLLNode<E>) node).parent;
    }

    @Override
    public int childCount(Node<E> node) {
        // tmp pokazuvac kon prvoto dete na node jazel
        // imame casting na tipot na node - vo slozhen jazol
        SLLNode<E> tmp = ((SLLNode<E>) node).firstChild;
        int num = 0;
        while (tmp != null) {
            // dvizhi se nadesno vo podlistata / izbroj deca
            tmp = tmp.sibling;
            num++;
        }
        return num;
    }

    @Override
    public void makeRoot(E elem) {
        // postavi elem od vlez kako nov jazol root na tree
        root = new SLLNode<E>(elem);
    }

    @Override
    public Node<E> addChild(Node<E> node, E elem) {
        // dodadi nov jazol so vrednost elem kako child node na node od vlez i vrati go na izlez

        // kreiranje na nov jazol so null vredn. na link polinja
        SLLNode<E> tmp = new SLLNode<E>(elem);
        // pok. kon node koj treba da e parent na noviot jazol
        SLLNode<E> curr = (SLLNode<E>) node;
        // insert first na noviot jazol vo podlistata
        tmp.sibling = curr.firstChild;
        curr.firstChild = tmp;
        tmp.parent = curr;
        return tmp;
    }

    @Override
    public void remove(Node<E> node) {
        // otstrani jazol node od vlez
        SLLNode<E> curr = (SLLNode<E>) node;
        // pokazuvac curr kon jazelot
        if (curr.parent != null) {
            // currNode != root node
            if (curr.parent.firstChild == curr) {
                // dokolku currNode naznachen za otstranuvanje od drvoto e prv vo podlista

                // The node is the first child of its parent
                // Reconnect the parent to the next sibling
                curr.parent.firstChild = curr.sibling;
            } else {
                // The node is not the first child of its parent
                // Start from the first and search the node in the sibling list
                // and remove it

                // izmini ja podlistata so sporedbi dodeka ne go pronajdesh curr
                SLLNode<E> tmp = curr.parent.firstChild;
                while (tmp.sibling != curr) {
                    tmp = tmp.sibling;
                }
                // reconnect t.e otstrani curr node
                tmp.sibling = curr.sibling;
            }
        } else {
            // currentRoot za otstranuv. == root
            // izbrishi go celo drvo
            root = null;
        }
    }

    class SLLTreeIterator<T> implements Iterator<T> {

        // iteriranje niz slozhena lista / mozhnost za otstranuv. elementi pri iteration

        SLLNode<T> start, current;

        public SLLTreeIterator(SLLNode<T> node) {
            // konstruktor - zapochni iteracija od node na vlez
            start = node;
            current = node;
        }

        public boolean hasNext() {
            // postoi sledbenik dokolku current!=null t.e ovaa statement e true
            return (current != null);
        }

        public T next() throws NoSuchElementException {
            // metod koj gi vrakja izminatite jazli od listata
            if (current != null) {
                SLLNode<T> tmp = current;
                current = current.sibling;
                return tmp.getElement();
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            // otstrani jazol
            if (current != null) {
                current = current.sibling;
            }
        }
    }


    @Override
    public Iterator<E> children(Node<E> node) {
        // iteriraj niz children nodes na node od vlez
        // vrati obj. od class SLLTreeIterator - vlezen parametar prvoto dete od podlistata od kade ke zapochne izminuv.
        return new SLLTreeIterator<E>(((SLLNode<E>) node).firstChild);
    }

    public void printTree() {
        // metod koj go povikuva metodot za rekurzivno izminuvanje na Drvo/Slozhena lista
        // ispechati hierarhija na drvoto
        printTreeRecursive(root, 0);
    }

    void printTreeRecursive(Node<E> node, int level) {
        // pochetok od koren, nivo 0 (broj na jazli koi treba da se pominat za da se stigne od koren do jazelot)
        if (node == null)
            // ne postoi takov jazol ili sme stignale do krajot na drvoto - exit method
            return;
        int i;
        SLLNode<E> tmp;

        for (i = 0; i < level; i++)
            // za root ne se pechati prazno mesto
            System.out.print("  ");
        System.out.println(node.getElement().toString());
        // procesiraj dali kon jazelot se pridruzhuva podlista
        tmp = ((SLLNode<E>) node).firstChild;
        // zapochni so podlista na root jazel
        while (tmp != null) {
            printTreeRecursive(tmp, level + 1);
            /* rek. povik, za pecatenje na elementi od nivo 1 vo drvoto/slozhena lista
            - pr. ispechati prazno mesto pred elementite od podlista
            Rek. izmin. na slozhena lista:
            Pristapi prv jazel
            Procesiraj go - dali e kompleksen - pok. kon podlista
            Da - izmini ja podlistata na toj jazel
            Pristapi do jazel sledbenik na jazel od koj preminuvame vo podlista
             */

            tmp = tmp.sibling;
        }
    }

    /**
     * Stepen na drvo (ne na jazel) - max stepen na jazel od drvoto
     * - stepen - brojot na poddrva koi gi ima eden jazel - links
     */
    public int countMaxChildren() {
        // metod koj go povik. rek. metod za presmetka na stepen na drvo so pochetok vo root node
        return countMaxChildrenRecursive(root);
    }

    int countMaxChildrenRecursive(SLLNode<E> node) {
        // povik na childCount metod koj ke gi izbroi children nodes - elementi od podlista(dokolku ima) na node jazelot
        int t = childCount(node);
        SLLNode<E> tmp = node.firstChild;
        while (tmp != null) {
            // rek. povik na metodot za broenje na children nodes na ostanatite jazli
            // pritoa se odreduva jazel koj sodrzhi max broj deca i vrednosta se chuva vo t
            t = Math.max(t, countMaxChildrenRecursive(tmp));
            // za site jazli od podlistata povtori proces
            tmp = tmp.sibling;
        }
        // stepen na drvo na izlez
        return t;
    }
}

public class SLLTreeTest {

    public static void main(String[] args) {

        Node<String> a, b, c, d, e;
        // jazli

        SLLTree<String> tree = new SLLTree<>();
        // Drvo implementirano so slozhena lista

        tree.makeRoot("C:");

        // metod addChild vrakja jazol koj e dodaden
        a = tree.addChild(tree.root, "Program files");
        b = tree.addChild(a, "CodeBlocks");
        c = tree.addChild(b, "codeblocks.dll");
        c = tree.addChild(b, "codeblocks.exe");
        b = tree.addChild(a, "Nodepad++");
        c = tree.addChild(b, "langs.xml");
        d = c;
        c = tree.addChild(b, "readme.txt");
        c = tree.addChild(b, "notepad++.exe");
        // Users ke bide sega firstChild na root pred Program files
        a = tree.addChild(tree.root, "Users");
        b = tree.addChild(a, "Darko");
        c = tree.addChild(b, "Desktop");
        c = tree.addChild(b, "Downloads");
        c = tree.addChild(b, "My Documents");
        c = tree.addChild(b, "My Pictures");
        b = tree.addChild(a, "Public");
        // Windoes, Users, Program files - children nodes na root node
        a = tree.addChild(tree.root, "Windows");
        b = tree.addChild(a, "Media");

        System.out.println("---------------INITIAL---------------");
        tree.printTree();

        System.out.println("The maximum number of children is "
                + tree.countMaxChildren());
        // stepen na drvo


        // otstranuvame jazol ili poddrvo cij koren e toj jazol
        tree.remove(d);
        System.out.println("--------------------AFTER REMOVE-------------");
        tree.printTree();


        System.out.println("------------------AFTER ROOT REMOVE-----------------");
        tree.remove(tree.root);
        tree.printTree();
    }
}
