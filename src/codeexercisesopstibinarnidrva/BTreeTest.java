package codeexercisesopstibinarnidrva;

import java.util.NoSuchElementException;

class BNode<E> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;
    // jazel od binarno drvo ima max dve link polinja (levo/desno dete)

    static int LEFT = 1;
    static int RIGHT = 2;
    // staticki promenlivi za direkten pristap

    public BNode(E info) {
        // jazol bez deca - inicijaliz. na koren
        this.info = info;
        left = null;
        right = null;
    }

    public BNode(E info, BNode<E> left, BNode<E> right) {
        // jazol so dve deca (levo/desno)
        this.info = info;
        this.left = left;
        this.right = right;
    }

    // default constructor
    public BNode() {}
}

interface Stack<E> {
    /**
     * interface stack za mozhni operacii vrz elementite vo stekot
     * nerekurzivno izminuvanje na binarno drvo
     */

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

class ArrayStack<E> implements Stack<E> {
    // implementacija na stek so pomosh na niza
    private E[] elems;
    private int depth;

    @SuppressWarnings("unchecked")
    public ArrayStack(int maxDepth) {
        // Konstrukcija na nov, prazen stek.
        elems = (E[]) new Object[maxDepth];
        depth = 0;
    }


    public boolean isEmpty() {
        // Vrakja true ako i samo ako stekot e prazen.
        return (depth == 0);
    }


    public E peek() {
        // Go vrakja elementot na vrvot od stekot.
        if (depth == 0)
            throw new NoSuchElementException();
        return elems[depth - 1];
    }


    public void clear() {
        // Go prazni stekot.
        for (int i = 0; i < depth; i++) elems[i] = null;
        depth = 0;
    }


    public void push(E x) {
        // Go dodava x na vrvot na stekot.
        elems[depth++] = x;
    }


    public E pop() {
        // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
        if (depth == 0)
            throw new NoSuchElementException();
        E topmost = elems[--depth];
        elems[depth] = null;
        return topmost;
    }
}

class BTree<E> {

    public BNode<E> root;
    // pok. kon koren na drvoto

    public BTree() {
        // konstrukcija na prazno drvo
        root = null;
    }

    public BTree(E info) {
        // konstrukcija na drvo, koren se inicijaliz. na vrednost od vlez
        root = new BNode<E>(info);
    }

    public void makeRoot(E elem) {
        // kreiraj nov jazol so vredn. elem i postavi go kako root na drvo
        // modifikacija na metod poradi zad.
        root = new BNode<E>(elem);
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {
        /**
         *  metod - dodavanje child node na node(vlezen parametar),
         *  where: 1 - LEFT (levo od node/levo dete), 2 - RIGHT (desno od node/desno dete)
         *  elem - vrednost na noviot jazol koj se dodava
         */

        BNode<E> tmp = new BNode<E>(elem);
        // nov jazol

        // gradenje na drvoto od pocetok

        if (where == BNode.LEFT) {
            // 1 - levo dete na node
            if (node.left != null)  // veke postoi element
                return null;
            // ako ne postoi levo dete na node
            node.left = tmp;
        } else {
            // where == BNode.RIGHT; 2 - desno dete na node
            if (node.right != null) // veke postoi element
                return null;
            // ne postoi desno dete - dodadi nov jazol
            node.right = tmp;
        }
        // jazelot koj e dodaden
        return tmp;
    }

    public void inorder() {
        // metod - povik na rekurziven inorder na celo drvo - root - starting point
        System.out.print("INORDER: ");
        inorderR(root);
        System.out.println();
    }

    public void inorderR(BNode<E> currRoot) {
        // levo - koren - desno
        if (currRoot != null) {

            inorderR(currRoot.left);
            System.out.print(currRoot.info.toString() + " ");
            inorderR(currRoot.right);
        }
    }

    public void preorder() {
        System.out.print("PREORDER: ");
        preorderR(root);
        System.out.println();
    }

    public void preorderR(BNode<E> currRoot) {
        // koren - levo - desno
        if (currRoot != null) {
            System.out.print(currRoot.info.toString() + " ");
            preorderR(currRoot.left);
            preorderR(currRoot.right);
        }
    }

    public void postorder() {
        System.out.print("POSTORDER: ");
        postorderR(root);
        System.out.println();
    }

    public void postorderR(BNode<E> currRoot) {
        if (currRoot != null) {
            postorderR(currRoot.left);
            postorderR(currRoot.right);
            System.out.print(currRoot.info.toString() + " ");
        }
    }

    public void inorderNonRecursive() {
        ArrayStack<BNode<E>> s = new ArrayStack<BNode<E>>(100);
        // stek koj chuva nodes koi vo sebe chuvaat elementi od tip E
        BNode<E> p = root;
        System.out.print("INORDER (nonrecursive): ");

        while (true) {
            // pridvizuvanje do kraj vo leva nasoka pri sto site koreni
            // na potstebla se dodavaat vo magacin za podocnezna obrabotka
            while (p != null) {
                s.push(p);
                p = p.left;
            }

            // ako magacinot e prazen znaci deka stebloto e celosno izminato
            if (s.isEmpty())
                break;

            p = s.peek();
            // pecatenje (obrabotka) na jazelot na vrvot od magacinot
            System.out.print(p.info.toString() + " ");
            // brisenje na obraboteniot jazel od magacinot
            s.pop();
            // pridvizuvanje vo desno od obraboteniot jazel i povtoruvanje na
            // postapkata za desnoto potsteblo na jazelot
            p = p.right;

        }
        System.out.println();

    }


    public void preorderNonRecursive() {
        ArrayStack<BNode> s = new ArrayStack<>(100);
        BNode<E> p = root;
        // pokazuvac kon root

        System.out.print("PREORDER (nonrecursive): ");
        // koren - levo - desno
        while (true) {

            while (p != null) {
                System.out.print(p.info.toString() + " ");
                s.push(p);
                p = p.left;
            }

            if (s.isEmpty())
                break;


            p = s.peek();
            s.pop();
            p = p.right;


        }

        System.out.println();

    }

    public void postorderNonRecursive() {
        ArrayStack<BNode<E>> s1 = new ArrayStack<>(100);
        ArrayStack<BNode<E>> s2 = new ArrayStack<>(100);
        BNode<E> p = root;

        System.out.print("POSTORDER (nonrecursive): ");
        // levo - desno - koren

        s1.push(p);

        while (!s1.isEmpty()){

            p = s1.pop();
            s2.push(p);

            if(p.left!=null){
                s1.push(p.left);
            }
            if(p.right!=null){
                s1.push(p.right);
            }
        }

        while (!s2.isEmpty()){
            System.out.print(s2.pop().info.toString() + " ");
        }

        System.out.println();

    }
    // metod - vnatreshni jazli vo drvo
    public int insideNodes() {
        
        return insideNodesR(root);
    }

    public int insideNodesR(BNode<E> currRoot) {
        if (currRoot == null)
            return 0;

        if ((currRoot.left == null)&&(currRoot.right == null))
            return 0;

        return insideNodesR(currRoot.left) + insideNodesR(currRoot.right) + 1;
    }

    public int leaves() {

        return leavesR(root);
    }

    int leavesR(BNode<E> currRoot) {
        if (currRoot != null) {
            if ((currRoot.left == null)&&(currRoot.right == null))
                // nema potomci dodadi 1
                return 1;
            else
                return (leavesR(currRoot.left) + leavesR(currRoot.right));
            // za sekoj jazol ispituvaj levo i desno
        } else {
            return 0;
        }
    }


    public int depth() {

        return depthR(root);
    }

    int depthR(BNode<E> currRoot) {
        // jazolot so max dlabochina e dlabochina na drvo
        // dlabochina na jazel - dolzhina na edinstvena pateka od koren do toj jazel - br. na links
        if (currRoot == null)
            return 0;
        if ((currRoot.left == null)&&(currRoot.right == null))
            return 0;
        return (1 + Math.max(depthR(currRoot.left), depthR(currRoot.right)));
    }


    public void mirror() {

        mirrorR(root);
    }

    void mirrorR(BNode<E> currRoot) {
        BNode<E> tmp;

        if (currRoot == null)
            return;

        // simetricno preslikuvanje na levoto i desnoto potsteblo
        mirrorR(currRoot.left);
        mirrorR(currRoot.right);

        // smena na ulogite na pokazuvacite na momentalniot jazel
        tmp = currRoot.left;
        // left ja prezema vrednosta na right node
        currRoot.left = currRoot.right;
        // right node ja prezema tmp (left node)
        currRoot.right = tmp;
    }

}

public class BTreeTest {
    public static void main(String[] args) {

        BNode<Character> a, b, c;
        // jazli
        BTree<Character> tree = new BTree<Character>('F');
        // btree so root F
        a = tree.addChild(tree.root, BNode.LEFT, 'D');
        b = tree.addChild(a, BNode.LEFT, 'B');
        c = tree.addChild(b, BNode.LEFT, 'A');
        c = tree.addChild(b, BNode.RIGHT, 'C');
        c = tree.addChild(a, BNode.RIGHT, 'E');
        a = tree.addChild(tree.root, BNode.RIGHT, 'G');
        // (node-na koj jazol mu dodavame dete, where - 1 levo, 2 desno, info na jazol koj go dodavame)
        b = tree.addChild(a, BNode.RIGHT, 'I');
        c = tree.addChild(b, BNode.LEFT, 'H');
        c = tree.addChild(b, BNode.RIGHT, 'J');

        tree.inorder();
        tree.preorder();
        tree.postorder();

        // non-rec
        tree.inorderNonRecursive();
        tree.preorderNonRecursive();
        tree.postorderNonRecursive();

        System.out.println("Broj na vnatreshni jazli: " + tree.insideNodes());
        System.out.println("Broj na listovi: " + tree.leaves());
        System.out.println("Dlabochina na drvo: " + tree.depth());

        tree.mirror();

    }


}
