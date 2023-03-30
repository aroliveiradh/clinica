package com.dh.clinica.service;

import com.dh.clinica.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    Usuario salvar(Usuario usuario);

    List<Usuario> buscarTodos();

    void excluir(Integer id);

    Optional<Usuario> buscar(Integer id);

    Optional<Usuario> buscarPorNome(String nome);

    Usuario atualizar(Usuario usuario);

}
