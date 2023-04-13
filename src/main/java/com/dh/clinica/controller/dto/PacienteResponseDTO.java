package com.dh.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteResponseDTO {

    private String nome;
    private String sobrenome;
    private LocalDate dataCadastro = LocalDate.now();
    private EnderecoDTO endereco;

}
