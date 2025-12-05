public class Aluno extends Usuario {
    private String curso;

    public Aluno(String nome, long matricula, String curso) {
        super(nome, matricula, 3); // Alunos podem pegar até 3 itens
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public double aplicarDescontoMulta(double multaOriginal) {
        return multaOriginal; // Alunos não têm desconto
    }

    @Override
    public String toString() {
        return "ALUNO: " + super.toString() + " - Curso: " + curso;
    }
}