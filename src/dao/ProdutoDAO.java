package dao;

import model.Produto;
import model.Categoria;
import java.sql.*;
import java.util.*;

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Produto p) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, categoria_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setInt(3, p.getCategoria().getId());
            stmt.executeUpdate();
        }
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.preco, c.id, c.nome, c.ativo " +
                     "FROM produto p INNER JOIN categoria c ON p.categoria_id = c.id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt(4), rs.getString(5), rs.getBoolean(6));
                Produto prod = new Produto(rs.getInt(1), rs.getString(2), rs.getDouble(3), cat);
                lista.add(prod);
            }
        }
        return lista;
    }

    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produto SET nome=?, preco=?, categoria_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setInt(3, p.getCategoria().getId());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
