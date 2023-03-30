package com.dh.clinica.service.impl;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IConsultaRepository;
import com.dh.clinica.service.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements IConsultaService {


    private final IConsultaRepository consultaRepository;

    @Autowired
    public ConsultaServiceImpl(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }


    @Override
    public Consulta salvar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @Override
    public List<Consulta> buscarTodos() {
        return consultaRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public Optional<Consulta> buscar(Integer id) {
        return consultaRepository.findById(id);
    }

//    @Override
//    public Optional<Consulta> buscarPorDentista(String nomeDentista) {
//        return consultaRepository.findConsultaByDentista(nomeDentista);
//    }

    @Override
    public Consulta atualizar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @Override
    public List<Consulta> findConsultaByNomeDentista(String nome) {
        return consultaRepository.findByDentistaNome(nome);
    }

    @Override
    public List<Consulta> findByDentistaMatricula(String matricula) {
        return consultaRepository.findByDentistaMatricula(matricula);
    }

    @Override
    public List<Consulta> findConsultaByNomePaciente(String nome) {
        return consultaRepository.findByPacienteNome(nome);
    }
}
