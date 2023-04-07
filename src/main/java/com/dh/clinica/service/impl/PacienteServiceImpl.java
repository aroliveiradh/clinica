package com.dh.clinica.service.impl;


import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    ObjectMapper mapper = new ObjectMapper();
    private IPacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponse salvar(PacienteRequest pacienteRequest) {
        mapper.registerModule(new JavaTimeModule());
        Paciente pacienteEntity = mapper.convertValue(pacienteRequest, Paciente.class);
        Paciente pacienteSalvo = pacienteRepository.save(pacienteEntity);
        PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
        return pacienteResponse;
    }

    public Optional<Paciente> buscar(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Optional<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findPacienteByNomeContainingIgnoreCase(nome);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    public void excluir(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente atualizar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

}

