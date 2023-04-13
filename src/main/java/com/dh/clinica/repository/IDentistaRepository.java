package com.dh.clinica.repository;

import com.dh.clinica.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDentistaRepository extends JpaRepository<Dentista, Integer> {

    List<Dentista> findDentistaByNomeContainingIgnoreCase(String nome);
   Dentista findDentistaByMatriculaContainingIgnoreCase(String matricula);
}
