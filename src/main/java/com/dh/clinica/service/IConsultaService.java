package com.dh.clinica.service;

import com.dh.clinica.controller.dto.ConsultaResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Consulta;

import java.util.List;

public interface IConsultaService {

    ConsultaResponse salvar(Consulta consulta) throws InvalidDataException;
    List<ConsultaResponse> buscarTodos() throws ResourceNotFoundException;
    void excluir(Integer id) throws ResourceNotFoundException;
    ConsultaResponse buscar(Integer id) throws ResourceNotFoundException;
//    Optional<Consulta> buscarPorDentista(String nome);
    ConsultaResponse atualizar (Consulta consulta) throws ResourceNotFoundException;
    List<ConsultaResponse> findConsultaByNomePaciente(String nome) throws ResourceNotFoundException;
    List<ConsultaResponse> findConsultaByNomeDentista(String nome) throws ResourceNotFoundException;
    List<ConsultaResponse> findByDentistaMatricula(String matricula) throws ResourceNotFoundException;

}
