package com.sava.lsaparser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Data
public class Lsa {
    @NotBlank
    @Length(max = 100)
    private String algorithmInput;
}
