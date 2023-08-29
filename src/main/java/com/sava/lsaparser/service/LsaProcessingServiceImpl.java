package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.exception.LsaValidationException;
import com.sava.lsaparser.structure.Node;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LsaProcessingServiceImpl implements LsaProcessingService {

  private final LsaValidatorService lsaValidatorService;
  private final LsaParserService lsaParserService;

  @Override
  public List<Node> processLsa(Lsa lsa) {
    String lsaString = lsa.getAlgorithmInput();
    try {
      lsaValidatorService.validate(lsaString);
      return lsaParserService.calculate(lsaString);
    } catch (LsaValidationException e) {
      log.warn("LSA validation failed, returning an empty list", e);
      return Collections.emptyList();
    }
  }
}
