package com.sava.lsaparser.service;

import com.sava.lsaparser.structure.Condition;
import com.sava.lsaparser.structure.Node;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LsaParserServiceImpl implements LsaParserService {

  @Override
  public void calculate(String lsa) {
    AdjacencyDetails adjacency = buildAdjacencyMatrix(lsa);
    List<Node> lsaNodes = generateLSA(adjacency);
    buildReachabilityMatrix(adjacency.getAdjMatrix(), lsaNodes);
  }

  private AdjacencyDetails buildAdjacencyMatrix(String lsa) {
    // TODO: implement
    return new AdjacencyDetails();
  }

  private List<Node> generateLSA(AdjacencyDetails adjacency) {
    int[][] adjacencyMatrix = adjacency.getAdjMatrix();
    int[] additional = adjacency.getAdditional();

    List<Node> nodes = new ArrayList<>();
    boolean x = false;
    boolean y = false;
    Node b = new Node("b", 0, 0);
    nodes.add(b);

    for (int i = 1; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix.length; j++) {
        if (adjacencyMatrix[i][j] == 2) {
          x = true;
        }
        if (adjacencyMatrix[i][j] == 1) {
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
        for (int j = 0; j < adjacencyMatrix.length; j++) {
          if (adjacencyMatrix[nodes.get(i).getN()][j] == 1) {
            for (Node node : nodes) {
              if (node.getN() == j) {
                nodes.get(i).setNext(node);
              }
            }
          }
        }
      }
      if (nodes.get(i).getName().equals("x")) {
        for (int j = 0; j < adjacencyMatrix.length; j++) {
          if (adjacencyMatrix[nodes.get(i).getN()][j] == 1) {
            for (Node node : nodes) {
              if (node.getN() == j) {
                nodes.get(i).setNext(node);
              }
            }
          }
          if (adjacencyMatrix[nodes.get(i).getN()][j] == 2) {
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
