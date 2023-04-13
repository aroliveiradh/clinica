package com.dh.clinica.controller.dto;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaResponse {

    private PacienteResponse pacienteResponse;

    private DentistaResponse dentistaResponse;

    private LocalDateTime dataConsulta;
}
