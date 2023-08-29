package com.sava.lsaparser.service;

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

  private MatrixDetails buildMatrix(String inputLsaString) {
    List<String> lsa = new ArrayList<>(Arrays.asList(inputLsaString.split("\\s* \\s*")));
    int[][] matrix = new int[lsa.size()][lsa.size()];
    int[] additional = new int[lsa.size()];

    // begin always reaches the next vertex
    matrix[0][1] = 1;

    for (int i = 1; i < lsa.size(); i++) {
      int k = 1;
      int tmp;
      if (lsa.get(i).startsWith("y")) {
        additional[i] = getIntFromSecondChar(lsa, i);
        if (lsa.get(i + 1).startsWith("i")) {
          k++;
        } else if (lsa.get(i + 1).startsWith("o")) {
          for (int j = 0; j < lsa.size(); j++) {
            if (lsa.get(j).equals("i" + lsa.get(i + 1).charAt(1))) {
              tmp = j;
              if (lsa.get(j + 1).startsWith("o") || lsa.get(j + 1).startsWith("i")) {
                k++;
              }
              matrix[i][tmp + k] = 1;
            }
          }
        } else {
          matrix[i][i + k] = 1;
        }
      }
      if (lsa.get(i).startsWith("x")) {
        additional[i] = getIntFromSecondChar(lsa, i);
        for (int j = 0; j < lsa.size(); j++) {
          if (lsa.get(i + 1).length() < 2) {
            break;
          }
          // TODO: ArrayIndexOutOfBoundsException: 2
          if (lsa.get(j).equals("i" + lsa.get(i + 1).charAt(1))) {
            tmp = j;
            if (lsa.get(j + 1).startsWith("o") || lsa.get(j + 1).startsWith("i")) {
              k++;
            }
            matrix[i][tmp + k] = 1;
          }
        }
        k = 1;
        if (lsa.size() < 4) {
          break;
        }
        // TODO: ArrayIndexOutOfBoundsException: 4
        if (lsa.get(i + 2).startsWith("o") || lsa.get(i + 2).startsWith("i")) {
          k++;
        }
        matrix[i][i + k + 1] = 2;
      }
      if (lsa.get(i).equals("e")) {
        additional[i] = 9;
      }
    }
    return new MatrixDetails.MatrixDetailsBuilder()
        .matrix(matrix)
        .additional(additional)
        .build();
  }

  private int getIntFromSecondChar(List<String> lsa, int i) {
    return lsa.get(i).charAt(1) - 48;
  }

  private List<Node> generateLSA(MatrixDetails matrixDetails) {
    int[][] matrix = matrixDetails.getMatrix();
    int[] additional = matrixDetails.getAdditional();

    List<Node> nodes = new ArrayList<>();
    boolean x = false;
    boolean y = false;
    Node b = new Node("b", 0, 0);
    nodes.add(b);

    for (int i = 1; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] == 2) {
          x = true;
        }
        if (matrix[i][j] == 1) {
          y = true;
        }
      }
      if (x) {
        nodes.add(new Condition("x", additional[i], i));
      } else if (additional[i] == 9) {
        Node e = new Node("e", 0, i);
        nodes.add(e);
      } else if (y) {
        nodes.add(new Node("y", additional[i], i));
      }
      x = false;
      y = false;
    }
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getName().equals("y") || nodes.get(i).getName().equals("b")) {
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
      if (nodes.get(i).getName().equals("x")) {
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
