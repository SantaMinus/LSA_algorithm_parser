package com.sava.structure;

public class Condition extends Node {
    private Node falseWay;

    public Condition(String name, int id, int n) {
        super(name, id, n);
    }

    public void setFalseWay(Node n) {
        falseWay = n;
    }

    public Node getFalseWay() {
        return falseWay;
    }
}
