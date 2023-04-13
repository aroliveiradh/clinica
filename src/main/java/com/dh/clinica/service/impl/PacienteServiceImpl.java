package com.dh.clinica.service.impl;


import com.dh.clinica.controller.dto.PacienteRequestDTO;
import com.dh.clinica.controller.dto.PacienteResponseDTO;
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

    public PacienteResponseDTO salvar(PacienteRequestDTO pacienteRequestDTO) throws InvalidDataException {
        if (validaEnderecoDTO(pacienteRequestDTO)) {
            mapper.registerModule(new JavaTimeModule());
            Paciente pacienteEntity = mapper.convertValue(pacienteRequestDTO, Paciente.class);
            Paciente pacienteSalvo = pacienteRepository.save(pacienteEntity);
            PacienteResponseDTO pacienteResponseDTO = mapper.convertValue(pacienteSalvo, PacienteResponseDTO.class);
            return pacienteResponseDTO;
        } else {
            throw new InvalidDataException("Não foi possível cadastrar um novo Paciente!");
        }
    }

    public PacienteResponseDTO buscar(Integer id) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (Objects.nonNull(paciente)) {
            return mapper.convertValue(paciente, PacienteResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Nenhum paciente foi encontrado para o Id informado!");
        }
    }

    @Override
    public PacienteResponseDTO buscarPorNome(String nome) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findPacienteByNomeContainingIgnoreCase(nome);
        if (Objects.nonNull(paciente)) {
            return mapper.convertValue(paciente, PacienteResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Nenhum paciente foi encontrado para o Id informado!");
        }
    }

    public List<PacienteResponseDTO> buscarTodos() throws ResourceNotFoundException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteResponseDTO> listaDentistaResponse = new ArrayList<>();
        if (!pacientes.isEmpty()) {
            for (Paciente paciente : pacientes) {
                listaDentistaResponse.add(mapper.convertValue(paciente, PacienteResponseDTO.class));
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

    public PacienteResponseDTO atualizar(Paciente paciente) throws ResourceNotFoundException {
        if (paciente.getId() != null && pacienteRepository.findById(paciente.getId()) != null) {
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            mapper.registerModule(new JavaTimeModule());
            PacienteResponseDTO pacienteResponseDTO = mapper.convertValue(pacienteSalvo, PacienteResponseDTO.class);
            return pacienteResponseDTO;
        } else {
            throw new ResourceNotFoundException("Não há registro de Paciente com o Id informado!");
        }

    }

    private static boolean validaEnderecoDTO(PacienteRequestDTO pacienteRequestDTO) {
        return Objects.nonNull(pacienteRequestDTO.getEndereco()) &&
                Objects.nonNull(pacienteRequestDTO.getEndereco().getCidade()) &&
                !pacienteRequestDTO.getEndereco().getCidade().isEmpty() &&
                !pacienteRequestDTO.getEndereco().getCidade().isBlank() &&
                Objects.nonNull(pacienteRequestDTO.getEndereco().getRua()) &&
                !pacienteRequestDTO.getEndereco().getRua().isEmpty() &&
                !pacienteRequestDTO.getEndereco().getRua().isBlank() &&
                Objects.nonNull(pacienteRequestDTO.getEndereco().getNumero()) &&
                !pacienteRequestDTO.getEndereco().getNumero().isEmpty() &&
                !pacienteRequestDTO.getEndereco().getNumero().isBlank() &&
                Objects.nonNull(pacienteRequestDTO.getEndereco().getEstado()) &&
                !pacienteRequestDTO.getEndereco().getEstado().isEmpty() &&
                !pacienteRequestDTO.getEndereco().getEstado().isBlank();
    }

}

