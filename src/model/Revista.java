package model;

public class Revista extends ItemBiblioteca {
    private final int edicao;
    private final String mesAno;

    public Revista(String titulo, String codigo, int edicao, String mesAno) {
        super(titulo, codigo, 7); // 7 dias para revistas
        this.edicao = edicao;
        this.mesAno = mesAno;
    }

    public int getEdicao() {
        return edicao;
    }

    public String getMesAno() {
        return mesAno;
    }

    @Override
    public double calcularMultaPorDia() {
        return 0.50; // R$ 0,50 por dia de atraso
    }

    @Override
    public String toString() {
        return "REVISTA: " + super.toString() + " - Ed. " + edicao + " (" + mesAno + ")";
    }
}