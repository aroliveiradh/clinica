package com.dh.clinica.service;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    UsuarioResponse salvar(UsuarioRequest usuario) throws InvalidDataException;

    List<UsuarioResponse> buscarTodos() throws ResourceNotFoundException;

    void excluir(Integer id) throws ResourceNotFoundException;

    UsuarioResponse buscar(Integer id) throws ResourceNotFoundException;

    List<UsuarioResponse> buscarPorNome(String nome) throws ResourceNotFoundException;
    UsuarioResponse buscarPorEmail(String email);

    UsuarioResponse atualizar(Usuario usuario) throws ResourceNotFoundException;

}
