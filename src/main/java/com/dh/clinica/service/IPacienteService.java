package com.dh.clinica.service;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;

import java.util.List;

public interface IPacienteService {

    PacienteResponse salvar(PacienteRequest paciente) throws InvalidDataException;

    List<PacienteResponse> buscarTodos() throws ResourceNotFoundException;

    void excluir(Integer id) throws ResourceNotFoundException;

    PacienteResponse buscar(Integer id) throws ResourceNotFoundException;

    PacienteResponse buscarPorNome(String nome) throws ResourceNotFoundException;

    PacienteResponse atualizar(Paciente paciente) throws ResourceNotFoundException;

}
