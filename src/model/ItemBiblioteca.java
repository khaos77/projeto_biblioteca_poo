
public abstract class ItemBiblioteca {
    protected String titulo;
    protected String codigo;
    protected boolean disponivel = true;
    protected int diasEmprestimo;

    public ItemBiblioteca(String titulo, String codigo, int diasEmprestimo) {
        this.titulo = titulo;
        this.codigo = codigo;
        this.diasEmprestimo = diasEmprestimo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getDiasEmprestimo() {
        return diasEmprestimo;
    }

    // Método abstrato - cada tipo de item calcula multa diferente
    public abstract double calcularMultaPorDia();

    @Override
    public String toString() {
        String status = disponivel ? "Disponível" : "Indisponível";
        return titulo + " [" + codigo + "] - " + status;
    }
}