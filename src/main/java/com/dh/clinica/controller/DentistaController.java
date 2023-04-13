package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/dentistas")
public class DentistaController {

    private DentistaServiceImpl dentistaServiceImpl;

    @Autowired
    public DentistaController(DentistaServiceImpl dentistaServiceImpl) {
        this.dentistaServiceImpl = dentistaServiceImpl;
    }

    @PostMapping
    public ResponseEntity<DentistaResponse> cadastrar(@RequestBody DentistaRequest request) throws InvalidDataException, ResourceNotFoundException {
        log.info("Inciando método cadastrar...");
        return ResponseEntity.status(HttpStatus.OK).body(dentistaServiceImpl.salvar(request));
    }

    @GetMapping
    public ResponseEntity<List<DentistaResponse>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        return ResponseEntity.status(HttpStatus.OK).body(dentistaServiceImpl.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(dentistaServiceImpl.buscar(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<DentistaResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorNome...");
        return ResponseEntity.status(HttpStatus.OK).body(dentistaServiceImpl.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método excluir...");
        dentistaServiceImpl.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<DentistaResponse> atualizar(@RequestBody Dentista dentista) throws Exception {
        log.info("Inciando método atualizar...");
        return ResponseEntity.status(HttpStatus.OK).body(dentistaServiceImpl.atualizar(dentista));
    }
}
