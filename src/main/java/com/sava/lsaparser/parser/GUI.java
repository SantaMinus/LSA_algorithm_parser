package com.sava.lsaparser.parser;

import com.sava.lsaparser.structure.Condition;
import com.sava.lsaparser.structure.Node;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// TODO: move into gui package
@Slf4j
public class GUI {
    private final LSAParser parser;
    private final JButton ok = new JButton("OK");
    private final JMenuBar menu = new JMenuBar();
    private static final JTextArea input = new JTextArea(2, 30);
    static final JTextArea output = new JTextArea();
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final JButton test = new JButton("Test");
    private int[][] rm;

    private static final String FILE_PATH = "tpks1.txt";
    private static final String FILE_NOT_FOUND = "File not found";

    public GUI(LSAParser parser) {
        this.parser = parser;
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
            @Override
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

    private void ok() {
        try {
            String inputString = input.getText();
            LSAParser.lsa = new ArrayList<>(Arrays.asList(inputString.split("\\s* \\s*")));

            for (int i = 0; i < LSAParser.lsa.size(); i++) {
                output.setText(output.getText() + "  " + LSAParser.lsa.get(i));
            }

            parser.calculate();
        } catch (Exception e) {
            output.setText(input.getText() + "\nEmpty text field");
        }
    }

    private void test() {
        int tmp = 0;
        reachabilityMatrix(parser.getMatrix());
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

    private void save() {
        output.setText(output.getText() + "\n" + "Saved");
        try (PrintStream ps = new PrintStream(FILE_PATH)) {

            for (int i = 0; i < LSAParser.lsa.size(); i++) {
                for (int j = 0; j < LSAParser.lsa.size(); j++) {
                    ps.print(parser.getMatrix()[i][j]);
                }
                ps.println("\n");
            }
            ps.println("sep");

            for (int i = 0; i < LSAParser.lsa.size(); i++) {
                ps.print(parser.getAdditional()[i]);
            }
        } catch (FileNotFoundException e) {
            log.error(FILE_NOT_FOUND, e);
        }
    }

    private void load() {
        String s;
        int k = 0;
        output.setText(output.getText() + "\nLoaded");
        File file = new File(FILE_PATH);
        if (file.exists()) {
            Scanner inFile = null;
            try {
                inFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.error(FILE_NOT_FOUND, e);
            }
            assert inFile != null;
            s = inFile.nextLine();
            inFile.close();

            parser.setMatrix(new int[s.length()][s.length()]);
            parser.setAdditional(new int[s.length()]);
            try {
                inFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.error(FILE_NOT_FOUND, e);
            }

            while (inFile.hasNext()) {
                s = inFile.nextLine();
                if (s.equals("sep"))
                    break;
                for (int i = 0; i < s.length(); i++) {
                    parser.getMatrix()[k][i] = Character.digit(s.charAt(i), 10);
                }
                if (!s.equals(""))
                    k++;
            }
            s = inFile.nextLine();
            for (int i = 0; i < s.length(); i++) {
                parser.getAdditional()[i] = Character.digit(s.charAt(i), 10);
            }

            output.setText(output.getText() + "\n");
            for (int i = 0; i < parser.getMatrix().length; i++) {
                for (int j = 0; j < parser.getMatrix().length; j++) {
                    output.setText(output.getText() + parser.getMatrix()[i][j]);
                }
                output.setText(output.getText() + "\n");
            }
            inFile.close();
        }
        generateLSA();
    }

    private void addComps(final Container pane) {
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
    private void generateLSA() {
        boolean x = false;
        boolean y = false;
        Node b = new Node("b", 0, 0);
        Node e;
        nodes.add(b);

        for (int i = 1; i < parser.getMatrix().length; i++) {
            for (int j = 0; j < parser.getMatrix().length; j++) {
                if (parser.getMatrix()[i][j] == 2) {
                    x = true;
                }
                if (parser.getMatrix()[i][j] == 1) {
                    y = true;
                }
            }
            if (x) {
                nodes.add(new Condition("x", parser.getAdditional()[i], i));
            } else if (parser.getAdditional()[i] == 9) {
                e = new Node("e", 0, i);
                nodes.add(e);
            } else if (y) {
                nodes.add(new Node("y", parser.getAdditional()[i], i));
            }
            x = false;
            y = false;
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals("y") || nodes.get(i).getName().equals("b")) {
                for (int j = 0; j < parser.getMatrix().length; j++) {
                    if (parser.getMatrix()[nodes.get(i).getN()][j] == 1) {
                        for (Node node : nodes) {
                            if (node.getN() == j)
                                nodes.get(i).setNext(node);
                        }
                    }
                }
            }
            if (nodes.get(i).getName().equals("x")) {
                for (int j = 0; j < parser.getMatrix().length; j++) {
                    if (parser.getMatrix()[nodes.get(i).getN()][j] == 1) {
                        for (Node node : nodes) {
                            if (node.getN() == j)
                                nodes.get(i).setNext(node);
                        }
                    }
                    if (parser.getMatrix()[nodes.get(i).getN()][j] == 2) {
                        for (Node node : nodes) {
                            if (node.getN() == j)
                                ((Condition) nodes.get(i)).setFalseWay(node);
                        }
                    }
                }
            }
        }
        for (Node node : nodes) {
            if (node.getNext() != null) {
                output.setText(output.getText() + "\n" + node.getName() + node.getId() + " --->  " + node.getNext().getName() + node.getNext().getId());
            }
        }
    }

    private void reachabilityMatrix(int[][] matrix) {
        int matrixLength = matrix.length;
        int[][] e1 = matrix.clone();
        int[][] tmp = new int[matrixLength][matrixLength];
        rm = new int[matrixLength][matrixLength];
        for (int i = 0; i < nodes.size(); i++) {
            for (int m = 0; m < matrixLength; m++) {
                for (int j = 0; j < matrixLength; j++) {
                    for (int k = 0; k < matrixLength; k++) {
                        tmp[m][j] += e1[m][k] * matrix[k][j];
                    }
                }
            }
            e1 = tmp;
            for (int m = 0; m < matrixLength; m++) {
                for (int j = 0; j < matrixLength; j++) {
                    rm[m][j] = matrix[m][j] ^ e1[m][j];
                    log.debug("Reachability matrix [{}][{}] = {}", m, j, rm[m][j]);
                }
            }
        }
    }
}