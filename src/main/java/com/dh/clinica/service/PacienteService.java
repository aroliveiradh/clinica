package com.dh.clinica.service;


import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente cadastrar(Paciente p) {
        //p.setDataCadastro(new Date());
        return pacienteIDao.salvar(p);
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteIDao.buscar(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteIDao.buscarTodos();
    }

    public void excluir(Integer id) {
        pacienteIDao.excluir(id);
    }

    public Paciente atualizar(Paciente paciente) {
        return pacienteIDao.atualizar(paciente);
    }

//    public Paciente atualizar (Paciente paciente){
//        return pacienteIDao.atualizar(paciente);
//    }
}

