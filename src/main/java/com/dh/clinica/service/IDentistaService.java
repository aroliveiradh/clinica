package com.dh.clinica.service;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;

import java.util.List;
import java.util.Optional;

public interface IDentistaService {

    DentistaResponse salvar(DentistaRequest request);
    List<DentistaResponse> buscarTodos();
    void excluir(Integer id);
    DentistaResponse buscar(Integer id) throws ResourceNotFoundException;
    Optional<DentistaResponse> buscarPorNome(String nome);
    DentistaResponse atualizar (DentistaRequest dentista);
    Optional<DentistaResponse> buscarPorMatricula(String matricula);
}
