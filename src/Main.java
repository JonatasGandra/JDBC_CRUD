import dao.CategoriaDAO;
import java.sql.*;
import model.Categoria;

public class Main {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/loja?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String pass = "Jsg1556!";

        try {
            // Força o carregamento do driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conexão fecha automAático
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                CategoriaDAO categoriaDAO = new CategoriaDAO(conn);

                // CREATE
                Categoria nova = new Categoria("Eletrônicos", true);
                categoriaDAO.inserir(nova);
                System.out.println("Categoria inserida!");

                // READ
                System.out.println("Listando categorias:");
                for (Categoria c : categoriaDAO.listar()) {
                    System.out.println(c);
                }

                // UPDATE (alterao primeiro registro)
                Categoria primeira = categoriaDAO.listar().get(0);
                primeira.setNome("Eletrônicos e Acessórios");
                categoriaDAO.atualizar(primeira);
                System.out.println("Categoria atualizada!");

                // DELETE (deleta pelo id)
                categoriaDAO.excluir(primeira.getId());
                System.out.println("Categoria excluída!");

            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro de SQL:");
            e.printStackTrace();
        }
    }
}
