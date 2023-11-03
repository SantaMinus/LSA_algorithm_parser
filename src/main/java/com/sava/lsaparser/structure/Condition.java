package com.sava.lsaparser.structure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Condition extends Node {

  private Node falseWay;

  public Condition(String name, int id, int n) {
    super(name, id, n);
  }
}
