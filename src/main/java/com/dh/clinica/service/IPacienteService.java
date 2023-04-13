package com.dh.clinica.service;

import com.dh.clinica.controller.dto.PacienteRequestDTO;
import com.dh.clinica.controller.dto.PacienteResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;

import java.util.List;

public interface IPacienteService {

    PacienteResponseDTO salvar(PacienteRequestDTO paciente) throws InvalidDataException;

    List<PacienteResponseDTO> buscarTodos() throws ResourceNotFoundException;

    void excluir(Integer id) throws ResourceNotFoundException;

    PacienteResponseDTO buscar(Integer id) throws ResourceNotFoundException;

    PacienteResponseDTO buscarPorNome(String nome) throws ResourceNotFoundException;

    PacienteResponseDTO atualizar(Paciente paciente) throws ResourceNotFoundException;

}
