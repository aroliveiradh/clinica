package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IDentistaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImpl implements IDentistaService {

    private IDentistaRepository dentistaRepository;

    @Autowired
    public DentistaServiceImpl(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public DentistaResponse salvar(DentistaRequest request) {
        Dentista dentista = mapper.convertValue(request, Dentista.class);
        Dentista save = dentistaRepository.save(dentista);
        DentistaResponse dentistaResponse1 = mapper.convertValue(save, DentistaResponse.class);
        return dentistaResponse1;
    }

    @Override
    public List<DentistaResponse> buscarTodos() {
        List<Dentista> listaDentista = dentistaRepository.findAll();
        List<DentistaResponse> listaDentistaResponse = new ArrayList<>();
        for (Dentista dentista : listaDentista) {
            listaDentistaResponse.add(mapper.convertValue(dentista, DentistaResponse.class));
        }
        return listaDentistaResponse;
    }

    @Override
    public void excluir(Integer id) {
        dentistaRepository.deleteById(id);
    }

    @Override
    public Optional<DentistaResponse> buscar(Integer id) {
        Optional<Dentista> dentista = dentistaRepository.findById(id);
        DentistaResponse dentistaResponse = toDentistaResponse(dentista);
//        DentistaResponse dentistaResponse = mapper.convertValue(dentistaRepository.findById(id), DentistaResponse.class);
        return Optional.ofNullable(dentistaResponse);
    }

    private DentistaResponse toDentistaResponse(Optional<Dentista> dentista) {
        return DentistaResponse.builder()
                .nome(dentista.get().getNome())
                .sobrenome(dentista.get().getSobrenome())
                .build();
    }

    @Override
    public Optional<DentistaResponse> buscarPorNome(String nome) {
        Optional<Dentista> dentista = dentistaRepository.findDentistaByNomeContainingIgnoreCase(nome);
        DentistaResponse dentistaResponse = toDentistaResponse(dentista);
        return Optional.ofNullable(dentistaResponse);
    }

    @Override
    public DentistaResponse atualizar(DentistaRequest dentista) {
        Dentista dentistaEntity = mapper.convertValue(dentista, Dentista.class);
        Dentista dentistaAtualizado = dentistaRepository.saveAndFlush(dentistaEntity);
        DentistaResponse dentistaResponse = mapper.convertValue(dentistaAtualizado, DentistaResponse.class);
        return dentistaResponse;
    }

    @Override
    public Optional<DentistaResponse> buscarPorMatricula(String matricula) {
        Optional<Dentista> dentista = dentistaRepository.findDentistaByMatriculaContainingIgnoreCase(matricula);
        DentistaResponse dentistaResponse = toDentistaResponse(dentista);
        return Optional.ofNullable(dentistaResponse);
    }
}
