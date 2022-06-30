package com.sava.lsaparser.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
// TODO: fix AmbiguousTableNameException (enable qualifiedTableNames in DBUnit)
@Table(name = "consumer", schema = "public")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}
