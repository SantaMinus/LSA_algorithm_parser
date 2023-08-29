package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.exception.LsaValidationException;
import com.sava.lsaparser.structure.Node;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
      // TODO: handle exception
      return null;
    }
  }
}
