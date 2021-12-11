package com.sava.lsaparser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Lsa {
    @NotBlank
    private String algorithmInput;
}
