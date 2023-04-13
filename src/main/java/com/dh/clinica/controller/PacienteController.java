package com.dh.clinica.controller;


import com.dh.clinica.controller.dto.PacienteRequestDTO;
import com.dh.clinica.controller.dto.PacienteResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<PacienteResponseDTO> cadastrar(@RequestBody PacienteRequestDTO pacienteRequestDTO) throws InvalidDataException {
        log.info("Inciando método cadastrar...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.salvar(pacienteRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscar(id));
    }

    @PutMapping
    public ResponseEntity<PacienteResponseDTO> atualizar(@RequestBody Paciente paciente) throws Exception {
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
    public ResponseEntity<List<PacienteResponseDTO>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscarTodos());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<PacienteResponseDTO> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorNome...");
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServiceImpl.buscarPorNome(nome));
    }
}
