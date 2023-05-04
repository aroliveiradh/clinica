package com.dh.clinica.repository;

import com.dh.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findUsuarioByLoginContainingIgnoreCase(String login);
    Usuario findUsuarioByEmailContainingIgnoreCase(String email);
    UserDetails findByLogin(String login);
    boolean existsByEmail(String email);
}
