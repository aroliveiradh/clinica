package com.dh.clinica.service;

import com.dh.clinica.controller.dto.UsuarioRequestDTO;
import com.dh.clinica.controller.dto.UsuarioResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    UsuarioResponseDTO salvar(UsuarioRequestDTO usuario) throws InvalidDataException;

    List<UsuarioResponseDTO> buscarTodos() throws ResourceNotFoundException;

    void excluir(Integer id) throws ResourceNotFoundException;

    UsuarioResponseDTO buscar(Integer id) throws ResourceNotFoundException;

    List<UsuarioResponseDTO> buscarPorNome(String nome) throws ResourceNotFoundException;
    UsuarioResponseDTO buscarPorEmail(String email);

    UsuarioResponseDTO atualizar(Usuario usuario) throws ResourceNotFoundException;

}
