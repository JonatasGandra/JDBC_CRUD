import dao.CategoriaDAO;
import dao.ProdutoDAO;
import java.sql.*;
import java.util.*;
import model.Categoria;
import model.Produto;

public class Main {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/loja?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String senha = "Jsg1556!";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, senha);
                 Scanner sc = new Scanner(System.in)) {

                CategoriaDAO categoriaDAO = new CategoriaDAO(conn);
                ProdutoDAO produtoDAO = new ProdutoDAO(conn);

                while (true) {
                    System.out.println("\n========= MENU =========");
                    System.out.println("1 - Inserir Categoria");
                    System.out.println("2 - Listar Categorias");
                    System.out.println("3 - Atualizar Categoria");
                    System.out.println("4 - Deletar Categoria");
                    System.out.println("5 - Inserir Produto");
                    System.out.println("6 - Listar Produtos");
                    System.out.println("7 - Atualizar Produto");
                    System.out.println("8 - Deletar Produto");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");

                    int opcao = sc.nextInt();
                    sc.nextLine(); // limpar buffer

                    switch (opcao) {
                        case 1: // CREATE Categoria
                            System.out.print("Nome da categoria: ");
                            String nomeCat = sc.nextLine();
                            System.out.print("Ativa? (true/false): ");
                            boolean ativo = sc.nextBoolean();
                            sc.nextLine();
                            Categoria nova = new Categoria(nomeCat, ativo);
                            categoriaDAO.inserir(nova);
                            System.out.println("Categoria inserida!");
                            break;

                        case 2: // READ Categoria
                            System.out.println("Listando categorias:");
                            for (Categoria c : categoriaDAO.listar()) {
                                System.out.println(c);
                            }
                            break;

                        case 3: // UPDATE Categoria
                            System.out.print("ID da categoria para atualizar: ");
                            int idCat = sc.nextInt(); sc.nextLine();
                            System.out.print("Novo nome: ");
                            nomeCat = sc.nextLine();
                            System.out.print("Ativa? (true/false): ");
                            ativo = sc.nextBoolean();
                            sc.nextLine();
                            Categoria catAtualizada = new Categoria(idCat, nomeCat, ativo);
                            categoriaDAO.atualizar(catAtualizada);
                            System.out.println("Categoria atualizada!");
                            break;

                        case 4: // DELETE Categoria
                            System.out.print("ID da categoria para deletar: ");
                            idCat = sc.nextInt();
                            categoriaDAO.excluir(idCat);
                            System.out.println("Categoria excluída!");
                            break;

                        case 5: // CREATE Produto
                            System.out.print("Nome do produto: ");
                            String nomeProd = sc.nextLine();
                            System.out.print("Preço do produto: ");
                            double preco = sc.nextDouble();
                            sc.nextLine();

                            System.out.println("Categorias disponíveis:");
                            for (Categoria c : categoriaDAO.listar()) {
                                System.out.println(c.getId() + " - " + c.getNome());
                            }

                            System.out.print("Escolha o ID da categoria: ");
                            int categoriaId = sc.nextInt();
                            sc.nextLine();

                            Categoria cat = new Categoria(categoriaId, "", true);
                            Produto novoProd = new Produto(nomeProd, preco, cat);

                            produtoDAO.inserir(novoProd);
                            System.out.println("Produto inserido com categoria!");
                            break;

                        case 6: // READ Produto
                            System.out.println("Listando produtos:");
                            for (Produto prod : produtoDAO.listar()) {
                                System.out.println(prod);
                            }
                            break;

                        case 7: // UPDATE Produto
                            System.out.print("ID do produto: ");
                            int idProd = sc.nextInt(); sc.nextLine();
                            System.out.print("Novo nome: ");
                            nomeProd = sc.nextLine();
                            System.out.print("Novo preço: ");
                            preco = sc.nextDouble();
                            sc.nextLine();

                            System.out.println("Categorias disponíveis:");
                            for (Categoria c : categoriaDAO.listar()) {
                                System.out.println(c.getId() + " - " + c.getNome());
                            }

                            System.out.print("Escolha o ID da nova categoria: ");
                            categoriaId = sc.nextInt();
                            sc.nextLine();

                            Categoria novaCat = new Categoria(categoriaId, "", true);
                            Produto prodAtualizado = new Produto(idProd, nomeProd, preco, novaCat);
                            produtoDAO.atualizar(prodAtualizado);
                            System.out.println("Produto atualizado!");
                            break;

                        case 8: // DELETE Produto
                            System.out.print("ID do produto a deletar: ");
                            idProd = sc.nextInt();
                            produtoDAO.deletar(idProd);
                            System.out.println("Produto deletado!");
                            break;

                        case 0: // SAIR
                            System.out.println("Saindo...");
                            return;

                        default:
                            System.out.println("Opção inválida!");
                    }
                }

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
