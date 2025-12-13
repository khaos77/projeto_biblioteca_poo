package model;

public class Livro extends ItemBiblioteca {
    private final String autor;
    private final long isbn;

    public Livro(String titulo, String autor, String codigo, long isbn) {
        super(titulo, codigo, 14); 
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
        return 1.00; 
    }

    @Override
    public String toString() {
        return "LIVRO: " + super.toString() + " - " + autor + " (ISBN: " + isbn + ")";
    }
}