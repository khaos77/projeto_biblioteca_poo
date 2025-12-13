package model;

public class Professor extends Usuario {
    private final String departamento;

    public Professor(String nome, long matricula, String departamento) {
        super(nome, matricula, 5); 
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public double aplicarDescontoMulta(double multaOriginal) {
        return multaOriginal * 0.5; 
    }

    @Override
    public String toString() {
        return "PROFESSOR: " + super.toString() + " - Depto: " + departamento;
    }
}