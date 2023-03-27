package com.dh.clinica.repository;

import com.dh.clinica.config.ConfiguracaoJDBC;
import com.dh.clinica.model.Dentista;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DentistaDaoImpl implements IDao<Dentista> {

    final static Logger log = Logger.getLogger(DentistaDaoImpl.class);
    private ConfiguracaoJDBC configuracaoJDBC;

    public DentistaDaoImpl() {
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    @Override
    public Dentista salvar(Dentista dentista) {
        log.debug("Registrando novo dentista: " + dentista.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("INSERT INTO DENTISTA (NOME, SOBRENOME, MATRICULA) VALUES ('%s', '%s', '%s')",
                dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                dentista.setId(keys.getInt(1));
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dentista;
    }

    @Override
    public List<Dentista> buscarTodos() {
        log.debug("Buscando todos os dentistas");
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT *  FROM DENTISTA";
        List<Dentista> dentistas = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                dentistas.add(criaDentista(result));
            }
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dentistas;
    }

    @Override
    public void excluir(Integer id) {
        log.debug("Excluinudo dentista com id : " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM DENTISTA WHERE ID = %s", id);
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Dentista> buscar(Integer id) {
        log.debug("Buscando dentista com id : " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, NOME, SOBRENOME, MATRICULA FROM DENTISTA WHERE ID = '%s'", id);
        Dentista dentista = null;
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                dentista = criaDentista(result);
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dentista != null ? Optional.of(dentista) : Optional.empty();
    }

    @Override
    public Dentista atualizar (Dentista dentista) {
        log.debug("Atualizando um paciente: " + dentista.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("UPDATE DENTISTA SET NOME = '%s', SOBRENOME = '%s', MATRICULA = '%s' WHERE ID = '%s'",
                dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula(), dentista.getId());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dentista;
    }

    private Dentista criaDentista(ResultSet resultSet) throws SQLException {
        return new Dentista(resultSet.getInt("ID"),
                resultSet.getString("NOME"),
                resultSet.getString("SOBRENOME"),
                resultSet.getString("MATRICULA"));
    }
}
