package org.example.model;

public class Ksiazka {
    private int id;
    private String tytul;
    private String autor;
    private int stanMagazynowy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getStanMagazynowy() {
        return stanMagazynowy;
    }

    public void setStanMagazynowy(int stanMagazynowy) {
        this.stanMagazynowy = stanMagazynowy;
    }

    public Ksiazka(String tytul, String autor, int stanMagazynowy) {
        this.tytul = tytul;
        this.autor = autor;
        this.stanMagazynowy = stanMagazynowy;
    }
}
