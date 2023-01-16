package com.sava.lsaparser.dto;

import com.sava.lsaparser.validate.PasswordMatches;
import com.sava.lsaparser.validate.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    private String matchingPassword;
    private String username;
}
