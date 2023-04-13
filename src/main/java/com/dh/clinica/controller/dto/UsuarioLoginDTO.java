package com.dh.clinica.controller.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioLoginDTO {

    private String login;
    private String senha;
}
