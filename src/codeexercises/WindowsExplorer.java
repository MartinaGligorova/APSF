package codeexercises;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WindowsExplorer {

    public static void main(String[] args) throws IOException {
        int i, j, k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // vlezen parametar vo obj. -> String
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        String commands[] = new String[N];
        for (i = 0; i < N; i++)
            commands[i] = br.readLine();

        br.close();
        /**
         * 15
         * CREATE a
         * OPEN a
         * CREATE b
         * CREATE d
         * CREATE c
         * PATH
         * OPEN c
         * PATH
         * CREATE u
         * CREATE g
         * CREATE h
         * PATH
         * BACK
         * PATH
         * PRINT
         */


        SLLTree<String> tree = new SLLTree<>();
        tree.makeRoot("c:");

        // current root
        SLLNode<String> tmp = (SLLNode<String>) tree.root();

        // niza commands[] od tip String, chitaj gi, izgradi drvo
        // vasiot kod stoi ovde
        // try StringTokenizer
        for (j = 0; j < commands.length; j++) {
            String line = commands[j];
            st = new StringTokenizer(line);
            String komanda = st.nextToken();
            String folder = null;
            if (komanda.equals("CREATE") || komanda.equals("OPEN") || komanda.equals("DELETE"))
                folder = st.nextToken();


            switch (komanda) {
                // evaluiraj string

                case "CREATE":
                    // vmetni jazol vo drvo/slozhena lista leksikografski
                    if (tmp.firstChild == null) {
                        tree.addChild(tmp, folder);
                    }
                    // jazli gi vmetnuvash kako deca na tmp jazol - tekoven root
                    else if (tmp.firstChild != null) {
                        SLLNode<String> pokazuvac = tmp.firstChild;
                        SLLNode<String> nov = new SLLNode<>(folder);
                        while (true) {
                            if (pokazuvac.element.compareTo(folder) > 0 && pokazuvac.sibling == null) {
                                // togas noviot folder smesti go prv
                                nov.sibling = tmp.firstChild;
                                tmp.firstChild = nov; // relacija "e roditel na"
                                nov.parent = tmp; // relacija "e dete na"
                                break;
                            } else if (pokazuvac.element.compareTo(folder) < 0 && pokazuvac.sibling == null) {
                                nov.sibling = pokazuvac.sibling;
                                pokazuvac.sibling = nov;
                                nov.parent = tmp;
                                break;
                            } else if (pokazuvac.element.compareTo(folder) < 0 && pokazuvac.sibling != null) {
                                while (pokazuvac.sibling.element.compareTo(nov.element) < 0) {
                                    pokazuvac = pokazuvac.sibling;
                                }
                                nov.sibling = pokazuvac.sibling;
                                pokazuvac.sibling = nov;
                                nov.parent = tmp;
                                break;
                            }
                        }
                    }

                    break;

                case "OPEN":
                    // nov folder stanuva tekoven

                    // pridvizhi go noviot pokazuvac folderot?
                    //SLLNode<String> pok = tmp;
                    SLLNode<String> pom;

                    // folderot sto go otvorash e sekogash vo vnatreshnost na tekovniot

                    while (!tmp.element.equals(folder)) {
                        /**
                         * Pristapi tekoven jazol
                         * Procesiraj - dali ima podlista
                         * Dokolku ima pristapi do podlistata
                         *
                         */
                        pom = tmp.firstChild;
                        if (!pom.element.equals(folder)) {
                            while (pom != null && !pom.element.equals(folder)) {
                                pom = pom.sibling;
                            }
                            if (pom.element.equals(folder)) {
                                tmp = pom;
                                break;
                            }
                        } else
                            tmp = pom;
                    }
                    break;

                case "PATH":
                    // od root do tekoven jazol ispechati pateka vo format
                    List<String> niza = new ArrayList<>();
                    SLLNode<String> pomoshen = tmp;

                    if (pomoshen.element.compareTo(tree.root.element) == 0) {
                        System.out.println(pomoshen.element + "\\");
                    } else {
                        while (pomoshen.element.compareTo(tree.root.element) != 0) {
                            niza.add(pomoshen.element);
                            pomoshen = pomoshen.parent;
                        }
                        if (pomoshen.element.compareTo(tree.root.element) == 0) {
                            niza.add(pomoshen.element);
                        }
                    }

                    for (k = niza.size() - 1; k >= 0; k--) {
                        System.out.print(niza.get(k) + "\\");
                    }
                    System.out.println();


                    break;

                case "BACK":
                    // od tekoven folder vo preth. folder
                    if (tmp.parent != null) {
                        tmp = tmp.parent;
                    }
                    break;

                case "PRINT":

            }
        }
    }
}
