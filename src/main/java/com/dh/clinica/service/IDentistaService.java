package com.dh.clinica.service;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;

import java.util.List;

public interface IDentistaService {

    DentistaResponse salvar(DentistaRequest request) throws ResourceNotFoundException;
    List<DentistaResponse> buscarTodos() throws ResourceNotFoundException;
    void excluir(Integer id) throws ResourceNotFoundException;
    DentistaResponse buscar(Integer id) throws ResourceNotFoundException;
    List<DentistaResponse> buscarPorNome(String nome) throws ResourceNotFoundException;
    DentistaResponse atualizar (Dentista dentista) throws ResourceNotFoundException;
    DentistaResponse buscarPorMatricula(String matricula) throws ResourceNotFoundException;
}
