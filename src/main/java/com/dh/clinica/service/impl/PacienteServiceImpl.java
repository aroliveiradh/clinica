package com.dh.clinica.service.impl;


import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private IPacienteRepository pacienteRepository;

    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente salvar(Paciente paciente) {
        //paciente.setDataCadastro(new Date());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscar(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Optional<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findPacienteByNomeContainingIgnoreCase(nome);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    public void excluir(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente atualizar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

}

