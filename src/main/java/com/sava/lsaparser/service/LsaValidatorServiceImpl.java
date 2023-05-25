package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.exception.LsaValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LsaValidatorServiceImpl implements LsaValidatorService {

  @Override
  public void validate(Lsa lsa) throws LsaValidationException {
    List<String> lsaNodes = new ArrayList<>(
        Arrays.asList(lsa.getAlgorithmInput().split("\\s* \\s*")));

    checkForBegin(lsaNodes);
    checkForEnd(lsaNodes);
    checkInputOutput(lsaNodes);
  }

  private void checkForBegin(List<String> lsaNodes) throws LsaValidationException {
    String firstNode = lsaNodes.get(0);
    if (!firstNode.equalsIgnoreCase("b")) {
      throw new IllegalArgumentException("LSA must begin with 'begin'");
    }
    for (String node : lsaNodes) {
      if (node.equalsIgnoreCase("b")) {
        throw new LsaValidationException("Only one BEGIN is allowed");
      }
    }
  }

  private void checkForEnd(List<String> lsaNodes) {
    String lastNode = lsaNodes.get(lsaNodes.size() - 1);
    if (!lastNode.equalsIgnoreCase("e")) {
      throw new IllegalArgumentException("LSA must end with 'end'");
    }
  }

  private void checkInputOutput(List<String> lsaNodes) {
    int inputNumber = 0;
    int outputNumber = 0;
    for (String node : lsaNodes) {
      // TODO: refactor condition
      if (!node.startsWith("x")
          && !node.startsWith("y")
          && !node.startsWith("i")
          && !node.startsWith("o")
          && !node.startsWith("e")) {
        throw new IllegalArgumentException("Wrong character contained");
      }

      if (node.startsWith("i")) {
        inputNumber++;
      }
      if (node.startsWith("o")) {
        outputNumber++;
      }
    }
    if (inputNumber != outputNumber) {
      throw new IllegalArgumentException("Input/output quantity mismatch");
    }
  }
}
