package opshtiibinarnidrvalab7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

interface Tree<E> {
    ////////////Accessors ////////////

    public Node<E> root();

    public Node<E> parent(Node<E> node);

    public int childCount(Node<E> node);

    ////////////Transformers ////////////
    public void makeRoot(E elem);

    public Node<E> addChild(Node<E> node, E elem);

    public void remove(Node<E> node);

    ////////////Iterator ////////////
    public Iterator<E> children(Node<E> node);

}

interface Node<E> {

    public E getElement();

    public void setElement(E elem);
}


class SLLTree<E extends Comparable<E>> implements Tree<E> {

    public SLLNode<E> root;

    public SLLTree() {
        root = null;
    }

    public Node<E> root() {
        return root;
    }

    public Node<E> parent(Node<E> node) {
        return ((SLLNode<E>) node).parent;
    }

    public int childCount(Node<E> node) {
        SLLNode<E> tmp = ((SLLNode<E>) node).firstChild;
        int num = 0;
        while (tmp != null) {
            tmp = tmp.sibling;
            num++;
        }
        return num;
    }

    public void makeRoot(E elem) {
        root = new SLLNode<E>(elem);
    }

    public Node<E> addChild(Node<E> node, E elem) {
        SLLNode<E> tmp = new SLLNode<E>(elem);
        SLLNode<E> curr = (SLLNode<E>) node;
        tmp.sibling = curr.firstChild;
        curr.firstChild = tmp;
        tmp.parent = curr;
        return tmp;
    }

    public Node<E> addSibling(Node<E> sortPok, E elem) {
        // nov jazol so elementot od vlez
        SLLNode<E> tmp = new SLLNode<E>(elem);
        SLLNode<E> curr = (SLLNode<E>) sortPok;
        tmp.sibling = curr.sibling;
        curr.sibling = tmp;
        tmp.parent = curr.parent;
        return tmp;
    }

    public void remove(Node<E> node) {
        SLLNode<E> curr = (SLLNode<E>) node;
        if (curr.parent != null) {
            if (curr.parent.firstChild == curr) {
                // The node is the first child of its parent
                // Reconnect the parent to the next sibling
                curr.parent.firstChild = curr.sibling;
            } else {
                // The node is not the first child of its parent
                // Start from the first and search the node in the sibling list and remove it
                SLLNode<E> tmp = curr.parent.firstChild;
                while (tmp.sibling != curr) {
                    tmp = tmp.sibling;
                }
                tmp.sibling = curr.sibling;
            }
        } else {
            root = null;
        }
    }


    class SLLTreeIterator<T extends Comparable<T>> implements Iterator<T> {

        SLLNode<T> start, current;

        public SLLTreeIterator(SLLNode<T> node) {
            start = node;
            current = node;
        }

        public boolean hasNext() {
            return (current != null);
        }

        public T next() throws NoSuchElementException {
            if (current != null) {
                SLLNode<T> tmp = current;
                current = current.sibling;
                return tmp.getElement();
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (current != null) {
                current = current.sibling;
            }
        }
    }

    public Iterator<E> children(Node<E> node) {
        return new SLLTreeIterator<E>(((SLLNode<E>) node).firstChild);
    }

    void printTreeRecursive(Node<E> node, int level) {
        if (node == null)
            return;
        int i;
        SLLNode<E> tmp;

        for (i = 0; i < level; i++)
            System.out.print(" ");
        System.out.println(node.getElement().toString());
        tmp = ((SLLNode<E>) node).firstChild;
        while (tmp != null) {
            printTreeRecursive(tmp, level + 1);
            tmp = tmp.sibling;
        }
    }

    public void printTree(Node<E> root) {
        //printTreeRecursive(root, 0);
        printTreeRecursive(root, 0);
    }

}

class SLLNode<P extends Comparable<P>> implements Node<P> {

    // Holds the links to the needed nodes
    public SLLNode<P> parent, sibling, firstChild;
    // Hold the data
    public P element;

    public SLLNode(P o) {
        element = o;
        parent = sibling = firstChild = null;
    }

    public P getElement() {
        return element;
    }

    public void setElement(P o) {
        element = o;
    }
}

public class WindowsExplorer {

    public static void main(String[] args) throws Exception {
        int i, j, k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        //String commands[] = new String[N];


        SLLTree<String> tree = new SLLTree<String>();
        tree.makeRoot("c:");

        Node<String> takeRoot = tree.root();
        Node<String> zaPrint = takeRoot;

        // vasiot kod stoi ovde

        for (i = 0; i < N; i++) {
            String line = br.readLine();
            String[] podniza = line.split(" ");


            for (j = 0; j < podniza.length; j++) {

                if (podniza[j].equals("CREATE")) {

                    // pokazuvac kon koren
                    SLLNode<String> pokRoot = (SLLNode<String>) takeRoot;

                    // ako tekovniot koren NEMA deca
                    if (pokRoot.firstChild == null) {
                        //dodadi dete jazol na tekovniot koren
                        tree.addChild(pokRoot, podniza[j + 1]);
                        break;
                    } // -> leksikografski
                    else {
                        // simni se da ja izminesh podlistata i smestuvash vo odnos na prvi bukvi

                        SLLNode<String> sortPok = pokRoot.firstChild;
                        // samo eden element vo podlista so koj treba da izvrshime sporedba
                        if (sortPok.sibling == null) {
                            // sporedi gi elems
                            if (podniza[j + 1].compareTo(sortPok.element) > 0) { // elmOdVlez > elemOdDrvo
                                // dodadi sibling
                                tree.addSibling(sortPok, podniza[j + 1]);
                                break;

                            } else if (podniza[j + 1].compareTo(sortPok.element) < 0) {
                                // staj go pred nego - kako prvo dete na ROOT
                                tree.addChild(pokRoot, podniza[j + 1]);
                                break;
                            }

                        } else if (sortPok.sibling != null) {
                            SLLNode<String> preth = null;
                            // treba negde izmegju da se smesti jazol - leksikografski
                            while (sortPok.sibling != null) {
                                // if - smeneto vo while mozebi
                                if (podniza[j + 1].compareTo(sortPok.element) > 0) {
                                    while (podniza[j + 1].compareTo(sortPok.element) > 0 && sortPok.sibling != null) {
                                        // dvizhi se niz podlista za sporedbi
                                        // zachuvaj go prethodnikot cause SLL
                                        preth = sortPok;
                                        sortPok = sortPok.sibling;
                                    }
                                    // koga ke stigne do sortPok.sibling==null
                                    if (podniza[j + 1].compareTo(sortPok.element) > 0 && sortPok.sibling == null) {
                                        tree.addSibling(sortPok, podniza[j + 1]);
                                        break;
                                    } else if (podniza[j + 1].compareTo(sortPok.element) < 0 && sortPok.sibling == null) {
                                        tree.addSibling(preth, podniza[j + 1]);
                                        break;
                                    }


                                } else if (podniza[j + 1].compareTo(sortPok.element) < 0) {
                                    // dokolku noviot jazol e < od jazol koj NE e prv vo podlistata
                                    if (podniza[j + 1].compareTo(sortPok.element) < 0 && preth != null) {
                                        tree.addSibling(preth, podniza[j + 1]);
                                        break;
                                    } else {
                                        // dokolku noviot jazol e < od prviot jazol od podlistata napraj mu INSERT FIRST
                                        tree.addChild(pokRoot, podniza[j + 1]);
                                        break;
                                    }
                                }
                            }
                            // sme stignale do sortPok.sibling==null

                            break;

                        }
                    }
                } else if (podniza[j].equals("OPEN")) {
                    // vo tekovniot folder, najdi i otvori folder od vlez i napravi go tekoven folder.

                    // pokazuvac kon koren
                    SLLNode<String> pokRoot = (SLLNode<String>) takeRoot;

                    // ako tekovniot koren IMA deca
                    if (pokRoot.firstChild != null) {
                        // simni se edno nivo nadolu
                        pokRoot = pokRoot.firstChild;

                        // dokolku folderot vednash prv vo podlistata e toj sto se bara...napraj go tekoven.
                        if (pokRoot.element.equals(podniza[j + 1])) {
                            // nov koren
                            Node<String> novRoot = pokRoot;
                            takeRoot = novRoot;
                            break;
                        }
                        // sme se simnale vo folderot, no prviot element od podlista != od folder na vlez
                        else if (!(pokRoot.element.equals(podniza[j + 1]))) {
                            // dvizhi se niz siblings
                            while (pokRoot.sibling != null) {

                                pokRoot = pokRoot.sibling;

                                if (pokRoot.element.equals(podniza[j + 1])) {
                                    // nov koren
                                    Node<String> novRoot = pokRoot;
                                    takeRoot = novRoot;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else if (podniza[j].equals("PATH")) {
                    // pateka - izminati jazli - od koren do tekoven folder pechati (vo odreden format)
                    SLLNode<String> tekoven = (SLLNode<String>) takeRoot;

                    // dokolku sme na vrvoto od drvoto i.e se bara se ispecati main root
                    if (tekoven.parent == null) {
                        System.out.println(tekoven.element + "\\");
                        break;
                    }

                    // dokolku imame samo 1 parent node
                    if (tekoven.parent.parent == null) {
                        System.out.println(tekoven.parent.element + "\\" + tekoven.element + "\\");
                        break;
                    }
                    // pateka site jazli sto se izminuvaat od pocetok do tekovniot folder
                    // znaci nekako zachuvaj gi
                    SLLNode<String> prvParent = tekoven.parent;
                    if (prvParent.parent.parent == null) {
                        System.out.println(prvParent.parent.element + "\\" + prvParent.element + "\\" +
                                tekoven.element + "\\");
                        break;
                    } else {
                        // imame >2 parent nodes
                        while (true) {
                            // infinite loop
                            SLLNode<String> novPok = prvParent.parent;
                            if (novPok != null) {
                                if (novPok.sibling == null) {
                                    SLLNode<String> bezSiblings = novPok;
                                    while (bezSiblings.parent != null) {
                                        bezSiblings = bezSiblings.parent;
                                    }
                                    while (bezSiblings != novPok) {
                                        System.out.print(bezSiblings.element + "\\");
                                        bezSiblings = bezSiblings.firstChild;
                                    }
                                    if (bezSiblings == novPok) {
                                        System.out.println(bezSiblings.element + "\\" + prvParent.element + "\\" +
                                                tekoven.element + "\\");
                                        break;
                                    }
                                } else {

                                    SLLNode<String> bezSiblings = novPok;
                                    while (bezSiblings.parent != null) {
                                        bezSiblings = bezSiblings.parent;
                                    }
                                    while (bezSiblings != novPok) {
                                        System.out.print(bezSiblings.element + "\\");
                                        bezSiblings = bezSiblings.firstChild;
                                    }
                                    if (bezSiblings == novPok) {
                                        System.out.println(bezSiblings.element + "\\" + prvParent.element + "\\" +
                                                tekoven.element + "\\");
                                        break;
                                    }
                                }
                            }
                        }
                    }


                } else if (podniza[j].equals("BACK")) {
                    // odi vo prethoden folder

                    // zemi tekoven koren
                    SLLNode<String> tekovenKoren = (SLLNode<String>) takeRoot;

                    // kachi se edno nivo pogore
                    if (tekovenKoren.parent != null) {
                        tekovenKoren = tekovenKoren.parent;
                        // tekovniot koren ja prezema vrednosta na noviot koren
                        takeRoot = tekovenKoren;
                        break;
                    } else
                        break;

                } else if (podniza[j].equals("PRINT")) {

                    // od main root
                    tree.printTree(zaPrint);

                } else if (podniza[j].equals("DELETE")) {
                    // sekogas sme edno nivo nad jazol za brisenje

                    // simni se edno nivo nadolu
                    SLLNode<String> pomoshen = (SLLNode<String>) takeRoot;
                    if (pomoshen.firstChild != null) {
                        pomoshen = pomoshen.firstChild;

                        // prviot od podlista e za brisenje
                        if (pomoshen.element.equals(podniza[j + 1])) {
                            Node<String> deleteIt = pomoshen;
                            tree.remove(deleteIt);
                            break;
                        } else {
                            while (pomoshen.sibling != null) {
                                pomoshen = pomoshen.sibling;
                                if (pomoshen.element.equals(podniza[j + 1])) {
                                    Node<String> deleteIt = pomoshen;
                                    tree.remove(deleteIt);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        br.close();
    }
}
