package com.dh.clinica.service;

import com.dh.clinica.model.Dentista;

import java.util.List;
import java.util.Optional;

public interface IDentistaService {

    Dentista salvar(Dentista dentista);
    List<Dentista> buscarTodos();
    void excluir(Integer id);
    Optional<Dentista> buscar(Integer id);
    Optional<Dentista> buscarPorNome(String nome);
    Dentista atualizar (Dentista dentista);

}
