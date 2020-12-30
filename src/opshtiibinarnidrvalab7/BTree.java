package opshtiibinarnidrvalab7;

class BTree<E extends Comparable<E>> {

    public BNode<E> root;

    public BTree() {
        root = null;
    }

    public BTree(E info) {
        root = new BNode<E>(info);
    }

    public void makeRoot(E elem) {
        root = new BNode(elem);
    }

    public void makeRootNode(BNode<E> node) {
        root = node;
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {

        BNode<E> tmp = new BNode<E>(elem);

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

    public void PreorderNonRecursive() {
        // vasiot kod ovde
        // KOREN - LEFT - RIGHT
        // so implementacija na stek se izvrshuva nerekurzivno izminuv.
        ArrayStack<BNode<E>> s = new ArrayStack<>(100);
        // chuva nodes stekot
        BNode<E> currentRoot = root;
        // metod za PreorderNonRecursive zadachata
        while (true) {


            while (currentRoot != null) {
                System.out.print(currentRoot.info.toString() + " ");
                s.push(currentRoot);
                currentRoot = currentRoot.left;
            }


            if (s.isEmpty()) {
                break;
            }

            currentRoot = s.peek();
            s.pop();
            currentRoot = currentRoot.right;

        }

        System.out.println();
    }

    public void preorder() {
        System.out.print("PREORDER: ");
        preorderR(root);
        System.out.println();
    }

    public void preorderR(BNode<E> n) {
        if (n != null) {
            System.out.print(n.info.toString() + " ");
            preorderR(n.left);
            preorderR(n.right);
        }
    }

    public void postorder() {
        System.out.print("POSTORDER: ");
        postorderR(root);
        System.out.println();
    }

    public void postorderR(BNode<E> n) {
        if (n != null) {
            postorderR(n.left);
            postorderR(n.right);
            System.out.print(n.info.toString() + " ");
        }
    }

    public void inorderNonRecursive(int vrednost) {
        ArrayStack<BNode<Integer>> s = new ArrayStack<>(100);
        BNode<Integer> tmp = (BNode<Integer>) root;

        // metod za BinaryTreeSum zadacha

        int suma = 0;
        // System.out.print("INORDER (nonrecursive): ");

        while (true) {
            // pridvizuvanje do kraj vo leva nasoka pri sto site koreni
            // na potstebla se dodavaat vo magacin za podocnezna obrabotka
            while (tmp != null) {


                s.push(tmp);
                tmp = tmp.left;


            }

            // ako magacinot e prazen znaci deka stebloto e celosno izminato
            if (s.isEmpty())
                break;

            tmp = s.peek();
            // pecatenje (obrabotka) na jazelot na vrvot od magacinot
            // System.out.print(tmp.info.toString()+" ");
            // brisenje na obraboteniot jazel od magacinot
            if (tmp.equals(root)) {
                System.out.print(suma + " ");
                tmp = tmp.right;
                s.pop();
                suma = 0;

                while (true) {

                    while (tmp != null) {


                        s.push(tmp);
                        tmp = tmp.left;


                    }
                    if (s.isEmpty()) {
                        System.out.print(suma);
                        break;
                    }


                    tmp = s.peek();


                    if (tmp.info > vrednost) {
                        suma += tmp.info;
                        s.pop();
                    } else {
                        s.pop();
                    }


                    tmp = tmp.right;

                }
            }

            if (s.isEmpty())
                break;


            if (tmp.info < vrednost) {
                suma += tmp.info;
                s.pop();
            } else {
                s.pop();
            }


            // pridvizuvanje vo desno od obraboteniot jazel i povtoruvanje na
            // postapkata za desnoto potsteblo na jazelot
            tmp = tmp.right;

        }
        System.out.println();

    }

}