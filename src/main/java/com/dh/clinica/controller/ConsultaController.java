package com.dh.clinica.controller;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
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
    private PacienteServiceImpl pacienteServiceImpl;

    @Autowired
    private DentistaServiceImpl dentistaServiceImpl;

    @Autowired
    private ConsultaServiceImpl consultaServiceImpl;


    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        log.info("Inciando método cadastrar...");
        ResponseEntity<Consulta> response;
        if(pacienteServiceImpl.buscar(consulta.getPaciente().getId()).isPresent()
        && dentistaServiceImpl.buscar(consulta.getDentista().getId()) != null) {
            log.info("Cadastrando Consulta: " + consulta.toString());
            response = ResponseEntity.ok(consultaServiceImpl.salvar(consulta));
        } else {
            log.info("Não foi possível cadastrar a Consulta, pois é preciso associar um Dentista e um Paciente a consulta.");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        List<Consulta> consultas = consultaServiceImpl.buscarTodos();
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
        if (consulta.getId() != null && consultaServiceImpl.buscar(consulta.getId()).isPresent()) {
            log.info("Atualizando consulta com o id: " + consulta.getId());
            response = ResponseEntity.ok(consultaServiceImpl.atualizar(consulta));
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
        if (consultaServiceImpl.buscar(id).isPresent()) {
            consultaServiceImpl.excluir(id);
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
        Consulta consulta = consultaServiceImpl.buscar(id).orElse(null);
        if (Objects.nonNull(consulta)) {
            log.info("Consulta encontrada para o id informado: " + consulta.toString());
            return ResponseEntity.ok(consulta);
        } else {
            log.info("Nenhuma Consulta foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/paciente/{nome}")
    public ResponseEntity<List<Consulta>> findConsultaByNomePaciente(@PathVariable String nome) {
        log.info("Inciando método findConsultaByNomePaciente...");
        List<Consulta> consultas = consultaServiceImpl.findConsultaByNomePaciente(nome);
        if (Objects.nonNull(consultas)) {
            log.info("Consultas encontrada para o paciente: " + consultas.toString());
            return ResponseEntity.ok(consultas);
        } else {
            log.info("Nenhuma Consulta foi encontrado para o paciente: " + nome);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/dentista/{nome}")
    public ResponseEntity<List<Consulta>> findConsultaByNomeDentista(@PathVariable String nome) {
        log.info("Inciando método findConsultaByNomeDentista...");
        List<Consulta> consultas = consultaServiceImpl.findConsultaByNomeDentista(nome);
        if (Objects.nonNull(consultas)) {
            log.info("Consultas encontrada para o Dentista: " + consultas.toString());
            return ResponseEntity.ok(consultas);
        } else {
            log.info("Nenhuma Consulta foi encontrado para o dentista: " + nome);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/dentista/matricula/{matricula}")
    public ResponseEntity<List<Consulta>> findConsultaByMatriculaDentista(@PathVariable String matricula) {
        log.info("Inciando método findConsultaByMatriculaDentista...");
        List<Consulta> consultas = consultaServiceImpl.findByDentistaMatricula(matricula);
        if (Objects.nonNull(consultas)) {
            log.info("Consultas encontrada para o Dentista: " + consultas.toString());
            return ResponseEntity.ok(consultas);
        } else {
            log.info("Nenhuma Consulta foi encontrado para o Dentista com matricula: " + matricula);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
