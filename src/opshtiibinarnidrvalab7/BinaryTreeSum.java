package opshtiibinarnidrvalab7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class BinaryTreeSum {


    public static void main(String[] args) throws Exception {
        int i, j, k;
        int index;
        String action;

        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // citame string od vlez
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        // vkupen br na jazli vo drvoto

        BNode<Integer> nodes[] = new BNode[N];
        // gi chuvame vo niza od jazli - max length N
        BTree<Integer> tree = new BTree<Integer>();
        // kreirame binarno drvo koe rab. so Integers

        for (i = 0; i < N; i++)
            nodes[i] = new BNode<Integer>();
        // na i-ta pozicija vo niza smesti nov obj. od tip jazol(zasega empty nodes)

        for (i = 0; i < N; i++) {
            line = br.readLine();
            st = new StringTokenizer(line);
            index = Integer.parseInt(st.nextToken());
            // prviot element od 0 10 ROOT e indeksot
            nodes[index].info = Integer.parseInt(st.nextToken());
            // jazolot na index pozicija vo nizata jazli vo info poleto go smestuva elem. 10 od vlez t.e vrednosta na jazelot
            action = st.nextToken();
            // vo action promenlivata od tip String se smestuva posledniot token - ROOT

            // go polnime drvoto spored actions od sekoj red
            if (action.equals("LEFT")) {
                //tree.addChildNode(na koj jazol mu dodavash dete, (kade) levo, jazolot koj mu go dodavash
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.LEFT, nodes[index]);
            } else if (action.equals("RIGHT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.RIGHT, nodes[index]);
            } else {
                // this node is the root
                // action = ROOT
                tree.makeRootNode(nodes[index]);
            }
        }

        int baranaVrednost = Integer.parseInt(br.readLine());

        br.close();

        // vasiot kod ovde
        BNode<Integer> tmp = tree.root;
        tree.inorderNonRecursive(baranaVrednost);

    }

}