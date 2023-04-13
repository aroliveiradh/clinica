package com.dh.clinica.service.impl;


import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PacienteServiceImpl implements IPacienteService {

    ObjectMapper mapper = new ObjectMapper();
    private IPacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponse salvar(PacienteRequest pacienteRequest) throws InvalidDataException {
        if (validaEnderecoDTO(pacienteRequest)) {
            mapper.registerModule(new JavaTimeModule());
            Paciente pacienteEntity = mapper.convertValue(pacienteRequest, Paciente.class);
            Paciente pacienteSalvo = pacienteRepository.save(pacienteEntity);
            PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
            return pacienteResponse;
        } else {
            throw new InvalidDataException("Não foi possível cadastrar um novo Paciente!");
        }
    }

    public PacienteResponse buscar(Integer id) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (Objects.nonNull(paciente)) {
            return mapper.convertValue(paciente, PacienteResponse.class);
        } else {
            throw new ResourceNotFoundException("Nenhum paciente foi encontrado para o Id informado!");
        }
    }

    @Override
    public PacienteResponse buscarPorNome(String nome) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findPacienteByNomeContainingIgnoreCase(nome);
        if (Objects.nonNull(paciente)) {
            return mapper.convertValue(paciente, PacienteResponse.class);
        } else {
            throw new ResourceNotFoundException("Nenhum paciente foi encontrado para o Id informado!");
        }
    }

    public List<PacienteResponse> buscarTodos() throws ResourceNotFoundException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteResponse> listaDentistaResponse = new ArrayList<>();
        if (!pacientes.isEmpty()) {
            for (Paciente paciente : pacientes) {
                listaDentistaResponse.add(mapper.convertValue(paciente, PacienteResponse.class));
                return listaDentistaResponse;
            }
        }
        throw new ResourceNotFoundException("Não há Pacientes cadastrados!");
    }

    public void excluir(Integer id) throws ResourceNotFoundException {
        if (pacienteRepository.findById(id).isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Não há registro de Paciente para o Id informado!");
        }
    }

    public PacienteResponse atualizar(Paciente paciente) throws ResourceNotFoundException {
        if (paciente.getId() != null && pacienteRepository.findById(paciente.getId()) != null) {
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            mapper.registerModule(new JavaTimeModule());
            PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
            return pacienteResponse;
        } else {
            throw new ResourceNotFoundException("Não há registro de Paciente com o Id informado!");
        }

    }

    private static boolean validaEnderecoDTO(PacienteRequest pacienteRequest) {
        return Objects.nonNull(pacienteRequest.getEndereco()) &&
                Objects.nonNull(pacienteRequest.getEndereco().getCidade()) &&
                !pacienteRequest.getEndereco().getCidade().isEmpty() &&
                !pacienteRequest.getEndereco().getCidade().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getRua()) &&
                !pacienteRequest.getEndereco().getRua().isEmpty() &&
                !pacienteRequest.getEndereco().getRua().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getNumero()) &&
                !pacienteRequest.getEndereco().getNumero().isEmpty() &&
                !pacienteRequest.getEndereco().getNumero().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getEstado()) &&
                !pacienteRequest.getEndereco().getEstado().isEmpty() &&
                !pacienteRequest.getEndereco().getEstado().isBlank();
    }

}

