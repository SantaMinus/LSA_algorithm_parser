package com.sava.lsaparser.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
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
}
