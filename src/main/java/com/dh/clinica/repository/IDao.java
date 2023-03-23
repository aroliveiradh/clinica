package com.dh.clinica.repository;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {

    T salvar(T t);
    List<T> buscarTodos();
    void excluir(Integer id);
    Optional<T> buscar(Integer id);
    T atualizar (T t);
}
