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
        return 1.50; // R$ 1,50 por dia de atraso
    }

    @Override
    public String toString() {
        return "LIVRO: " + super.toString() + " - Autor: " + autor;
    }
}
