package com.sava.main;

import com.sava.parser.GUI;
import com.sava.parser.LSAParser;

public class Main {

    public static void main(String[] args) {
        LSAParser parser = new LSAParser();
        GUI gui = new GUI(parser);
        gui.paint();
    }
}
