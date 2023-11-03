package com.sava.lsaparser.service

import com.sava.lsaparser.exception.LsaValidationException
import spock.lang.Specification

class LsaValidatorServiceImplTest extends Specification {
  LsaValidatorService validator = new LsaValidatorServiceImpl()

  def "validate() returns true for valid input"() {
    expect:
    validator.validate('b x1 y1 o1 x2 i1 x3 e')
  }

  def "validate() throws LsaValidationException when no begin"() {
    when:
    validator.validate('x1 y1 o1 x2 i1 x3 e')

    then:
    LsaValidationException e = thrown(LsaValidationException)
    e.message == "LSA must begin with 'b'/'B'"
  }

  def "validate() throws LsaValidationException when multiple begins"() {
    when:
    validator.validate('b x1 y1 b o1 x2 i1 x3 e')

    then:
    LsaValidationException e = thrown(LsaValidationException)
    e.message == 'Only one BEGIN is allowed'
  }

  def "validate() throws LsaValidationException when no end"() {
    when:
    validator.validate('b x1 y1 o1 x2 i1 x3')

    then:
    LsaValidationException e = thrown(LsaValidationException)
    e.message == "LSA must end with 'end'"
  }

  def "validate() throws LsaValidationException when invalid char occurs"() {
    when:
    validator.validate('b x1 y1 o1 f x2 i1 x3 e')

    then:
    LsaValidationException e = thrown(LsaValidationException)
    e.message == 'Wrong character contained: f'
  }

  def "validate() throws LsaValidationException when input number != output number"() {
    when:
    validator.validate(lsaString)

    then:
    LsaValidationException e = thrown(LsaValidationException)
    e.message == 'Input/output quantity mismatch'

    where:
    lsaString << ['b x1 y1 o1 x2 y2 o2 i1 x3 e',
                  'b x1 y1 o1 x2 y2 i1 x3 i2 e',
                  'b x1 y1 o1 x2 y2 x3 e',
                  'b x1 y1 x2 i1 x3 e']
  }
}
