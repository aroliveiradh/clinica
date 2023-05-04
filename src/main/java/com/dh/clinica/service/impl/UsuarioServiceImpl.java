package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.UsuarioRequestDTO;
import com.dh.clinica.controller.dto.UsuarioResponseDTO;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public UsuarioResponseDTO salvar(UsuarioRequestDTO request) throws InvalidDataException {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.info("Erro ao cadastrar usuário: Já existe um usuario cadastrado com este e-mail");
            throw new InvalidDataException("Já existe um usuario cadastrado com este e-mail");
        }
        log.info("Iniciando processo para salvar um novo Usuario...");
        Usuario usuarioEntity = mapper.convertValue(request, Usuario.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
        log.info("Usuario salvo com successo: "+ request);;
        return mapper.convertValue(usuarioSalvo, UsuarioResponseDTO.class);

    }

    public List<UsuarioResponseDTO> buscarTodos() throws ResourceNotFoundException {
        log.info("Buscando todos Usuarios cadastrados...");
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                response.add(mapper.convertValue(usuario, UsuarioResponseDTO.class));
            }
            return response;
        } else {
            throw new ResourceNotFoundException("Não há Usuários cadastrados");
        }
    }

    public UsuarioResponseDTO buscar(Integer id) throws ResourceNotFoundException {
        log.info("Buscando Usuario cadastrado com id: " + id);
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (Objects.nonNull(usuario)) {
            return toUsuarioResponse(usuario);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Id informado!");
        }
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorNome(String nome) throws ResourceNotFoundException {
        log.info("Buscando Usuario cadastrado com nome: " + nome);
        List<Usuario> usuarios = usuarioRepository.findUsuarioByLoginContainingIgnoreCase(nome);
        List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();
        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                usuarioResponseDTOList.add(toUsuarioResponse(usuario));
            }
            return usuarioResponseDTOList;
        } else {
            throw new ResourceNotFoundException("Não há Usuários cadastrados para o Nome informado!");
        }
    }

    @Override
    public UsuarioResponseDTO buscarPorEmail(String email) throws ResourceNotFoundException {
        log.info("Buscando Usuario cadastrado com email: " + email);
        Usuario usuario = usuarioRepository.findUsuarioByEmailContainingIgnoreCase(email);
        if (Objects.nonNull(usuario)) {
            return toUsuarioResponse(usuario);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o e-mail informado!");
        }
    }

    public void excluir(Integer id) throws ResourceNotFoundException {
        if (usuarioRepository.findById(id).isPresent()) {
            log.info("Excluindo registro de Usuario com id: " + id);
            usuarioRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Id informado!");
        }
    }

    public UsuarioResponseDTO atualizar(Usuario usuario) throws ResourceNotFoundException {
        if (usuario.getId() != null
                && usuarioRepository.findById(usuario.getId()) != null) {
            UsuarioResponseDTO usuarioResponseDTO = mapper.convertValue(usuarioRepository.save(usuario), UsuarioResponseDTO.class);
            return usuarioResponseDTO;
        } else {
            throw new ResourceNotFoundException("Não há Usuário cadastrados para o Email informado!");
        }
    }

    private UsuarioResponseDTO toUsuarioResponse(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .login(usuario.getLogin())
                .email(usuario.getEmail())
                .nivelAcesso(usuario.getNivelAcesso().toString())
                .build();
    }
}
