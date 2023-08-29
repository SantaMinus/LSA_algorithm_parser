package com.sava.lsaparser.service;

import com.sava.lsaparser.structure.Node;
import java.util.List;

public interface LsaParserService {

  /**
   * Builds a reachability matrix for a given LSA and creates a structure of {@link Node}s
   * representing the algorithm in a form of a list
   *
   * @param lsa input String containing a logical scheme of an algorithm
   * @return a list of {@link Node}s
   */
  List<Node> calculate(String lsa);
}
