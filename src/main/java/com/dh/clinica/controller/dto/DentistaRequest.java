package com.dh.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DentistaRequest {

    private String nome;
    private String sobrenome;
    private String matricula;
}
