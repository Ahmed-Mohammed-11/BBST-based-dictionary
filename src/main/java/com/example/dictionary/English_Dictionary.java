package com.example.dictionary;
import java.io.*;
import java.util.ArrayList;

public class English_Dictionary {
    Tree<String> DT;

    public English_Dictionary(String ty) {
        if (ty.equals("1")) {
            DT = new AVL<String>();
        } else {
            DT = new RBTree<String>();
        }
    }

    public void Insert(String w) {
        if (DT.insert(w)) {
            System.out.println("Successful Insertion");
        } else {
            System.out.println("Failed Insertion");
        }
    }

    public void Delete(String w) {
        if (DT.delete(w)) {
            System.out.println("Successful Deletion");
        } else {
            System.out.println("Failed Deletion");
        }
    }

    public void Search(String w) {
        if (DT.search(w)) {
            System.out.println(w + "was found");
        } else {
            System.out.println(w + "wasn't found");
        }
    }

    public void BInsert(String p) throws IOException {
        int fc = 0, sc = 0;
        ArrayList<String> batch = read(p);
        if (batch.get(0).equals("E")) {
            System.out.println("wrong word syntax");
        } else {
            for (int i = 0; i < batch.size(); i++) {
                if (DT.insert(batch.get(i))) {
                    ++sc;
                } else {
                    ++fc;
                }
            }
            System.out.println("Number of new added words = " + sc);
            System.out.println("Number of already existing words = " + fc);
        }
    }

    public void BDelete(String p) throws IOException {
        int fc = 0, sc = 0;
        ArrayList<String> batch = read(p);
        if (batch.get(0).equals("E")) {
            System.out.println("wrong word syntax");
        } else {
            for (int i = 0; i < batch.size(); i++) {
                if (DT.delete(batch.get(i))) {
                    ++sc;
                } else {
                    ++fc;
                }
            }
            System.out.println("Number of Deleted words = " + sc);
            System.out.println("Number of non existing words = " + fc);
        }
    }

    public void Show() {
        System.out.print("Dictionary words are: ");
        DT.printTree();
    }

    public void Size() {
        System.out.println("Number of words in dictionary is: " + DT.size());
    }

    public void Tree_Height() {
        System.out.println("Tree height is: " + DT.height());
    }

    protected ArrayList<String> read(String p) throws IOException {
        File file = new File(p);
        BufferedReader B = new BufferedReader(new FileReader(file));
        String w;
        ArrayList<String> batch = new ArrayList<String>();
        while ((w = B.readLine()) != null) {
            if (w.contains("(") || w.contains(")") || w.contains("[") || w.contains("]") || w.contains("{")
                    || w.contains("}")) {
                System.out.println("please enter a valid word :(\n");
                batch = new ArrayList<>();
                batch.add("E");
                break;
            } else {
                batch.add(w);
            }
        }
        return batch;
    }
}
