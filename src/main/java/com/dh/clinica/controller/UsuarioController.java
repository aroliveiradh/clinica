package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> buscarTodos() throws ResourceNotFoundException {
        log.info("Inciando método buscarTodos...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.buscarTodos());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método excluir...");
        usuarioServiceImpl.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.buscar(id));

    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorNome...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) throws InvalidDataException {
        log.info("Inciando método cadastrar...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.salvar(request));
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody Usuario usuario) throws Exception {
        log.info("Inciando método atualizar...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.atualizar(usuario));
    }

}
