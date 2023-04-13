package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.UsuarioLoginDTO;
import com.dh.clinica.controller.dto.UsuarioRequestDTO;
import com.dh.clinica.controller.dto.UsuarioResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.security.TokenDTO;
import com.dh.clinica.security.TokenService;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioServiceImpl usuarioServiceImpl;
    private TokenService tokenService;
    private AuthenticationManager manager;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl, TokenService tokenService, AuthenticationManager manager) {
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.tokenService = tokenService;
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity logar(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getLogin(), usuarioLoginDTO.getSenha());
        Authentication authenticate = manager.authenticate(token);
        String tokenJWT = tokenService.gerarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody UsuarioRequestDTO usuarioRequestDTO) throws InvalidDataException {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServiceImpl.salvar(usuarioRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() throws ResourceNotFoundException {
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
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorId...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.buscar(id));

    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(@PathVariable String nome) throws ResourceNotFoundException {
        log.info("Inciando método buscarPorNome...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.buscarPorNome(nome));
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> atualizar(@RequestBody Usuario usuario) throws Exception {
        log.info("Inciando método atualizar...");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServiceImpl.atualizar(usuario));
    }

}
