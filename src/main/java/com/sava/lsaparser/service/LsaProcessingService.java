package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;

public interface LsaProcessingService {

  /**
   * Validates the input LSA string and calculates the result
   *
   * @param lsa containing the LSA string
   */
  // TODO: return reachability matrix
  void processLsa(Lsa lsa);
}
