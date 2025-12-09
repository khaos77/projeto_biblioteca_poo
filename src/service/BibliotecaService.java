package service;
import exception.*;
import model.*;
import java.util.*;



public class BibliotecaService {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<ItemBiblioteca> itens = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

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
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        throw new ItemNaoEncontradoException("Item com código " + codigo + " não encontrado.");
    }

    // Sobrecarga 4: buscar itens por título (parcial)
    public List<ItemBiblioteca> buscarItem(String tituloParcial, boolean buscarPorTitulo) {
        List<ItemBiblioteca> resultado = new ArrayList<>();
        if (!buscarPorTitulo){
            return resultado;
        }
        for (ItemBiblioteca item : itens) {
            if (item.getTitulo().toLowerCase().contains(tituloParcial.toLowerCase())) {
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
        if (usuarios.isEmpty()){
            System.out.println(" Nennhum usuário cadastrado.");
            return;
        }
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

    public void listarEmprestimosUsuario(long matricula) 
            throws UsuarioNaoEncontradoException {
        Usuario usuario = buscarUsuario(matricula);
        System.out.println("\n=== EMPRÉSTIMOS DE " + usuario.getNome() + " ===");
        
        List<Emprestimo> lista = usuario.getEmprestimos();
        if (lista.isEmpty()){
            System.out.println("  Nenhum emprestimo registrado.");
        }else{
            for(Emprestimo e : lista){
                System.out.println(" " + e);
            }
            System.out.println("\nTotal de multas: R$ "
                + String.format("%.2f", usuario.totalMultas()));
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
            throws UsuarioNaoEncontradoException, ItemNaoEncontradoException, EmprestimoNaoEncontradoException {
        Usuario usuario = buscarUsuario(matricula);
        ItemBiblioteca item = buscarItem(codigoItem);
        
        for (Emprestimo e : emprestimos) {
            if(!e.isFinalizado() && e.getUsuario().getMatricula() == usuario.getMatricula()
                && e.getItem().getCodigo().equalsIgnoreCase(item.getCodigo())){
               return e;  
              } 
        }
              throw new EmprestimoNaoEncontradoException(
                "nenhum emprestimo ativo encontrado para o item " + codigoItem + " e ususario " + matricula + ".");
              }
        public Emprestimo devolverItem(long matricula, String codigoItem, int diasAtraso)
               throws UsuarioNaoEncontradoException, ItemNaoEncontradoException, EmprestimoNaoEncontradoException {

                Emprestimo emp= buscarEmprestimoAtivo(matricula, codigoItem);
                Date dataDevolucao = new Date(
                    emp.getDataPrevista().getTime() + (diasAtraso * 24L * 60 * 60 * 1000));
                emp.devolver(dataDevolucao);   
                return emp;
        
    }
}