package com.dh.clinica.service;

import com.dh.clinica.controller.dto.ConsultaResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Consulta;

import java.util.List;

public interface IConsultaService {

    ConsultaResponseDTO salvar(Consulta consulta) throws InvalidDataException;
    List<ConsultaResponseDTO> buscarTodos() throws ResourceNotFoundException;
    void excluir(Integer id) throws ResourceNotFoundException;
    ConsultaResponseDTO buscar(Integer id) throws ResourceNotFoundException;
//    Optional<Consulta> buscarPorDentista(String nome);
    ConsultaResponseDTO atualizar (Consulta consulta) throws ResourceNotFoundException;
    List<ConsultaResponseDTO> findConsultaByNomePaciente(String nome) throws ResourceNotFoundException;
    List<ConsultaResponseDTO> findConsultaByNomeDentista(String nome) throws ResourceNotFoundException;
    List<ConsultaResponseDTO> findByDentistaMatricula(String matricula) throws ResourceNotFoundException;

}
