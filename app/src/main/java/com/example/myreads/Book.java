package com.example.myreads;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {

    private int             id;
    private final String    titre;
    private final String    auteur;
    private final Genre     genre;
    private final String    notes;
    private boolean         lu;
    private boolean         fav;

    public Book(int id, String titre, String auteur, Genre genre, String notes, boolean lu) {
        this.id     = id;           this.titre = titre;
        this.auteur = auteur;       this.genre = genre;
        this.notes  = notes;        this.lu    = lu;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        titre = in.readString();
        auteur = in.readString();
        notes = in.readString();
        genre = Genre.values()[in.readInt()];
        lu = in.readByte() != 0;
        fav = in.readByte() != 0;
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

    public String getAuteur() {
        return auteur;
    }


    public Genre getGenre() {
        return genre;
    }

    public String getNotes() {
        return notes;
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
        return this.titre+" - "+this.auteur+" ("+this.genre+")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titre);
        dest.writeString(auteur);
        dest.writeString(notes);
        dest.writeInt(genre.ordinal());
        dest.writeByte((byte) (lu ? 1 : 0));
        dest.writeByte((byte) (fav ? 1 : 0));
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
