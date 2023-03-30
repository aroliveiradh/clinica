package com.dh.clinica.service;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.model.Dentista;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {

    Consulta salvar(Consulta consulta);
    List<Consulta> buscarTodos();
    void excluir(Integer id);
    Optional<Consulta> buscar(Integer id);
//    Optional<Consulta> buscarPorDentista(String nome);
    Consulta atualizar (Consulta consulta);
    List<Consulta> findConsultaByNomePaciente(String nome);
    List<Consulta> findConsultaByNomeDentista(String nome);
    List<Consulta> findByDentistaMatricula(String matricula);

}
