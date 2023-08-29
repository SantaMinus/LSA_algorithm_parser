package com.sava.lsaparser.structure;

import lombok.ToString;

@ToString
public class Node {

  private String name;
  private int id;
  private int n;
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

  public int getN() {
    return n;
  }

  public void setN(int n) {
    this.n = n;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}