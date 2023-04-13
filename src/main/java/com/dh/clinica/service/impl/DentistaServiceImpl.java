package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
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
    public DentistaResponse salvar(DentistaRequest request) throws ResourceNotFoundException {
        if (validaDentista(request)) {
            Dentista dentista = mapper.convertValue(request, Dentista.class);
            Dentista save = dentistaRepository.save(dentista);
            return mapper.convertValue(save, DentistaResponse.class);
        } else {
            throw new ResourceNotFoundException("Não foi possível cadastrar o novo Dentista!");
        }
    }

    @Override
    public List<DentistaResponse> buscarTodos() throws ResourceNotFoundException {
        List<Dentista> listaDentista = dentistaRepository.findAll();
        List<DentistaResponse> listaDentistaResponse = new ArrayList<>();
        if (!listaDentista.isEmpty()) {
            for (Dentista dentista : listaDentista) {
                listaDentistaResponse.add(toDentistaResponse(dentista));
            }
            return listaDentistaResponse;
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
    public DentistaResponse buscar(Integer id) throws ResourceNotFoundException {
        Dentista dentista = dentistaRepository.findById(id).orElse(null);
        if (Objects.nonNull(dentista)) {
            DentistaResponse dentistaResponse = toDentistaResponse(dentista);
            return dentistaResponse;
        } else {
            throw new ResourceNotFoundException("Dentista não encontrado para o Id informado!");
        }
    }

    private DentistaResponse toDentistaResponse(Dentista dentista) {
        return DentistaResponse.builder()
                .nome(dentista.getNome())
                .sobrenome(dentista.getSobrenome())
                .build();
    }

    @Override
    public List<DentistaResponse> buscarPorNome(String nome) throws ResourceNotFoundException {
        List<Dentista> dentistaList = dentistaRepository.findDentistaByNomeContainingIgnoreCase(nome);
        List<DentistaResponse> dentistaResponseList = new ArrayList<>();
        if (!dentistaList.isEmpty()) {
            for (Dentista dentista : dentistaList) {
                dentistaResponseList.add(toDentistaResponse(dentista));
            }
            return dentistaResponseList;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrada para o Nome informado!");
        }
    }

    @Override
    public DentistaResponse atualizar(Dentista dentista) throws ResourceNotFoundException {
        if (dentista.getId() != null
                && dentistaRepository.findById(dentista.getId()) != null) {
            Dentista dentistaAtualizado = dentistaRepository.saveAndFlush(dentista);
            DentistaResponse dentistaResponse = mapper.convertValue(dentistaAtualizado, DentistaResponse.class);
            return dentistaResponse;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrados para a Matricula informada!");
        }
    }

    @Override
    public DentistaResponse buscarPorMatricula(String matricula) throws ResourceNotFoundException {
        Dentista dentista = dentistaRepository.findDentistaByMatriculaContainingIgnoreCase(matricula);
        if (Objects.nonNull(dentista)) {
            DentistaResponse dentistaResponse = toDentistaResponse(dentista);
            return dentistaResponse;
        } else {
            throw new ResourceNotFoundException("Não há Dentista cadastrados para a Matricula informada!");
        }
    }

    private boolean validaDentista(DentistaRequest request) {
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
