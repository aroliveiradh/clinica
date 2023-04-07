package com.dh.clinica.service;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    PacienteResponse salvar(PacienteRequest paciente);

    List<Paciente> buscarTodos();

    void excluir(Integer id);

    Optional<Paciente> buscar(Integer id);

    Optional<Paciente> buscarPorNome(String nome);

    Paciente atualizar(Paciente paciente);

}
