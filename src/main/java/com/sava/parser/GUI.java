package com.sava.parser;

import com.sava.structure.Condition;
import com.sava.structure.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GUI {
    private static final String FILE_PATH = "/home/katerynasavina/Documents/tpks1.txt";
    private static LSAParser parser;
    private static JButton ok = new JButton("OK");
    private static JMenuBar menu = new JMenuBar();
    private static final JTextArea input = new JTextArea(2, 30);
    static final JTextArea output = new JTextArea();
    private static ArrayList<Node> nodes = new ArrayList<>();
    private static JButton test = new JButton("Test");
    private static int[][] rm;

    public GUI(LSAParser parser) {
        GUI.parser = parser;
    }

    public void paint() {
        //frame
        JFrame frame = new JFrame("tpks1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menu
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(save);
        file.add(load);
        file.add(exit);

        //button
        ok.setSize(100, 50);

        //adding listeners
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                input.requestFocusInWindow();
            }
        });
        ok.addActionListener(arg0 -> ok());
        exit.addActionListener(arg0 -> System.exit(0));
        save.addActionListener(arg0 -> save());
        load.addActionListener(arg0 -> load());
        test.addActionListener(arg0 -> test());

        //adding components
        addComps(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void ok() {
        try {
            parser.s = input.getText();
            LSAParser.lsa = new ArrayList<>(Arrays.asList(parser.s.split("\\s* \\s*")));

            for (int i = 0; i < LSAParser.lsa.size(); i++) {
                output.setText(output.getText() + "  " + LSAParser.lsa.get(i));
            }

            LSAParser.calculate();
        } catch (Exception e) {
            output.setText(input.getText() + "\nEmpty text field");
        }
    }

    private static void test() {
        int tmp = 0;
        reachabilityMatrix(LSAParser.matrix);
        for (int j = 0; j < rm.length; j++) {
            for (int[] ints : rm) {
                tmp += ints[j];
            }
            if (tmp == 0 && j != 0) {
                output.setText(output.getText() + "\nERROR: Pendant vertex");
            }
        }
        output.setText(output.getText() + "\nThe algorithm is correct");
    }

    private static void save() {
        output.setText(output.getText() + "\n" + "Saved");
        java.io.PrintStream ps = null;
        try {
            ps = new java.io.PrintStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < LSAParser.lsa.size(); i++) {
            for (int j = 0; j < LSAParser.lsa.size(); j++) {
                ps.print(LSAParser.matrix[i][j]);
            }
            ps.println("\n");
        }
        ps.println("sep");
        for (int i = 0; i < LSAParser.lsa.size(); i++) {
            ps.print(LSAParser.additional[i]);
        }
        ps.close();
    }

    private static void load() {
        String s;
        int k = 0;
        output.setText(output.getText() + "\n" + "Loaded");
        File file = new File(FILE_PATH);
        if (file.exists()) {
            Scanner inFile = null;
            try {
                inFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            s = inFile.nextLine();
            inFile.close();

            LSAParser.matrix = new int[s.length()][s.length()];
            LSAParser.additional = new int[s.length()];
            try {
                inFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (inFile.hasNext()) {
                s = inFile.nextLine();
                if (s.equals("sep"))
                    break;
                for (int i = 0; i < s.length(); i++) {
                    LSAParser.matrix[k][i] = Character.digit(s.charAt(i), 10);
                }
                if (!s.equals(""))
                    k++;
            }
            s = inFile.nextLine();
            for (int i = 0; i < s.length(); i++) {
                LSAParser.additional[i] = Character.digit(s.charAt(i), 10);
            }

            output.setText(output.getText() + "\n");
            for (int i = 0; i < LSAParser.matrix.length; i++) {
                for (int j = 0; j < LSAParser.matrix.length; j++) {
                    output.setText(output.getText() + LSAParser.matrix[i][j]);
                }
                output.setText(output.getText() + "\n");
            }
            inFile.close();
        }
        generateLSA();
    }

    private static void addComps(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JPanel controls = new JPanel();
        JLabel sign = new JLabel("Greetings. Let us make up a logical scheme :)");
        JLabel separator = new JLabel("Hit the button and see your result below:");
        JScrollPane scrollPane = new JScrollPane(output);
        controls.setLayout(new GridLayout(2, 2));

        panel.add(menu);
        panel.add(sign);
        panel.add(input);
        panel.add(separator);
        panel.add(scrollPane);
        panel.add(ok);
        panel.add(test);

        pane.add(panel);
    }

    //LSA objects generation
    private static void generateLSA() {
        boolean x = false, y = false;
        Node b = new Node("b", 0, 0);
        Node e;
        nodes.add(b);

        for (int i = 1; i < LSAParser.matrix.length; i++) {
            for (int j = 0; j < LSAParser.matrix.length; j++) {
                if (LSAParser.matrix[i][j] == 2) {
                    x = true;
                }
                if (LSAParser.matrix[i][j] == 1) {
                    y = true;
                }
            }
            if (x) {
                nodes.add(new Condition("x", LSAParser.additional[i], i));
            } else if (LSAParser.additional[i] == 9) {
                e = new Node("e", 0, i);
                nodes.add(e);
            } else if (y) {
                nodes.add(new Node("y", LSAParser.additional[i], i));
            }
            x = false;
            y = false;
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals("y") || nodes.get(i).name.equals("b")) {
                for (int j = 0; j < LSAParser.matrix.length; j++) {
                    if (LSAParser.matrix[nodes.get(i).n][j] == 1) {
                        for (Node node : nodes) {
                            if (node.n == j)
                                nodes.get(i).setNext(node);
                        }
                    }
                }
            }
            if (nodes.get(i).name.equals("x")) {
                for (int j = 0; j < LSAParser.matrix.length; j++) {
                    if (LSAParser.matrix[nodes.get(i).n][j] == 1) {
                        for (Node node : nodes) {
                            if (node.n == j)
                                nodes.get(i).setNext(node);
                        }
                    }
                    if (LSAParser.matrix[nodes.get(i).n][j] == 2) {
                        for (Node node : nodes) {
                            if (node.n == j)
                                ((Condition) nodes.get(i)).setFalseWay(node);
                        }
                    }
                }
            }
        }
        for (Node node : nodes) {
            if (node.getNext() != null) {
                output.setText(output.getText() + "\n" + node.name + node.id + " --->  " + node.getNext().name + node.getNext().id);
            }
        }
    }

    private static void reachabilityMatrix(int[][] matrix) {
        int[][] e1 = matrix.clone();
        int[][] tmp = new int[matrix.length][matrix.length];
        rm = new int[LSAParser.matrix.length][LSAParser.matrix.length];
        for (int i = 0; i < nodes.size(); i++) {
            for (int m = 0; m < matrix.length; m++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        tmp[m][j] += e1[m][k] * matrix[k][j];
                    }
                }
            }
            e1 = tmp;
            for (int m = 0; m < matrix.length; m++) {
                for (int j = 0; j < matrix.length; j++) {
                    rm[m][j] = matrix[m][j] ^ e1[m][j];
                    System.out.print(rm[m][j]);
                }
            }
        }
    }
}