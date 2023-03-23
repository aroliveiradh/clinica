package com.dh.clinica.controller;


import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    final static Logger log = Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;

    @PostMapping()
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        log.info("Inciando método cadastrar...");
        ResponseEntity<Paciente> response;
        if (validaEndereco(paciente)) {
            log.info("Cadastrando paciente: " + paciente.toString());
            response = ResponseEntity.ok(pacienteService.cadastrar(paciente));
        } else {
            log.info("Não foi possível cadastrar o paciente, pois o endereço do paciente não foi informado ou foi informado sem todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        Paciente paciente = pacienteService.buscarPorId(id).orElse(null);
        if (Objects.nonNull(paciente)) {
            log.info("Paciente encontrado para o id informado: " + paciente.toString());
            return ResponseEntity.ok(paciente);
        } else {
            log.info("Nenhum Paciente foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<Paciente> atualizar(@RequestBody Paciente paciente) throws Exception {
        log.info("Inciando método atualizar...");
        ResponseEntity response = null;
        if (paciente.getId() != null && pacienteService.buscarPorId(paciente.getId()).isPresent()) {
            log.info("Atualizando paciente com o id: " + paciente.getId());
            response = ResponseEntity.ok(pacienteService.atualizar(paciente));
        }
        else {
            log.info("Não foi encontrado nenhum paciente com o id: " + paciente.getId());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        log.info("Inciando método excluir...");
        ResponseEntity<String> response;
        if (pacienteService.buscarPorId(id).isPresent()) {
            pacienteService.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Paciente excluído");
        } else {
            log.info("Não foi encontrado nenhum paciente com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro registro foi encontrado para o id informado");
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        List<Paciente> pacientes = pacienteService.buscarTodos();
        if (!pacientes.isEmpty()) {
            log.info("Lista de Pacientes encontrada: " + pacientes.toString());
            return ResponseEntity.ok(pacientes);
        } else {
            log.info("Não há Pacientes cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    private static boolean validaEndereco(Paciente paciente) {
        return Objects.nonNull(paciente.getEndereco()) &&
                Objects.nonNull(paciente.getEndereco().getCidade()) &&
                !paciente.getEndereco().getCidade().isEmpty() &&
                !paciente.getEndereco().getCidade().isBlank() &&
                Objects.nonNull(paciente.getEndereco().getRua()) &&
                !paciente.getEndereco().getRua().isEmpty() &&
                !paciente.getEndereco().getRua().isBlank() &&
                Objects.nonNull(paciente.getEndereco().getNumero()) &&
                !paciente.getEndereco().getNumero().isEmpty() &&
                !paciente.getEndereco().getNumero().isBlank() &&
                Objects.nonNull(paciente.getEndereco().getEstado()) &&
                !paciente.getEndereco().getEstado().isEmpty() &&
                !paciente.getEndereco().getEstado().isBlank();
    }
}
