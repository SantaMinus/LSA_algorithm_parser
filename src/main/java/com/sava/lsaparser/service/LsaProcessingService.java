package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.structure.Node;
import java.util.List;

public interface LsaProcessingService {

  /**
   * Validates the input LSA string and calculates the result
   *
   * @param lsa containing the LSA string
   * @return a list of Nodes connected to each other
   */
  // TODO: return reachability matrix
  List<Node> processLsa(Lsa lsa);
}
