package main.java.com.sava;

public class Node {
    String name;
    int id;
    int n;
    private Node next;

    public Node(String name, int id, int n) {
        this.id = id;
        this.name = name;
        this.n = n;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }
}