package com.dh.clinica.service.impl;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;
import com.dh.clinica.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscar(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findUsuarioByNomeContainingIgnoreCase(nome);
    }

    public void excluir(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
