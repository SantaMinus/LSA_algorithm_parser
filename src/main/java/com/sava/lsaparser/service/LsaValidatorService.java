package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.exception.LsaValidationException;

public interface LsaValidatorService {

    void validate(Lsa lsa) throws LsaValidationException;
}
