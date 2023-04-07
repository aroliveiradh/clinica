package com.dh.clinica.controller.dto;

import com.dh.clinica.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteResponse {

    private String nome;
    private String sobrenome;
    private LocalDate dataCadastro = LocalDate.now();
    private EnderecoDTO endereco;

}
