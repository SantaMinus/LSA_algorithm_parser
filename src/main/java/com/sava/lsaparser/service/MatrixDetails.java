package com.sava.lsaparser.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatrixDetails {

  private int[][] matrix;
  private int[] additional;
}
