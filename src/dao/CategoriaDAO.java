package dao;

import model.Categoria;
import java.sql.*;
import java.util.*;

public class CategoriaDAO {
    private Connection conn;

    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void inserir(Categoria c) throws SQLException {
        String sql = "INSERT INTO categoria (nome, ativo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setBoolean(2, c.isAtivo());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Categoria> listar() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getBoolean("ativo")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    // UPDATE
    public void atualizar(Categoria c) throws SQLException {
        String sql = "UPDATE categoria SET nome=?, ativo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setBoolean(2, c.isAtivo());
            stmt.setInt(3, c.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
