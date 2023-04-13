package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private ObjectMapper mapper = new ObjectMapper();
    private IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse salvar(UsuarioRequest request) throws InvalidDataException {
        if (validarUsuario(request)) {
            Usuario usuario = mapper.convertValue(request, Usuario.class);
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            return mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
        } else {
            throw new InvalidDataException("Não foi possível cadastrar o novo Usuário!");
        }
    }

    public List<UsuarioResponse> buscarTodos() throws ResourceNotFoundException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> response = new ArrayList<>();
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                response.add(mapper.convertValue(usuario, UsuarioResponse.class));
            }
            return response;
        } else {
            throw new ResourceNotFoundException("Não há Usuários cadastrados");
        }
    }

    public UsuarioResponse buscar(Integer id) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (Objects.nonNull(usuario)) {
            return toUsuarioResponse(usuario);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Id informado!");
        }
    }

    @Override
    public List<UsuarioResponse> buscarPorNome(String nome) throws ResourceNotFoundException {
        List<Usuario> usuarios = usuarioRepository.findUsuarioByNomeContainingIgnoreCase(nome);
        List<UsuarioResponse> usuarioResponseList = new ArrayList<>();
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                usuarioResponseList.add(toUsuarioResponse(usuario));
            }
            return usuarioResponseList;
        } else {
            throw new ResourceNotFoundException("Não há Usuários cadastrados para o Nome informado!");
        }
    }

    @Override
    public UsuarioResponse buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findUsuarioByEmailContainingIgnoreCase(email);
        UsuarioResponse usuarioResponse = toUsuarioResponse(usuario);
        return usuarioResponse;
    }

    public void excluir(Integer id) throws ResourceNotFoundException {
        if (usuarioRepository.findById(id).isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Id informado!");
        }
    }

    public UsuarioResponse atualizar(Usuario usuario) throws ResourceNotFoundException {
        if (usuario.getId() != null
                && usuarioRepository.findById(usuario.getId()) != null) {
            UsuarioResponse usuarioResponse = mapper.convertValue(usuarioRepository.save(usuario), UsuarioResponse.class);
            return usuarioResponse;
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Email informado!");
        }
    }

    private UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .nivelAcesso(usuario.isNivelAcesso())
                .build();
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
