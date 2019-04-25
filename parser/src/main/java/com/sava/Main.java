package main.java.com.sava;

public class Main {

    public static void main(String[] args) {
        LSAParser parser = new LSAParser();
        GUI gui = new GUI(parser);
        gui.paint();
    }
}