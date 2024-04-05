package com.example.myreads;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

public enum Genre {
    SF(R.drawable.sf, R.string.sf),
    THRILLER(R.drawable.thriller,R.string.thriller),
    POESIE(R.drawable.poetry,R.string.poesie),
    PHILO(R.drawable.philosophy,R.string.philo),
    CLASSIQUE(R.drawable.classic,R.string.classique),
    FANTAISIE(R.drawable.fantasy,R.string.fantaisie),
    ROMANCE(R.drawable.romance,R.string.romance),
    BIOGRAPHY(R.drawable.biography, R.string.biography),
    POLITIQUE(R.drawable.politics,R.string.politique),
    POLAR(R.drawable.polar,R.string.polar),
    MANGA(R.drawable.manga,R.string.manga),
    MANHWA(R.drawable.manhwa,R.string.manhwa),
    BD(R.drawable.bd,R.string.bd),
    DYSTOPIE(R.drawable.dystopia,R.string.dystopie);
    private final int  drawable;
    private final int  labelId;

    Genre(int drawable, int label) {
        this.drawable = drawable;
        this.labelId  = label;
    }

    public static Genre findGenreByLabel(String label) {
        for (Genre g : Genre.values()) {
            if (g.toString().equals(label)) {
                return g;
            }
        }
        return null;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getLabelId() { return labelId; }

}
