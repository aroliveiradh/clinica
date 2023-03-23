package com.dh.clinica.service;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.UsuarioDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDaoImpl usuarioDao;

    public Usuario cadastrar(Usuario usuario) {
        return usuarioDao.salvar(usuario);
    }

    public List<Usuario> buscarTodos(){
        return usuarioDao.buscarTodos();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioDao.buscar(id);
    }

    public void excluir(Integer id) {
        usuarioDao.excluir(id);
    }

    public Usuario atualizar(Usuario usuario) {
        return usuarioDao.atualizar(usuario);
    }
}
