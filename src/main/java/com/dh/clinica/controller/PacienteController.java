package com.dh.clinica.controller;


import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteServiceImpl pacienteServiceImpl;

    @Autowired
    public PacienteController(PacienteServiceImpl pacienteServiceImpl) {
        this.pacienteServiceImpl = pacienteServiceImpl;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody PacienteRequest pacienteRequest) throws InvalidDataException {
        log.info("Inciando método cadastrar...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.salvar(pacienteRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscar(id));
    }

    @PutMapping
    public ResponseEntity<PacienteResponse> atualizar(@RequestBody Paciente paciente) throws Exception {
        log.info("Inciando método atualizar...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.atualizar(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método excluir...");
        pacienteServiceImpl.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscarTodos());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<PacienteResponse> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorNome...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscarPorNome(nome));
    }
}
