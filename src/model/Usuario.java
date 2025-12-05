import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    protected String nome;
    protected long matricula;
    protected List<Emprestimo> emprestimos;
    protected int limiteEmprestimos;

    public Usuario(String nome, long matricula, int limiteEmprestimos) {
        this.nome = nome;
        this.matricula = matricula;
        this.limiteEmprestimos = limiteEmprestimos;
        this.emprestimos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public long getMatricula() {
        return matricula;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarEmprestimo(Emprestimo e) {
        emprestimos.add(e);
    }

    public int totalEmprestimosAtivos() {
        int count = 0;
        for (Emprestimo e : emprestimos) {
            if (!e.isFinalizado()) {
                count++;
            }
        }
        return count;
    }

    public double totalMultas() {
        double soma = 0;
        for (Emprestimo e : emprestimos) {
            soma += e.getMulta();
        }
        return soma;
    }

    // Método abstrato - cada tipo de usuário tem desconto diferente
    public abstract double aplicarDescontoMulta(double multaOriginal);

    @Override
    public String toString() {
        return nome + " [Mat: " + matricula + "]";
    }
}
