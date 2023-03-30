package com.dh.clinica.service;

import com.dh.clinica.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente salvar(Paciente paciente);

    List<Paciente> buscarTodos();

    void excluir(Integer id);

    Optional<Paciente> buscar(Integer id);

    Optional<Paciente> buscarPorNome(String nome);

    Paciente atualizar(Paciente paciente);

}
