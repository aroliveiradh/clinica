package com.dh.clinica.repository;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByNomeContainingIgnoreCase(String nome);
}
