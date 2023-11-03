package com.sava.lsaparser.service;

import static com.sava.lsaparser.service.LsaValidatorServiceImpl.BEGIN;
import static com.sava.lsaparser.service.LsaValidatorServiceImpl.NODE;
import static com.sava.lsaparser.service.LsaValidatorServiceImpl.END;
import static com.sava.lsaparser.service.LsaValidatorServiceImpl.IN;
import static com.sava.lsaparser.service.LsaValidatorServiceImpl.CONDITION;
import static com.sava.lsaparser.service.LsaValidatorServiceImpl.OUT;

import com.sava.lsaparser.structure.Condition;
import com.sava.lsaparser.structure.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LsaParserServiceImpl implements LsaParserService {

  @Override
  public List<Node> calculate(String lsa) {
    MatrixDetails matrixDetails = buildMatrix(lsa);
    List<Node> lsaNodes = generateLSA(matrixDetails);
    log.debug("LSA generated: {}", lsaNodes);
    return lsaNodes;
  }

  // Valid LSAs:
  // b y1 e
  // b y1 x1 o1 y2 i1 y3 e
  // b y1 x1 o1 y3 o2 i1 y4 i2 e
  private MatrixDetails buildMatrix(String inputLsaString) {
    List<String> lsa = new ArrayList<>(
        Arrays.asList(inputLsaString.split("\\s* ")));
    int[][] matrix = new int[lsa.size()][lsa.size()];
    int[] additional = new int[lsa.size()];

    // begin always reaches the next vertex
    matrix[0][1] = 1;

    for (int i = 1; i < lsa.size(); i++) {
      int offset = 1;
      int tmp;
      if (lsa.get(i).startsWith(NODE)) {
        additional[i] = getNodeNumber(lsa, i);
        if (lsa.get(i + 1).startsWith(IN)) {
          offset++;
        } else if (lsa.get(i + 1).startsWith(OUT)) {
          for (int j = 0; j < lsa.size(); j++) {
            if (lsa.get(j).equals(IN + lsa.get(i + 1).charAt(1))) {
              tmp = j;
              if (lsa.get(j + 1).startsWith(OUT) || lsa.get(j + 1).startsWith(IN)) {
                offset++;
              }
              matrix[i][tmp + offset] = 1;
            }
          }
        } else {
          matrix[i][i + offset] = 1;
        }
      }
      // there should be an O vertex after the X
      if (lsa.get(i).startsWith(CONDITION)) {
        additional[i] = getNodeNumber(lsa, i);
        for (int j = 0; j < lsa.size(); j++) {
          if (lsa.get(i + 1).length() < 2) {
            break;
          }
          if (lsa.get(j).equals(IN + lsa.get(i + 1).charAt(1))) {
            tmp = j;
            if (lsa.get(j + 1).startsWith(OUT) || lsa.get(j + 1).startsWith(IN)) {
              offset++;
            }
            matrix[i][tmp + offset] = 1;
          }
        }
        offset = 1;
        if (lsa.size() < 4) {
          break;
        }
        if (lsa.get(i + 2).startsWith(OUT) || lsa.get(i + 2).startsWith(IN)) {
          offset++;
        }
        matrix[i][i + offset + 1] = 2;
      }
      if (lsa.get(i).equals(END)) {
        // TODO: change to another symbol
        additional[i] = 9;
      }
    }
    return new MatrixDetails.MatrixDetailsBuilder()
        .matrix(matrix)
        .additional(additional)
        .build();
  }

  private int getNodeNumber(List<String> lsa, int i) {
    return lsa.get(i).charAt(1) - 48;
  }

  private List<Node> generateLSA(MatrixDetails matrixDetails) {
    int[][] matrix = matrixDetails.getMatrix();
    int[] additional = matrixDetails.getAdditional();

    List<Node> nodes = createNodes(matrix, additional);
    linkNodes(matrix, nodes);
    return nodes;
  }

  private static void linkNodes(int[][] matrix, List<Node> nodes) {
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getName().equals(NODE) || nodes.get(i).getName().equals(BEGIN)) {
        for (int j = 0; j < matrix.length; j++) {
          if (matrix[nodes.get(i).getN()][j] == 1) {
            for (Node node : nodes) {
              if (node.getN() == j) {
                nodes.get(i).setNext(node);
              }
            }
          }
        }
      }
      if (nodes.get(i).getName().equals(CONDITION)) {
        for (int j = 0; j < matrix.length; j++) {
          if (matrix[nodes.get(i).getN()][j] == 1) {
            for (Node node : nodes) {
              if (node.getN() == j) {
                nodes.get(i).setNext(node);
              }
            }
          }
          if (matrix[nodes.get(i).getN()][j] == 2) {
            for (Node node : nodes) {
              if (node.getN() == j) {
                ((Condition) nodes.get(i)).setFalseWay(node);
              }
            }
          }
        }
      }
    }
  }

  private static List<Node> createNodes(int[][] matrix, int[] additional) {
    List<Node> nodes = new ArrayList<>();
    boolean x = false;
    boolean y = false;
    // there is always a beginning
    Node b = new Node(BEGIN, 0, 0);
    nodes.add(b);

    for (int i = 1; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        // while there can be 1 for both Y & X, the 2 is a conditional false way code present for X only
        if (matrix[i][j] == 2) {
          x = true;
        }
        if (matrix[i][j] == 1) {
          y = true;
        }
      }
      if (x) {
        nodes.add(new Condition(CONDITION, additional[i], i));
      } else if (additional[i] == 9) {
        Node e = new Node(END, 0, i);
        nodes.add(e);
      } else if (y) {
        nodes.add(new Node(NODE, additional[i], i));
      }
      x = false;
      y = false;
    }
    return nodes;
  }

  private void buildReachabilityMatrix(int[][] matrix, List<Node> nodes) {
    int matrixLength = matrix.length;
    int[][] e1 = matrix.clone();
    int[][] tmp = new int[matrixLength][matrixLength];
    int[][] reachMatrix = new int[matrixLength][matrixLength];

    for (int i = 0; i < nodes.size(); i++) {
      for (int m = 0; m < matrixLength; m++) {
        for (int j = 0; j < matrixLength; j++) {
          for (int k = 0; k < matrixLength; k++) {
            tmp[m][j] += e1[m][k] * matrix[k][j];
          }
        }
      }
      e1 = tmp;

      for (int m = 0; m < matrixLength; m++) {
        for (int j = 0; j < matrixLength; j++) {
          reachMatrix[m][j] = matrix[m][j] ^ e1[m][j];
          log.debug("Reachability matrix [{}][{}] = {}", m, j, reachMatrix[m][j]);
        }
      }
    }
  }
}
