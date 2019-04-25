package main.java.com.sava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LSAParser {
    static List<String> lsa = new ArrayList<>();
    public static int[][] matrix;
    public static int[] additional;
    static Scanner scanner = new Scanner(System.in);
    static int ni = 0, no = 0, tmp, msize = 0;

    String s;

    boolean lookFor(String s) {
        for (int i = 0; i < lsa.size(); i++) {
            if (lsa.get(i) == s)
                return true;
        }
        return false;
    }

    static void Calculate() {
        //check for BEGIN
        if (lsa.get(0).equalsIgnoreCase("b")) {
            for (int i = 1; i < lsa.size(); i++) {
                if (lsa.get(i).equalsIgnoreCase("b")) {
                    GUI.output.setText(GUI.output.getText() + "\nOnly one BEGIN is allowed");
                    return;
                }
            }
        } else {
            GUI.output.setText(GUI.output.getText() + "\nBEGIN required");
        }

        //check body
        for (int i = 1; i < lsa.size(); i++) {
            if (!lsa.get(i).startsWith("x") && !lsa.get(i).startsWith("y") && !lsa.get(i).startsWith("i") && !lsa.get(i).startsWith("o") && !lsa.get(i).startsWith("e")) {
                GUI.output.setText(GUI.output.getText() + "\nWrong character contained");
            }
            if (lsa.get(i).startsWith("i")) {
                ni++;
            }
            if (lsa.get(i).startsWith("o")) {
                no++;
            }
        }
        if (ni != no) GUI.output.setText(GUI.output.getText() + "\nI/O mismatch");

        buildMatrix();

    }

    protected static void buildMatrix() {
        matrix = new int[lsa.size()][lsa.size()];
        additional = new int[lsa.size()];
        matrix[0][1] = 1;
        additional[0] = 0;

        for (int i = 1; i < lsa.size(); i++) {
            int k = 1;
            if (lsa.get(i).startsWith("y")) {
                additional[i] = lsa.get(i).charAt(1) - 48;
                if (lsa.get(i + 1).startsWith("i")) {
                    k++;
                } else if (lsa.get(i + 1).startsWith("o")) {
                    for (int j = 0; j < lsa.size(); j++) {
                        if (lsa.get(j).equals("i" + lsa.get(i + 1).charAt(1))) {
                            tmp = j;
                            if (lsa.get(j + 1).startsWith("o") || lsa.get(j + 1).startsWith("i"))
                                k++;
                            matrix[i][tmp + k] = 1;
                        }
                    }
                } else {
                    matrix[i][i + k] = 1;
                }
            }
            if (lsa.get(i).startsWith("x")) {
                additional[i] = lsa.get(i).charAt(1) - 48;
                for (int j = 0; j < lsa.size(); j++) {
                    if (lsa.get(j).equals("i" + lsa.get(i + 1).charAt(1))) {
                        tmp = j;
                        if (lsa.get(j + 1).startsWith("o") || lsa.get(j + 1).startsWith("i"))
                            k++;
                        matrix[i][tmp + k] = 1;
                    }
                }
                k = 1;
                if (lsa.get(i + 2).startsWith("o") || lsa.get(i + 2).startsWith("i"))
                    k++;
                matrix[i][i + k + 1] = 2;
            }
            if (lsa.get(i).equals("e")) {
                additional[i] = 9;
            }
        }
    }
}
