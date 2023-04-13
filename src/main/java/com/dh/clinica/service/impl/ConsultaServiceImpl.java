package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.ConsultaResponseDTO;
import com.dh.clinica.controller.dto.DentistaResponseDTO;
import com.dh.clinica.controller.dto.PacienteResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IConsultaRepository;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IConsultaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final IConsultaRepository consultaRepository;
    private final IPacienteRepository pacienteRepository;
    private final IDentistaRepository dentistaRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ConsultaServiceImpl(IConsultaRepository consultaRepository, IPacienteRepository pacienteRepository, IDentistaRepository dentistaRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.dentistaRepository = dentistaRepository;
    }

    @Override
    public ConsultaResponseDTO salvar(Consulta consulta) throws InvalidDataException {
        if (pacienteRepository.findById(consulta.getPaciente().getId()) != null
                && dentistaRepository.findById(consulta.getDentista().getId()) != null) {
            return toConsultaResponse(consultaRepository.save(consulta));
        } else {
            throw new InvalidDataException("Não foi possível cadastrar uma nova consulta!");
        }
    }

    @Override
    public List<ConsultaResponseDTO> buscarTodos() throws ResourceNotFoundException {
        List<Consulta> consultaList = consultaRepository.findAll();
        List<ConsultaResponseDTO> consultaResponseDTOList = new ArrayList<>();
        if (!consultaList.isEmpty()) {
            for (Consulta consulta : consultaList) {
                consultaResponseDTOList.add(toConsultaResponse(consulta));
            }
            return consultaResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não há Consultas cadastradas!");
        }

    }

    @Override
    public void excluir(Integer id) throws ResourceNotFoundException {
        if (consultaRepository.findById(id).isPresent()) {
            consultaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Id informado!");
        }
    }

    @Override
    public ConsultaResponseDTO buscar(Integer id) throws ResourceNotFoundException {
        Consulta consutaEntity = consultaRepository.findById(id).orElse(null);
        if (Objects.nonNull(consutaEntity)) {
            return toConsultaResponse(consutaEntity);
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Id informado!");
        }
    }

//    @Override
//    public Optional<Consulta> buscarPorDentista(String nomeDentista) {
//        return consultaRepository.findConsultaByDentista(nomeDentista);
//    }

    @Override
    public ConsultaResponseDTO atualizar(Consulta consulta) throws ResourceNotFoundException {
        if (consulta.getId() != null && consultaRepository.findById(consulta.getId()).isPresent()) {
            return toConsultaResponse(consultaRepository.save(consulta));
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Id informado!");
        }
    }

    @Override
    public List<ConsultaResponseDTO> findConsultaByNomeDentista(String nome) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByDentistaNome(nome);
        List<ConsultaResponseDTO> consultaResponseDTOList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseDTOList.add(toConsultaResponse(consulta));
            }
            return consultaResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Nome do Dentista informado!");
        }
    }

    @Override
    public List<ConsultaResponseDTO> findByDentistaMatricula(String matricula) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByDentistaMatricula(matricula);
        List<ConsultaResponseDTO> consultaResponseDTOList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseDTOList.add(toConsultaResponse(consulta));
            }
            return consultaResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para a Matricula do Dentista informada!");
        }
    }

    @Override
    public List<ConsultaResponseDTO> findConsultaByNomePaciente(String nome) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByPacienteNome(nome);
        List<ConsultaResponseDTO> consultaResponseDTOList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseDTOList.add(toConsultaResponse(consulta));
            }
            return consultaResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Nome do Paciente informado!");
        }
    }

    private ConsultaResponseDTO toConsultaResponse(Consulta consulta) {
        mapper.registerModule(new JavaTimeModule());
        return ConsultaResponseDTO.builder()
                .pacienteResponseDTO(mapper.convertValue(consulta.getPaciente(), PacienteResponseDTO.class))
                .dentistaResponseDTO(mapper.convertValue(consulta.getDentista(), DentistaResponseDTO.class))
                .dataConsulta(consulta.getDataConsulta())
                .build();
    }
}
