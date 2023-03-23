package com.dh.clinica.repository;

import com.dh.clinica.model.Consulta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsultaDaoImpl implements IDao<Consulta> {

    private List<Consulta> consultas;

    public ConsultaDaoImpl() {
        this.consultas = new ArrayList<>();
    }

    @Override
    public Consulta salvar(Consulta consulta) {
        this.consultas.add(consulta);
        return consulta;
    }

    @Override
    public List<Consulta> buscarTodos() {
        return consultas;
    }

    @Override
    public void excluir(Integer id) {
        consultas.removeIf(consulta -> consulta.getId().equals(id));
    }

    @Override
    public Optional<Consulta> buscar(Integer id) {
        return consultas.stream().filter(consulta -> consulta.getId().equals(id)).findFirst();
    }

    @Override
    public Consulta atualizar(Consulta consulta) {
        excluir(consulta.getId());
        consultas.add(consulta);
        return consulta;
    }
}
