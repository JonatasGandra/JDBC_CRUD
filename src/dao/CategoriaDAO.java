package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;

public class CategoriaDAO {
    private Connection conn;

    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categorias (nome, ativo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setBoolean(2, categoria.isAtivo());
            stmt.executeUpdate();
        }
    }

    // READ (buscar todos)
    public List<Categoria> listar() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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
    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categorias SET nome=?, ativo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setBoolean(2, categoria.isAtivo());
            stmt.setInt(3, categoria.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM Categorias WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
