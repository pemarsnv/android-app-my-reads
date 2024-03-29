package com.example.myreads;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public enum Genre {
    SF(1,"Science-fiction"),
    THRILLER(1,"Thriller"),
    POESIE(1,"Poésie"),
    PHILO(1,"Philosophie"),
    YA(1,"Jeune adulte"),
    CLASSIQUE(1,"Classique"),
    FANTAISIE(R.drawable.fantasy,"Fantaisie"),
    ROMANCE(1,"Romance"),
    HORREUR(1,"Horror"),
    NF(1, "Non-fiction"),
    POLITIQUE(1,"Politique"),
    POLAR(1,"Polar"),
    MANGA(R.drawable.manga,"Manga"),
    MANHWA(R.drawable.manhwa,"Manhwa"),
    BD(1,"BD"),
    DYSTOPIE(R.drawable.dystopia,"Dystopie");


    private final int  drawable;
    private final String    label;

    private Genre(int drawable, String label) {
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
