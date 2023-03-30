package com.dh.clinica.repository;

import com.dh.clinica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByPacienteNome(String nome);

    List<Consulta> findByDentistaNome(String nome);

    List<Consulta> findByDentistaMatricula(String matricula);

//    Consulta findByConsultaIdAndPacienteRg(int consultaId, String rg);
//
//    List<Consulta> findByDentistaMatriculaAndDhConsultaBetween(String matricula, LocalDateTime dataHoraDaConsultaIni, LocalDateTime dataHoraDaConsultaFim);

}
