package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private ObjectMapper mapper = new ObjectMapper();
    private IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse salvar(UsuarioRequest request) {
        Usuario usuario = mapper.convertValue(request, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        UsuarioResponse usuarioResponse = mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
        return usuarioResponse;
    }

    public List<UsuarioResponse> buscarTodos(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> response = new ArrayList<>();

        for(Usuario usuario : usuarios) {
            response.add(mapper.convertValue(usuario, UsuarioResponse.class));
        }
        return response;
    }

    public Optional<UsuarioResponse> buscar(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        UsuarioResponse usuarioResponse = toUsuarioResponse(usuario);
        return Optional.ofNullable(usuarioResponse);
    }

    @Override
    public Optional<UsuarioResponse> buscarPorNome(String nome) {
        Optional<Usuario> usuario = usuarioRepository.findUsuarioByNomeContainingIgnoreCase(nome);
        UsuarioResponse usuarioResponse = toUsuarioResponse(usuario);
        return Optional.ofNullable(usuarioResponse);
    }

    @Override
    public Optional<UsuarioResponse> buscarPorEmail(String email) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioByEmailContainingIgnoreCase(email));
        UsuarioResponse usuarioResponse = toUsuarioResponse(usuario);
        return Optional.ofNullable(usuarioResponse);
    }

    public void excluir(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponse atualizar(UsuarioRequest request) {
        Usuario usuarioMapper = mapper.convertValue(request, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuarioMapper);
        UsuarioResponse usuarioResponse = mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
        return usuarioResponse;
    }

    private UsuarioResponse toUsuarioResponse(Optional<Usuario> usuario) {
        return UsuarioResponse.builder()
                .nome(usuario.get().getNome())
                .email(usuario.get().getEmail())
                .nivelAcesso(usuario.get().isNivelAcesso())
                .build();
    }
}
