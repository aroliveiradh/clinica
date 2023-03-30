package com.dh.clinica.service.impl;


import com.dh.clinica.model.Endereco;
import com.dh.clinica.repository.IEnderecoRepository;
import com.dh.clinica.service.IEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IEnderecoService {

    private IEnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Optional<Endereco> buscar(Integer id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Endereco atualizar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    public void excluir(Integer id) {
        enderecoRepository.deleteById(id);
    }

}
