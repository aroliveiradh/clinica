package com.dh.clinica.service;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    UsuarioResponse salvar(UsuarioRequest usuario);

    List<UsuarioResponse> buscarTodos();

    void excluir(Integer id);

    Optional<UsuarioResponse> buscar(Integer id);

    Optional<UsuarioResponse> buscarPorNome(String nome);
    Optional<UsuarioResponse> buscarPorEmail(String email);

    UsuarioResponse atualizar(UsuarioRequest usuario);

}
