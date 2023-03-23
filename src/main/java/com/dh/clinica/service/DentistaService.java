package com.dh.clinica.service;

import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {

    private IDao<Dentista> dentistaIDao;

    public DentistaService(IDao<Dentista> dentistaIDao) {
        this.dentistaIDao = dentistaIDao;
    }

    public Dentista cadastrar(Dentista dentista) {
        return dentistaIDao.salvar(dentista);
    }

    public List<Dentista> buscarTodos() {
        return dentistaIDao.buscarTodos();
    }

    public void excluir(Integer id) {
        dentistaIDao.excluir(id);
    }

    public Optional<Dentista> buscarPorId(Integer id) {
        return dentistaIDao.buscar(id);
    }

    public Dentista atualizar (Dentista dentista){
        return dentistaIDao.atualizar(dentista);
    }
}
