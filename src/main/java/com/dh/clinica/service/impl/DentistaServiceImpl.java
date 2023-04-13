package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.DentistaRequestDTO;
import com.dh.clinica.controller.dto.DentistaResponseDTO;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IDentistaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DentistaServiceImpl implements IDentistaService {

    private IDentistaRepository dentistaRepository;

    @Autowired
    public DentistaServiceImpl(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public DentistaResponseDTO salvar(DentistaRequestDTO request) throws ResourceNotFoundException {
        if (validaDentista(request)) {
            Dentista dentista = mapper.convertValue(request, Dentista.class);
            Dentista save = dentistaRepository.save(dentista);
            return mapper.convertValue(save, DentistaResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Não foi possível cadastrar o novo Dentista!");
        }
    }

    @Override
    public List<DentistaResponseDTO> buscarTodos() throws ResourceNotFoundException {
        List<Dentista> listaDentista = dentistaRepository.findAll();
        List<DentistaResponseDTO> listaDentistaResponseDTO = new ArrayList<>();
        if (!listaDentista.isEmpty()) {
            for (Dentista dentista : listaDentista) {
                listaDentistaResponseDTO.add(toDentistaResponse(dentista));
            }
            return listaDentistaResponseDTO;
        }
        throw new ResourceNotFoundException("Não há registros de Dentistas");
    }

    @Override
    public void excluir(Integer id) throws ResourceNotFoundException {
        if (dentistaRepository.findById(id).isPresent()) {
            dentistaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Id informado!");
        }
    }

    @Override
    public DentistaResponseDTO buscar(Integer id) throws ResourceNotFoundException {
        Dentista dentista = dentistaRepository.findById(id).orElse(null);
        if (Objects.nonNull(dentista)) {
            DentistaResponseDTO dentistaResponseDTO = toDentistaResponse(dentista);
            return dentistaResponseDTO;
        } else {
            throw new ResourceNotFoundException("Dentista não encontrado para o Id informado!");
        }
    }

    private DentistaResponseDTO toDentistaResponse(Dentista dentista) {
        return DentistaResponseDTO.builder()
                .nome(dentista.getNome())
                .sobrenome(dentista.getSobrenome())
                .build();
    }

    @Override
    public List<DentistaResponseDTO> buscarPorNome(String nome) throws ResourceNotFoundException {
        List<Dentista> dentistaList = dentistaRepository.findDentistaByNomeContainingIgnoreCase(nome);
        List<DentistaResponseDTO> dentistaResponseDTOList = new ArrayList<>();
        if (!dentistaList.isEmpty()) {
            for (Dentista dentista : dentistaList) {
                dentistaResponseDTOList.add(toDentistaResponse(dentista));
            }
            return dentistaResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrada para o Nome informado!");
        }
    }

    @Override
    public DentistaResponseDTO atualizar(Dentista dentista) throws ResourceNotFoundException {
        if (dentista.getId() != null
                && dentistaRepository.findById(dentista.getId()) != null) {
            Dentista dentistaAtualizado = dentistaRepository.saveAndFlush(dentista);
            DentistaResponseDTO dentistaResponseDTO = mapper.convertValue(dentistaAtualizado, DentistaResponseDTO.class);
            return dentistaResponseDTO;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrados para a Matricula informada!");
        }
    }

    @Override
    public DentistaResponseDTO buscarPorMatricula(String matricula) throws ResourceNotFoundException {
        Dentista dentista = dentistaRepository.findDentistaByMatriculaContainingIgnoreCase(matricula);
        if (Objects.nonNull(dentista)) {
            DentistaResponseDTO dentistaResponseDTO = toDentistaResponse(dentista);
            return dentistaResponseDTO;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrados para a Matricula informada!");
        }
    }

    private boolean validaDentista(DentistaRequestDTO request) {
        return Objects.nonNull(request.getNome()) &&
                !request.getNome().isEmpty() &&
                !request.getNome().isBlank() &&
                Objects.nonNull(request.getSobrenome()) &&
                !request.getSobrenome().isEmpty() &&
                !request.getSobrenome().isBlank() &&
                Objects.nonNull(request.getMatricula()) &&
                !request.getMatricula().isEmpty() &&
                !request.getMatricula().isBlank();
    }
}
