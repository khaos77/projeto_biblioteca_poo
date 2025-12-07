package model;

public class Funcionario extends Usuario {
    private final String setor;

    public Funcionario(String nome, long matricula, String setor) {
        super(nome, matricula, 4); // Funcionários podem pegar até 4 itens
        this.setor = setor;
    }

    public String getSetor() {
        return setor;
    }

    @Override
    public double aplicarDescontoMulta(double multaOriginal) {
        return multaOriginal * 0.7; // 30% de desconto
    }

    @Override
    public String toString() {
        return "FUNCIONÁRIO: " + super.toString() + " - Setor: " + setor;
    }
}