package com.example.myreads;

import androidx.annotation.NonNull;

public enum Genre {
    SF(R.drawable.sf,"Science-fiction"),
    THRILLER(R.drawable.thriller,"Thriller"),
    POESIE(R.drawable.poetry,"Poésie"),
    PHILO(R.drawable.philosophy,"Philosophie"),
    CLASSIQUE(R.drawable.classic,"Classique"),
    FANTAISIE(R.drawable.fantasy,"Fantaisie"),
    ROMANCE(R.drawable.romance,"Romance"),
    BIOGRAPHY(R.drawable.biography, "Biographie"),
    POLITIQUE(R.drawable.politics,"Politique"),
    POLAR(R.drawable.polar,"Polar"),
    MANGA(R.drawable.manga,"Manga"),
    MANHWA(R.drawable.manhwa,"Manhwa"),
    BD(R.drawable.bd,"BD"),
    DYSTOPIE(R.drawable.dystopia,"Dystopie");

    private final int  drawable;
    private final String    label;

    Genre(int drawable, String label) {
        this.drawable = drawable;
        this.label =    label;
    }

    public static Genre findGenreByLabel(String label) {
        for (Genre g : Genre.values()) {
            if (g.label.equals(label)) {
                return g;
            }
        }
        return null;
    }

    public int getDrawable() {
        return drawable;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }

}
