package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    final static Logger log = Logger.getLogger(UsuarioController.class);

    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        ResponseEntity response = null;
        List<UsuarioResponse> responses = usuarioServiceImpl.buscarTodos();
        if (!responses.isEmpty()) {
            log.info("Lista de Usuario encontrada: " + responses.toString());
            return ResponseEntity.ok(responses);
        } else {
            log.info("Não há Usuários cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        log.info("Inciando método excluir...");
        ResponseEntity<String> response = null;
        if (usuarioServiceImpl.buscar(id).isPresent()) {
            usuarioServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Usuario excluído");
        } else {
            log.info("Não foi encontrado nenhum Usuario com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro registro foi encontrado para o id informado");
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        UsuarioResponse usuarioResponse = usuarioServiceImpl.buscar(id).orElse(null);
        if (Objects.nonNull(usuarioResponse)) {
            log.info("Usuario encontrado para o id informado: " + usuarioResponse.toString());
            return ResponseEntity.ok(usuarioResponse);
        } else {
            log.info("Nenhum Usuario foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<UsuarioResponse> buscarPorNome(@PathVariable String nome) {
        log.info("Inciando método buscarPorNome...");
        UsuarioResponse usuarioResponse = usuarioServiceImpl.buscarPorNome(nome).orElse(null);
        if (Objects.nonNull(usuarioResponse)) {
            log.info("Usuario encontrado: " + usuarioResponse.toString());
            return ResponseEntity.ok(usuarioResponse);
        } else {
            log.info("Nenhum Usuario foi encontrado com o nome: " + nome);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) {
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (validarUsuario(request)) {
            log.info("Cadastrando usuario: " + request.toString());
            response = ResponseEntity.ok(usuarioServiceImpl.salvar(request));
        } else {
            log.info("Não foi possível cadastrar o usuario, pois o endereço do usuario não foi informado ou foi informado sem todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioRequest usuarioRequest) throws Exception {
        log.info("Inciando método atualizar...");
        ResponseEntity response = null;
        if (usuarioRequest.getEmail() != null && usuarioServiceImpl.buscarPorEmail(usuarioRequest.getEmail()).isPresent()) {
            log.info("Atualizando usuario: " + usuarioRequest.getNome());
            response = ResponseEntity.ok(usuarioServiceImpl.atualizar(usuarioRequest));
        } else {
            log.info("Usuario não encontrado: " + usuarioRequest.getNome());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    private boolean validarUsuario(UsuarioRequest usuario) {
        return Objects.nonNull(usuario) &&
                Objects.nonNull(usuario.getNome()) &&
                !usuario.getNome().isEmpty() &&
                !usuario.getNome().isBlank() &&
                Objects.nonNull(usuario.getEmail()) &&
                !usuario.getEmail().isEmpty() &&
                !usuario.getEmail().isBlank() &&
                Objects.nonNull(usuario.getSenha()) &&
                !usuario.getSenha().isEmpty() &&
                !usuario.getSenha().isBlank() &&
                Objects.nonNull(usuario.isNivelAcesso());
    }
}
