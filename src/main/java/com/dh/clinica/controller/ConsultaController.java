package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.ConsultaResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Consulta;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private PacienteServiceImpl pacienteServiceImpl;

    private DentistaServiceImpl dentistaServiceImpl;

    private ConsultaServiceImpl consultaServiceImpl;

    @Autowired
    public ConsultaController(PacienteServiceImpl pacienteServiceImpl, DentistaServiceImpl dentistaServiceImpl, ConsultaServiceImpl consultaServiceImpl) {
        this.pacienteServiceImpl = pacienteServiceImpl;
        this.dentistaServiceImpl = dentistaServiceImpl;
        this.consultaServiceImpl = consultaServiceImpl;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponse> cadastrar(@RequestBody Consulta consulta) throws ResourceNotFoundException, InvalidDataException {
        log.info("Inciando método cadastrar...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.salvar(consulta));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.buscarTodos());
    }

    /*
    TODO
    Método atualizar retornando erro 500
     */
    @PutMapping
    public ResponseEntity<ConsultaResponse> atualizar(@RequestBody Consulta consulta) throws ResourceNotFoundException {
        log.info("Inciando método atualizar...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.atualizar(consulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método excluir...");
        consultaServiceImpl.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.buscar(id));
    }

    @GetMapping("/paciente/{nome}")
    public ResponseEntity<List<ConsultaResponse>> findConsultaByNomePaciente(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método findConsultaByNomePaciente...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.findConsultaByNomePaciente(nome));
    }

    @GetMapping("/dentista/{nome}")
    public ResponseEntity<List<ConsultaResponse>> findConsultaByNomeDentista(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método findConsultaByNomeDentista...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.findConsultaByNomeDentista(nome));
    }

    @GetMapping("/dentista/matricula/{matricula}")
    public ResponseEntity<List<ConsultaResponse>> findConsultaByMatriculaDentista(@PathVariable String matricula) throws ResourceNotFoundException {
        log.info("Inciando método findConsultaByMatriculaDentista...");
        return ResponseEntity.status(HttpStatus.OK).body(consultaServiceImpl.findByDentistaMatricula(matricula));
    }
}
