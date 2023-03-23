package com.dh.clinica.service;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private IDao<Consulta> consultaIDao;

    public ConsultaService(IDao<Consulta> consultaIDao) {
        this.consultaIDao = consultaIDao;
    }

    public Consulta cadastrar(Consulta consulta) {
        return consultaIDao.salvar(consulta);
    }

    public List<Consulta> buscarTodos(){
        return consultaIDao.buscarTodos();
    }

    public Optional<Consulta> bucarPorId(Integer id) {
        return consultaIDao.buscar(id);
    }

    public Consulta atualizar(Consulta consulta) {
        return consultaIDao.atualizar(consulta);
    }

    public void excluir (Integer id) {
        consultaIDao.excluir(id);
    }
}
