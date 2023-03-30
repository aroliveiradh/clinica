package com.dh.clinica.service;

import com.dh.clinica.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface IEnderecoService {

    Endereco salvar(Endereco endereco);

    List<Endereco> buscarTodos();

    void excluir(Integer id);

    Optional<Endereco> buscar(Integer id);

    //    Optional<Consulta> buscarPorNome(String nome);
    Endereco atualizar(Endereco endereco);

}
