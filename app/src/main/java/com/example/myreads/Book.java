package com.example.myreads;

import androidx.annotation.NonNull;

public class Book {

    private int id;
    private String titre;
    private String auteur;
    private Genre genre;
    private String notes;
    private boolean lu;

    public Book(int id, String titre, String auteur, Genre genre, String notes, boolean lu) {
        this.id = id;           this.titre = titre;
        this.auteur = auteur;   this.genre = genre;
        this.notes = notes;     this.lu = lu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    @NonNull
    @Override
    public String toString() {
        return this.titre+" par "+this.auteur+" ("+this.genre+")";
    }

}
