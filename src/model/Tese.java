public class Tese extends ItemBiblioteca {
    private String autor;
    private int ano;

    public Tese(String titulo, String codigo, String autor, String orientador, int ano) {
        super(titulo, codigo, 21); // 21 dias para teses
        this.autor = autor;
        this.ano = ano;
    }

    @Override
    public double calcularMultaPorDia() {
        return 2.00; // R$ 2,00 por dia de atraso
    }

    @Override
    public String toString() {
        return "TESE: " + super.toString() + " - " + autor + " (" + ano + ")";
    }
}