package model;

public class Aluno extends Usuario {
    private final String curso;

    public Aluno(String nome, long matricula, String curso) {
        super(nome, matricula, 3); 
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public double aplicarDescontoMulta(double multaOriginal) {
        return multaOriginal; 
    }

    @Override
    public String toString() {
        return "ALUNO: " + super.toString() + " - Curso: " + curso;
    }
}