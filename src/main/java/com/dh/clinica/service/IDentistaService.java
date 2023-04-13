package com.dh.clinica.service;

import com.dh.clinica.controller.dto.DentistaRequestDTO;
import com.dh.clinica.controller.dto.DentistaResponseDTO;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;

import java.util.List;

public interface IDentistaService {

    DentistaResponseDTO salvar(DentistaRequestDTO request) throws ResourceNotFoundException;
    List<DentistaResponseDTO> buscarTodos() throws ResourceNotFoundException;
    void excluir(Integer id) throws ResourceNotFoundException;
    DentistaResponseDTO buscar(Integer id) throws ResourceNotFoundException;
    List<DentistaResponseDTO> buscarPorNome(String nome) throws ResourceNotFoundException;
    DentistaResponseDTO atualizar (Dentista dentista) throws ResourceNotFoundException;
    DentistaResponseDTO buscarPorMatricula(String matricula) throws ResourceNotFoundException;
}
