package com.sava.lsaparser.service;

import com.sava.lsaparser.exception.LsaValidationException;

public interface LsaValidatorService {

    void validate(String lsa) throws LsaValidationException;
}
