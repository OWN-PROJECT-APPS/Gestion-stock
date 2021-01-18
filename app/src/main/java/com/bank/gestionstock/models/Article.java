package com.bank.gestionstock.models;

public class Article {
    private int id;
    private String libelle;
    private int quantite;
    private double price;

    public Article(int id, String libelle, int quantite, double price) {
        this.id = id;
        this.libelle = libelle;
        this.quantite = quantite;
        this.price = price;
    }

    public Article(String libelle, int quantite,double price) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Article => " + "id = " + id + " | libelle = " + libelle + " | Quantity = " + quantite + "\\";
    }
}