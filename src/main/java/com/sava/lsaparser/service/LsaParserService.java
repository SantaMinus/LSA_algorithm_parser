package com.sava.lsaparser.service;

import com.sava.lsaparser.structure.Node;
import java.util.List;

public interface LsaParserService {

  List<Node> calculate(String lsa);
}
