package com.dh.clinica.service.impl;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IDentistaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImpl implements IDentistaService {

    private IDentistaRepository dentistaRepository;

    public DentistaServiceImpl(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    @Override
    public Dentista salvar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }

    @Override
    public List<Dentista> buscarTodos() {
        return dentistaRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {
        dentistaRepository.deleteById(id);
    }

    @Override
    public Optional<Dentista> buscar(Integer id) {
        return dentistaRepository.findById(id);
    }

    @Override
    public Optional<Dentista> buscarPorNome(String nome) {
        return dentistaRepository.findDentistaByNomeContainingIgnoreCase(nome);
    }

    @Override
    public Dentista atualizar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }
}
