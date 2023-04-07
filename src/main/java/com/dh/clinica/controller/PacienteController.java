package com.dh.clinica.controller;


import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
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
    private PacienteServiceImpl pacienteServiceImpl;

    @PostMapping
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody PacienteRequest pacienteRequest) {
        log.info("Inciando método cadastrar...");
        ResponseEntity<PacienteResponse> response;
        if (validaEnderecoDTO(pacienteRequest)) {
            log.info("Cadastrando paciente: " + pacienteRequest.toString());
            response = ResponseEntity.ok(pacienteServiceImpl.salvar(pacienteRequest));
        } else {
            log.info("Não foi possível cadastrar o paciente, pois o endereço do paciente não foi informado ou foi informado sem todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        Paciente paciente = pacienteServiceImpl.buscar(id).orElse(null);
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
        if (paciente.getId() != null && pacienteServiceImpl.buscar(paciente.getId()).isPresent()) {
            log.info("Atualizando paciente com o id: " + paciente.getId());
            response = ResponseEntity.ok(pacienteServiceImpl.atualizar(paciente));
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
        if (pacienteServiceImpl.buscar(id).isPresent()) {
            pacienteServiceImpl.excluir(id);
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
        List<Paciente> pacientes = pacienteServiceImpl.buscarTodos();
        if (!pacientes.isEmpty()) {
            log.info("Lista de Pacientes encontrada: " + pacientes.toString());
            return ResponseEntity.ok(pacientes);
        } else {
            log.info("Não há Pacientes cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Paciente> buscarPorNome (@PathVariable String nome) {

        log.info("Inciando método buscarPorNome...");
        Paciente paciente = pacienteServiceImpl.buscarPorNome(nome).orElse(null);
        if (Objects.nonNull(paciente)) {
            log.info("Paciente encontrado: " + paciente.toString());
            return ResponseEntity.ok(paciente);
        } else {
            log.info("Nenhum Paciente foi encontrado com o nome: " + nome);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private static boolean validaEnderecoDTO(PacienteRequest pacienteRequest) {
        return Objects.nonNull(pacienteRequest.getEndereco()) &&
                Objects.nonNull(pacienteRequest.getEndereco().getCidade()) &&
                !pacienteRequest.getEndereco().getCidade().isEmpty() &&
                !pacienteRequest.getEndereco().getCidade().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getRua()) &&
                !pacienteRequest.getEndereco().getRua().isEmpty() &&
                !pacienteRequest.getEndereco().getRua().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getNumero()) &&
                !pacienteRequest.getEndereco().getNumero().isEmpty() &&
                !pacienteRequest.getEndereco().getNumero().isBlank() &&
                Objects.nonNull(pacienteRequest.getEndereco().getEstado()) &&
                !pacienteRequest.getEndereco().getEstado().isEmpty() &&
                !pacienteRequest.getEndereco().getEstado().isBlank();
    }
}
