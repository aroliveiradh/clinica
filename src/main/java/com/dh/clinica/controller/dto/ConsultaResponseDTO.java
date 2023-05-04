package com.dh.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaResponseDTO {

    private PacienteResponseDTO pacienteResponseDTO;

    private DentistaResponseDTO dentistaResponseDTO;

    private LocalDateTime dataHoraConsulta;
}
