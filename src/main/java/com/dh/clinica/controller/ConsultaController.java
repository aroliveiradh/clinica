package com.dh.clinica.controller;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.ConsultaService;
import com.dh.clinica.service.DentistaService;
import com.dh.clinica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    final static Logger log = Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DentistaService dentistaService;

    @Autowired
    private ConsultaService consultaService;


    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        log.info("Inciando método cadastrar...");
        ResponseEntity<Consulta> response;
        if(pacienteService.buscarPorId(consulta.getPaciente().getId()).isPresent()
        && dentistaService.buscarPorId(consulta.getDentista().getId()).isPresent()
        ) {
            log.info("Cadastrando Consulta: " + consulta.toString());
            response = ResponseEntity.ok(consultaService.cadastrar(consulta));
        } else {
            log.info("Não foi possível cadastrar a Consulta, pois é preciso associar um Dentista e um Paciente a consulta.");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        List<Consulta> consultas = consultaService.buscarTodos();
        if (!consultas.isEmpty()) {
            log.info("Lista de Consulta encontrada: " + consultas.toString());
            return ResponseEntity.ok(consultas);
        } else {
            log.info("Não há Consulta cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta) {
        log.info("Inciando método atualizar...");
        ResponseEntity<Consulta> response;
        if (consulta.getId() != null && consultaService.bucarPorId(consulta.getId()).isPresent()) {
            log.info("Atualizando consulta com o id: " + consulta.getId());
            response = ResponseEntity.ok(consultaService.atualizar(consulta));
        }
        else {
            log.info("Não foi encontrado nenhuma consulta com o id: " + consulta.getId());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        log.info("Inciando método excluir...");
        ResponseEntity<String> response;
        if (consultaService.bucarPorId(id).isPresent()) {
            consultaService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Consulta apagada com sucesso!");
        } else {
            log.info("Não foi encontrado nenhuma Consulta com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        Consulta consulta = consultaService.bucarPorId(id).orElse(null);
        if (Objects.nonNull(consulta)) {
            log.info("Consulta encontrada para o id informado: " + consulta.toString());
            return ResponseEntity.ok(consulta);
        } else {
            log.info("Nenhuma Consulta foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
