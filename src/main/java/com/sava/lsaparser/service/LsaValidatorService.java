package com.sava.lsaparser.service;

import com.sava.lsaparser.exception.LsaValidationException;

public interface LsaValidatorService {

  /**
   * Checks the algorithm representation correctness: the presence of necessary nodes, validates
   * used characters and the links quantity
   *
   * @param lsa input algorithm
   * @throws LsaValidationException in case of the LSA form violation
   */
  void validate(String lsa) throws LsaValidationException;
}
