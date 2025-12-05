import java.util.ArrayList;
import java.util.List;

import exception.ItemIndisponivelException;
import exception.ItemNaoEncontradoException;
import exception.LimiteEmprestimoException;
import exception.UsuarioNaoEncontradoException;
import model.Emprestimo;
import model.ItemBiblioteca;
import model.Notificavel;
import model.Usuario;

public class BibliotecaService {
    private List<Usuario> usuarios;
    private List<ItemBiblioteca> itens;
    private List<Emprestimo> emprestimos;

    public BibliotecaService() {
        this.usuarios = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    // ========== MÉTODOS COM SOBRECARGA ==========
    
    // Sobrecarga 1: listar todos os itens
    public void listarItens() {
        listarItens(false);
    }

    // Sobrecarga 2: listar apenas disponíveis
    public void listarItens(boolean apenasDisponiveis) {
        System.out.println("\n=== CATÁLOGO DE ITENS ===");
        for (ItemBiblioteca item : itens) {
            if (!apenasDisponiveis || item.isDisponivel()) {
                System.out.println("  " + item);
            }
        }
    }

    // Sobrecarga 3: buscar item por código
    public ItemBiblioteca buscarItem(String codigo) throws ItemNaoEncontradoException {
        for (ItemBiblioteca item : itens) {
            if (item.getCodigo().equals(codigo)) {
                return item;
            }
        }
        throw new ItemNaoEncontradoException("Item com código " + codigo + " não encontrado.");
    }

    // Sobrecarga 4: buscar itens por título (parcial)
    public List<ItemBiblioteca> buscarItem(String titulo, boolean buscarPorTitulo) {
        List<ItemBiblioteca> resultado = new ArrayList<>();
        for (ItemBiblioteca item : itens) {
            if (item.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    // ========== GERENCIAMENTO DE USUÁRIOS ==========

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario buscarUsuario(long matricula) throws UsuarioNaoEncontradoException {
        for (Usuario u : usuarios) {
            if (u.getMatricula() == matricula) {
                return u;
            }
        }
        throw new UsuarioNaoEncontradoException("Usuário com matrícula " + matricula + " não encontrado.");
    }

    public void listarUsuarios() {
        System.out.println("\n=== USUÁRIOS CADASTRADOS ===");
        for (Usuario u : usuarios) {
            System.out.println("  " + u + " - Empréstimos ativos: " + u.totalEmprestimosAtivos());
        }
    }

    // ========== GERENCIAMENTO DE ITENS ==========

    public void cadastrarItem(ItemBiblioteca item) {
        itens.add(item);
    }

    // ========== EMPRÉSTIMOS ==========

    public Emprestimo realizarEmprestimo(long matricula, String codigoItem, Notificavel notificacao) 
            throws UsuarioNaoEncontradoException, ItemNaoEncontradoException, 
                   ItemIndisponivelException, LimiteEmprestimoException {
        
        Usuario usuario = buscarUsuario(matricula);
        ItemBiblioteca item = buscarItem(codigoItem);

        // Validações
        if (!item.isDisponivel()) {
            throw new ItemIndisponivelException("O item '" + item.getTitulo() + "' não está disponível.");
        }

        if (usuario.totalEmprestimosAtivos() >= usuario.getLimiteEmprestimos()) { throw new LimiteEmprestimoException("Usuário atingiu o limite de " + 
        usuario.getLimiteEmprestimos() + " empréstimos simultâneos.");
        }

        // Criar empréstimo
        Emprestimo emprestimo = new Emprestimo(usuario, item);
        emprestimos.add(emprestimo);

        // Notificar
        if (notificacao != null) {
            notificacao.notificar("Empréstimo realizado: " + item.getTitulo() + " para " + usuario.getNome());
        }

        return emprestimo;
    }

    public void listarEmprestimosUsuario(long matricula) throws UsuarioNaoEncontradoException {
        Usuario usuario = buscarUsuario(matricula);
        System.out.println("\n=== EMPRÉSTIMOS DE " + usuario.getNome() + " ===");
        
        if (usuario.getEmprestimos().isEmpty()) {
            System.out.println("  Nenhum empréstimo registrado.");
        } else {
            for (Emprestimo e : usuario.getEmprestimos()) {
                System.out.println("  " + e);
            }
            System.out.println("\nTotal de multas: R$ " + String.format("%.2f", usuario.totalMultas()));
        }
    }

    public void listarEmprestimosAtivos() {
        System.out.println("\n=== EMPRÉSTIMOS ATIVOS ===");
        boolean temAtivos = false;
        for (Emprestimo e : emprestimos) {
            if (!e.isFinalizado()) {
                System.out.println("  " + e.getUsuario().getNome() + " - " + e.getItem().getTitulo());
                temAtivos = true;
            }
        }
        if (!temAtivos) {
            System.out.println("  Nenhum empréstimo ativo no momento.");
        }
    }
     public Emprestimo buscarEmprestimoAtivo(long matricula, String codigoItem) 
            throws UsuarioNaoEncontradoException, ItemNaoEncontradoException {
        Usuario usuario = buscarUsuario(matricula);
        ItemBiblioteca item = buscarItem(codigoItem);
        
        for (Emprestimo e : emprestimos) {
            if (e.getUsuario().getMatricula() == matricula && 
                e.getItem().getCodigo().equals(codigoItem) && 
                !e.isFinalizado()) {
                return e;
            }
        }
        return null;
    }
}