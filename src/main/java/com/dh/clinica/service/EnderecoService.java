package com.dh.clinica.service;


import com.dh.clinica.model.Endereco;
import com.dh.clinica.repository.IDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private IDao<Endereco> enderecoIDao;

    public EnderecoService(IDao<Endereco> domicilioDao) {
        this.enderecoIDao = domicilioDao;
    }

    public Endereco cadastrar(Endereco d) {
        return enderecoIDao.salvar(d);
    }

    public Optional<Endereco> buscar(Integer id) {
        return enderecoIDao.buscar(id);
    }

    public List<Endereco> buscarTodos() {
        return enderecoIDao.buscarTodos();
    }

    public void eliminar(Integer id) {
        enderecoIDao.excluir(id);
    }

}
