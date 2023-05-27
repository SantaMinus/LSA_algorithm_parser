package com.sava.lsaparser.service;

import com.sava.lsaparser.exception.LsaValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LsaValidatorServiceImpl implements LsaValidatorService {

  private static final String BEGIN_CHAR = "b";
  private static final String END_CHAR = "e";
  public static final String IN_CHAR = "i";
  public static final String OUT_CHAR = "o";
  public static final String NODE_CHAR = "x";
  public static final String CONDITION_CHAR = "y";

  @Override
  public void validate(String lsa) throws LsaValidationException {
    List<String> lsaNodes = new ArrayList<>(
        Arrays.asList(lsa.split("\\s* \\s*")));

    checkValidCharacters(lsaNodes);
    checkForBegin(lsaNodes);
    checkForEnd(lsaNodes);
    checkInputOutput(lsaNodes);
  }

  private void checkForBegin(List<String> lsaNodes) throws LsaValidationException {
    String firstNode = lsaNodes.get(0);

    if (!firstNode.equalsIgnoreCase(BEGIN_CHAR)) {
      throw new LsaValidationException("LSA must begin with 'begin'");
    }
    for (int i = 1; i < lsaNodes.size(); i++) {
      if (lsaNodes.get(i).equalsIgnoreCase(BEGIN_CHAR)) {
        throw new LsaValidationException("Only one BEGIN is allowed");
      }
    }
  }

  private void checkForEnd(List<String> lsaNodes) throws LsaValidationException {
    String lastNode = lsaNodes.get(lsaNodes.size() - 1);

    if (!lastNode.equalsIgnoreCase(END_CHAR)) {
      throw new LsaValidationException("LSA must end with 'end'");
    }
  }

  private void checkInputOutput(List<String> lsaNodes) throws LsaValidationException {
    int inputNumber = 0;
    int outputNumber = 0;
    for (String node : lsaNodes) {
      if (node.startsWith(IN_CHAR)) {
        inputNumber++;
      }
      if (node.startsWith(OUT_CHAR)) {
        outputNumber++;
      }
    }
    if (inputNumber != outputNumber) {
      throw new LsaValidationException("Input/output quantity mismatch");
    }
  }

  private void checkValidCharacters(List<String> lsaNodes) throws LsaValidationException {
    for (String node : lsaNodes) {
      // TODO: refactor condition
      if (!node.startsWith(BEGIN_CHAR)
          && !node.startsWith(NODE_CHAR)
          && !node.startsWith(CONDITION_CHAR)
          && !node.startsWith(IN_CHAR)
          && !node.startsWith(OUT_CHAR)
          && !node.startsWith(END_CHAR)) {
        throw new LsaValidationException("Wrong character contained: " + node);
      }
    }
  }
}
