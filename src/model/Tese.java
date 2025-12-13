package model;

public class Tese extends ItemBiblioteca {
    private String autor;
    private String orientador;
    private int ano;

    public Tese(String titulo, String codigo, String autor, String orientador, int ano) {
        super(titulo, codigo, 21); 
        this.autor = autor;
        this.orientador = orientador;
        this.ano = ano;
    }

    public String getAutor(){
        return autor;
    } 

    public String getOrientador(){
        return orientador;
    } 

    public int getAno(){
        return ano;
    } 

    @Override
    public double calcularMultaPorDia() {
        return 2.00; 
    }

    @Override
    public String toString() {
        return "TESE: " + super.toString() +
         " - Autor: " + autor + 
         " | Orientador : " + orientador + 
         " | Ano: " + ano;
    } 
}