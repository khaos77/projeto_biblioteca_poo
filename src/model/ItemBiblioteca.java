
package model;

public abstract class ItemBiblioteca {
    private final String titulo;
    private final String codigo;
    private boolean disponivel = true;
    private final int diasEmprestimo;

    protected ItemBiblioteca(String titulo, String codigo, int diasEmprestimo) {
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

    
    public abstract double calcularMultaPorDia();

    @Override
    public String toString() {
        String status = disponivel ? "Disponível" : "Indisponível";
        return titulo + " [" + codigo + "] - " + status;
    }
}