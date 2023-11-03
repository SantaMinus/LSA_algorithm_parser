package com.sava.lsaparser.service;

import com.sava.lsaparser.exception.LsaValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LsaValidatorServiceImpl implements LsaValidatorService {

  public static final String BEGIN = "b";
  public static final String END = "e";
  public static final String IN = "i";
  public static final String OUT = "o";
  public static final String CONDITION = "x";
  public static final String NODE = "y";

  @Override
  public void validate(String lsa) throws LsaValidationException {
    List<String> lsaNodes = new ArrayList<>(
        Arrays.asList(lsa.split(" ")));

    checkValidCharacters(lsaNodes);
    checkForBegin(lsaNodes);
    checkForEnd(lsaNodes);
    checkInputOutput(lsaNodes);
  }

  private void checkForBegin(List<String> lsaNodes) throws LsaValidationException {
    String firstNode = lsaNodes.get(0);

    if (!firstNode.equalsIgnoreCase(BEGIN)) {
      throw new LsaValidationException("LSA must begin with 'b'/'B'");
    }
    for (int i = 1; i < lsaNodes.size(); i++) {
      if (lsaNodes.get(i).equalsIgnoreCase(BEGIN)) {
        throw new LsaValidationException("Only one BEGIN is allowed");
      }
    }
  }

  private void checkForEnd(List<String> lsaNodes) throws LsaValidationException {
    String lastNode = lsaNodes.get(lsaNodes.size() - 1);

    if (!lastNode.equalsIgnoreCase(END)) {
      throw new LsaValidationException("LSA must end with 'end'");
    }
  }

  private void checkInputOutput(List<String> lsaNodes) throws LsaValidationException {
    int inputNumber = 0;
    int outputNumber = 0;
    for (String node : lsaNodes) {
      if (node.startsWith(IN)) {
        inputNumber++;
      }
      if (node.startsWith(OUT)) {
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
      if (!node.startsWith(BEGIN)
          && !node.startsWith(CONDITION)
          && !node.startsWith(NODE)
          && !node.startsWith(IN)
          && !node.startsWith(OUT)
          && !node.startsWith(END)) {
        throw new LsaValidationException("Wrong character contained: " + node);
      }
    }
  }
}
