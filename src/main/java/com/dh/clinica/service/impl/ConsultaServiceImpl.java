package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.ConsultaResponse;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.controller.dto.PacienteResponse;
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
    public ConsultaResponse salvar(Consulta consulta) throws InvalidDataException {
        if (pacienteRepository.findById(consulta.getPaciente().getId()) != null
                && dentistaRepository.findById(consulta.getDentista().getId()) != null) {
            return toConsultaResponse(consultaRepository.save(consulta));
        } else {
            throw new InvalidDataException("Não foi possível cadastrar uma nova consulta!");
        }
    }

    @Override
    public List<ConsultaResponse> buscarTodos() throws ResourceNotFoundException {
        List<Consulta> consultaList = consultaRepository.findAll();
        List<ConsultaResponse> consultaResponseList = new ArrayList<>();
        if (!consultaList.isEmpty()) {
            for (Consulta consulta : consultaList) {
                consultaResponseList.add(toConsultaResponse(consulta));
            }
            return consultaResponseList;
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
    public ConsultaResponse buscar(Integer id) throws ResourceNotFoundException {
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
    public ConsultaResponse atualizar(Consulta consulta) throws ResourceNotFoundException {
        if (consulta.getId() != null && consultaRepository.findById(consulta.getId()).isPresent()) {
            return toConsultaResponse(consultaRepository.save(consulta));
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Id informado!");
        }
    }

    @Override
    public List<ConsultaResponse> findConsultaByNomeDentista(String nome) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByDentistaNome(nome);
        List<ConsultaResponse> consultaResponseList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseList.add(toConsultaResponse(consulta));
            }
            return consultaResponseList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Nome do Dentista informado!");
        }
    }

    @Override
    public List<ConsultaResponse> findByDentistaMatricula(String matricula) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByDentistaMatricula(matricula);
        List<ConsultaResponse> consultaResponseList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseList.add(toConsultaResponse(consulta));
            }
            return consultaResponseList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para a Matricula do Dentista informada!");
        }
    }

    @Override
    public List<ConsultaResponse> findConsultaByNomePaciente(String nome) throws ResourceNotFoundException {
        List<Consulta> consultaEntityList = consultaRepository.findByPacienteNome(nome);
        List<ConsultaResponse> consultaResponseList = new ArrayList<>();
        if (!consultaEntityList.isEmpty()) {
            for (Consulta consulta : consultaEntityList) {
                consultaResponseList.add(toConsultaResponse(consulta));
            }
            return consultaResponseList;
        } else {
            throw new ResourceNotFoundException("Não foi encontrado nenhuma Consulta para o Nome do Paciente informado!");
        }
    }

    private ConsultaResponse toConsultaResponse(Consulta consulta) {
        mapper.registerModule(new JavaTimeModule());
        return ConsultaResponse.builder()
                .pacienteResponse(mapper.convertValue(consulta.getPaciente(), PacienteResponse.class))
                .dentistaResponse(mapper.convertValue(consulta.getDentista(), DentistaResponse.class))
                .dataConsulta(consulta.getDataConsulta())
                .build();
    }
}
