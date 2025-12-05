public class Professor extends Usuario {
    private String departamento;

    public Professor(String nome, long matricula, String departamento) {
        super(nome, matricula, 5); // Professores podem pegar até 5 itens
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public double aplicarDescontoMulta(double multaOriginal) {
        return multaOriginal * 0.5; // 50% de desconto
    }

    @Override
    public String toString() {
        return "PROFESSOR: " + super.toString() + " - Depto: " + departamento;
    }
}