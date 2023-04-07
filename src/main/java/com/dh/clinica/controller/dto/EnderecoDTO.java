package com.dh.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
}
