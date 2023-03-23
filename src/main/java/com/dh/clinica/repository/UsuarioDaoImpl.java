package com.dh.clinica.repository;

import com.dh.clinica.config.ConfiguracaoJDBC;
import com.dh.clinica.model.Usuario;
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
public class UsuarioDaoImpl implements IDao<Usuario> {

    private ConfiguracaoJDBC configuracaoJDBC;
    final static Logger log = Logger.getLogger(DentistaDaoImpl.class);

    public UsuarioDaoImpl() {
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("INSERT INTO USUARIO (NOME ,EMAIL, SENHA, NIVEL_ACESSO) " +
                        "VALUES ('%s','%s','%s','%s')", usuario.getNome(),
                usuario.getEmail(), usuario.getSenha(), usuario.isNivelAcesso());
        try {
            stmt = conexao.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                usuario.setId(keys.getInt(1));
            stmt.close();
            conexao.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> buscarTodos() {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = "SELECT * FROM USUARIO";
        List<Usuario> usuarios = new ArrayList<>();
        try {
            stmt = conexao.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                usuarios.add(criarObjetoUsuario(result));
            }
            stmt.close();
            conexao.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public void excluir(Integer id) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("DELETE FROM USUARIO WHERE ID = %s", id);
        try {
            stmt = conexao.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conexao.close();
        } catch (SQLException throwables) {

        }
    }

    @Override
    public Optional<Usuario> buscar(Integer id) {
        log.debug("Buscando usuario com id : " + id);
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("SELECT ID, NOME ,EMAIL, SENHA, NIVEL_ACESSO FROM USUARIO WHERE ID = '%s'", id);
        Usuario usuario = null;
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                usuario = criarObjetoUsuario(result);
            }
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario != null ? Optional.of(usuario) : Optional.empty();
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        log.debug("Atualizando um usuario: " + usuario.toString());
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        String query = String.format("UPDATE USUARIO SET NOME = '%s', EMAIL = '%s', SENHA = '%s', NIVEL_ACESSO = '%s' WHERE ID = '%s'",
                usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.isNivelAcesso(), usuario.getId());
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario;
    }

    private Usuario criarObjetoUsuario(ResultSet resultado) throws SQLException {
        Integer id = resultado.getInt("ID");
        String nome = resultado.getString("NOME");
        String email = resultado.getString("EMAIL");
        String senha = resultado.getString("SENHA");
        boolean nivelAcesso = resultado.getBoolean("NIVEL_ACESSO");
        Usuario usuario = new Usuario(id, nome, email, senha, nivelAcesso);
        return usuario;
    }
}
