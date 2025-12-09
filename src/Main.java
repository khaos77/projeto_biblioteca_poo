import model.*;
import service.BibliotecaService;
import exception.*;

import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BibliotecaService biblioteca = new BibliotecaService();
        
        inicializarDados(biblioteca);
        
        boolean executando = true;
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE BIBLIOTECA - IFPB         ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        while (executando) {
            try {
                exibirMenu();
                String linha = scanner.nextLine().trim();
                if (linha.isEmpty()){
                    continue;
                }
                
                int opcao = Integer.parseInt(linha);
                
                switch (opcao) {
                    case 1:
                        realizarEmprestimo(scanner, biblioteca);
                        break;
                    case 2:
                        biblioteca.listarItens(true);
                        break;
                    case 3:
                        biblioteca.listarItens(false);
                        break;
                    case 4:
                        consultarEmprestimos(scanner, biblioteca);
                        break;
                    case 5:
                        biblioteca.listarEmprestimosAtivos();
                        break;
                    case 6:
                        biblioteca.listarUsuarios();
                        break;
                    case 7:
                        cadastrarNovoItem(scanner, biblioteca);
                        break;
                    case 8:
                        cadastrarNovoUsuario(scanner, biblioteca);
                        break;
                    case 9:
                        devolverItem(scanner, biblioteca);
                        break;
                    case 0:
                        executando = false;
                        System.out.println("\n✓ Sistema encerrado. Até logo!");
                        break;
                    default:
                        System.out.println("\n✗ Opção inválida. Tente novamente.");
                }
                
                if (executando && opcao != 0) {
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\n✗ Digite um número válido.");
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            } catch (ItemIndisponivelException | LimiteEmprestimoException | 
                     UsuarioNaoEncontradoException | ItemNaoEncontradoException | EmprestimoNaoEncontradoException e) {
                System.out.println("\n✗ " + e.getMessage());
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\n✗ Erro inesperado: " + e.getMessage());
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("\n======================================================");
        System.out.println("                    MENU PRINCIPAL");
        System.out.println("========================================================");
        System.out.println("1 - Realizar empréstimo");
        System.out.println("2 - Listar itens disponíveis");
        System.out.println("3 - Listar todos os itens");
        System.out.println("4 - Consultar empréstimos de um usuário");
        System.out.println("5 - Listar empréstimos ativos");
        System.out.println("6 - Listar usuários");
        System.out.println("7 - Cadastrar novo item");
        System.out.println("8 - Cadastrar novo usuário");
        System.out.println("9 - Devolver item");
        System.out.println("0 - Sair");
        System.out.println("========================================================");
        System.out.print("Opção: ");
    }
    
    private static void realizarEmprestimo(Scanner scanner, BibliotecaService biblioteca) 
             throws ItemIndisponivelException, LimiteEmprestimoException,
                   UsuarioNaoEncontradoException, ItemNaoEncontradoException {
        
        System.out.println("\n--- REALIZAR EMPRÉSTIMO ---");
        
        System.out.print("Matrícula do usuário: ");
        long matricula = Long.parseLong(scanner.nextLine());
        
        System.out.print("Código do item: ");
        String codigo = scanner.nextLine();
        
        System.out.print("Tipo de notificação (1=Email, 2=SMS, 0=Nenhuma): ");
        int tipoNotif = Integer.parseInt(scanner.nextLine());
        
        Notificavel notificacao = null;
        if (tipoNotif == 1) {
            notificacao = new NotificacaoEmail("biblioteca@ifpb.edu.br");
        } else if (tipoNotif == 2) {
            notificacao = new NotificacaoSMS("(83) 98888-7777");
        }
        
        Emprestimo emp = biblioteca.realizarEmprestimo(matricula, codigo, notificacao);
        
        System.out.println("\n✓ Empréstimo realizado com sucesso!");
        System.out.println("  Item: " + emp.getItem().getTitulo());
        System.out.println("  Usuário: " + emp.getUsuario().getNome());
        System.out.println("  Prazo de devolução: " + emp.getItem().getDiasEmprestimo() + " dias");
    }
    
    private static void consultarEmprestimos(Scanner scanner, BibliotecaService biblioteca) 
            throws UsuarioNaoEncontradoException {
        
        System.out.println("\n--- CONSULTAR EMPRÉSTIMOS ---");
        System.out.print("Matrícula do usuário: ");
        long matricula = Long.parseLong(scanner.nextLine());
        
        biblioteca.listarEmprestimosUsuario(matricula);
    }
    
    private static void devolverItem(Scanner scanner, BibliotecaService biblioteca) 
            throws UsuarioNaoEncontradoException, ItemNaoEncontradoException,
                   EmprestimoNaoEncontradoException {
        
        System.out.println("\n--- DEVOLVER ITEM ---");
        
        System.out.print("Matrícula do usuário: ");
        long matricula = Long.parseLong(scanner.nextLine());
        
        System.out.print("Código do item: ");
        String codigo = scanner.nextLine();

        System.out.print("Dias de atraso (0 se no prazo): ");
        int diasAtraso = Integer.parseInt(scanner.nextLine());
        
        Emprestimo emp = biblioteca.devolverItem(matricula, codigo, diasAtraso);
        
        System.out.println("\nItem devolvido com sucesso!");
        if(emp.getMulta() > 0){
            System.out.println("  Multa aplicada: R$ " + String.format("%.2f", emp.getMulta()));
        }else{
            System.out.println("  Devolução no prazo - sem multa.");
        }
    }
        
    
    private static void cadastrarNovoItem(Scanner scanner, BibliotecaService biblioteca) {
        System.out.println("\n--- CADASTRAR NOVO ITEM ---");
        System.out.println("Tipo: 1=Livro, 2=Revista, 3=Tese");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        
        ItemBiblioteca item = null;
        
        if (tipo == 1) {
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("ISBN: ");
            long isbn = Long.parseLong(scanner.nextLine());
            item = new Livro(titulo, autor, codigo, isbn);
            
        } else if (tipo == 2) {
            System.out.print("Edição: ");
            int edicao = Integer.parseInt(scanner.nextLine());
            System.out.print("Mês/Ano: ");
            String mesAno = scanner.nextLine();
            item = new Revista(titulo, codigo, edicao, mesAno);
            
        } else if (tipo == 3) {
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Orientador: ");
            String orientador = scanner.nextLine();
            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            item = new Tese(titulo, codigo, autor, orientador, ano);
        }
        
        if (item != null) {
            biblioteca.cadastrarItem(item);
            System.out.println("\n✓ Item cadastrado com sucesso!");
        }
    }
    
    private static void cadastrarNovoUsuario(Scanner scanner, BibliotecaService biblioteca) {
        System.out.println("\n--- CADASTRAR NOVO USUÁRIO ---");
        System.out.println("Tipo: 1=Aluno, 2=Professor, 3=Funcionário");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Matrícula: ");
        long matricula = Long.parseLong(scanner.nextLine());
        
        Usuario usuario = null;
        
        if (tipo == 1) {
            System.out.print("Curso: ");
            String curso = scanner.nextLine();
            usuario = new Aluno(nome, matricula, curso);
            
        } else if (tipo == 2) {
            System.out.print("Departamento: ");
            String depto = scanner.nextLine();
            usuario = new Professor(nome, matricula, depto);
            
        } else if (tipo == 3) {
            System.out.print("Setor: ");
            String setor = scanner.nextLine();
            usuario = new Funcionario(nome, matricula, setor);
        }
        
        if (usuario != null) {
            biblioteca.cadastrarUsuario(usuario);
            System.out.println("\n✓ Usuário cadastrado com sucesso!");
        }
    }
    
    private static void inicializarDados(BibliotecaService biblioteca) {
        // Usuários de exemplo
        biblioteca.cadastrarUsuario(new Aluno("Maria Silva", 20231001L, "Análise e Desenvolvimento"));
        biblioteca.cadastrarUsuario(new Aluno("João Santos", 20231002L, "Sistemas para Internet"));
        biblioteca.cadastrarUsuario(new Professor("Dr. Carlos Lima", 10001L, "Computação"));
        biblioteca.cadastrarUsuario(new Funcionario("Ana Costa", 30001L, "Biblioteca"));
        
        // Itens de exemplo
        biblioteca.cadastrarItem(new Livro("Clean Code", "Robert Martin", "LIV001", 9780132350884L));
        biblioteca.cadastrarItem(new Livro("Padrões de Projeto", "Gang of Four", "LIV002", 9788573076102L));
        biblioteca.cadastrarItem(new Livro("Java: Como Programar", "Deitel", "LIV003", 9788543004815L));
        biblioteca.cadastrarItem(new Revista("JavaWorld", "REV001", 45, "Novembro/2024"));
        biblioteca.cadastrarItem(new Revista("InfoQ Brasil", "REV002", 23, "Dezembro/2024"));
        biblioteca.cadastrarItem(new Tese("Inteligência Artificial em Saúde", "TES001", 
                                         "Pedro Oliveira", "Dra. Maria Santos", 2024));
        
        System.out.println("\n✓ Dados de exemplo carregados!");
        System.out.println("  - 4 usuários cadastrados");
        System.out.println("  - 6 itens no acervo");
    }
} 

