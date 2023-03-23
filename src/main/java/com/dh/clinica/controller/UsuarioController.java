package com.dh.clinica.controller;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.service.UsuarioService;
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

    final static Logger log = Logger.getLogger(PacienteController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        log.info("Inciando método buscarTodos...");
        ResponseEntity response = null;
        List<Usuario> usuarios = usuarioService.buscarTodos();
        if (!usuarios.isEmpty()) {
            log.info("Lista de Usuario encontrada: " + usuarios.toString());
            return ResponseEntity.ok(usuarios);
        } else {
            log.info("Não há Usuários cadastrados");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        log.info("Inciando método excluir...");
        ResponseEntity<String> response = null;
        if (usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Usuario excluído");
        } else {
            log.info("Não foi encontrado nenhum Usuario com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro registro foi encontrado para o id informado");
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        log.info("Inciando método buscarPorId...");
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        if (Objects.nonNull(usuario)) {
            log.info("Usuario encontrado para o id informado: " + usuario.toString());
            return ResponseEntity.ok(usuario);
        } else {
            log.info("Nenhum Usuario foi encontrado para o id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (validarUsuario(usuario)) {
            log.info("Cadastrando usuario: " + usuario.toString());
            response = ResponseEntity.ok(usuarioService.cadastrar(usuario));
        } else {
            log.info("Não foi possível cadastrar o usuario, pois o endereço do usuario não foi informado ou foi informado sem todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) throws Exception {
        log.info("Inciando método atualizar...");
        ResponseEntity response = null;
        if (usuario.getId() != null && usuarioService.buscarPorId(usuario.getId()).isPresent()) {
            log.info("Atualizando usuario com o id: " + usuario.getId());
            response = ResponseEntity.ok(usuarioService.atualizar(usuario));
        }
        else {
            log.info("Não foi encontrado nenhum usuario com o id: " + usuario.getId());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    private boolean validarUsuario(Usuario usuario) {
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
