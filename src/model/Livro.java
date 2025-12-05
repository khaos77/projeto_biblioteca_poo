public class Livro extends ItemBiblioteca {
    private String autor;
    private long isbn;

    public Livro(String titulo, String autor, String codigo, long isbn) {
        super(titulo, codigo, 14); // 14 dias para livros
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public long getIsbn() {
        return isbn;
    }

    @Override
    public double calcularMultaPorDia() {
        return 1.00; // R$ 1,00 por dia de atraso
    }

    @Override
    public String toString() {
        return "LIVRO: " + super.toString() + " - " + autor + " (ISBN: " + isbn + ")";
    }
}