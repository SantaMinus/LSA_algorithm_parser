package main.java.com.sava;

public class Condition extends Node {
    private Node falseway;

    public Condition(String name, int id, int n) {
        super(name, id, n);
    }

    public void setFalseWay(Node n) {
        falseway = n;
    }

    public Node getFalseWay() {
        return falseway;
    }
}
