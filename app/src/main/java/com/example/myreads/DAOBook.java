package com.example.myreads;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Optional;

public class DAOBook {

    private SQLiteDatabase db;
    private final BookDbHelper dbHelper;

    public DAOBook(BookDbHelper bdd) {
        this.dbHelper = bdd;
    }

    public boolean add(Book book) {

        this.db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre",book.getTitre());
        values.put("auteur",book.getAuteur());
        values.put("genre",book.getGenre().toString());
        values.put("notes",book.getNotes());
        values.put("lu",book.isLu() ? 1 : 0);

        long id = this.db.insert("Books",null,values);
        if (id > -1) {
            book.setId((int) id);
        }
        db.close();
        return id > -1;
    }

    public ArrayList<Book> getAll() {

        this.db = this.dbHelper.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();
        String[] col = {"id","titre","auteur","genre","notes","lu"};
        String[] select = {};

        Cursor curs = this.db.query("Books",col,"",select,null,null,"id ASC");

        if (curs.moveToFirst()) {
            do {

                int id          = (int) curs.getLong(curs.getColumnIndexOrThrow("id"));
                String titre    = curs.getString(curs.getColumnIndexOrThrow("titre"));
                String auteur   = curs.getString(curs.getColumnIndexOrThrow("auteur"));
                String genre    = curs.getString(curs.getColumnIndexOrThrow("genre"));
                String notes    = curs.getString(curs.getColumnIndexOrThrow("notes"));
                boolean lu      = curs.getInt(curs.getColumnIndexOrThrow("lu")) == 1;

                books.add(new Book(id,titre,auteur,Genre.findGenreByLabel(genre), notes,lu));

            } while (curs.moveToNext());
        }

        curs.close();   this.db.close();
        return books;

    }

    public Optional<Book> getById(int id) {

        this.db = this.dbHelper.getReadableDatabase();
        String[] col = {"id","titre","auteur","genre","notes","lu"};
        String[] select = {id+""};

        Cursor curs = this.db.query("Books",col,"id=?",select,null,null,"id ASC");

        if (curs.moveToFirst()) {
            do {

                String titre    = curs.getString(curs.getColumnIndexOrThrow("titre"));
                String auteur   = curs.getString(curs.getColumnIndexOrThrow("auteur"));
                String genre    = curs.getString(curs.getColumnIndexOrThrow("genre"));
                String notes    = curs.getString(curs.getColumnIndexOrThrow("notes"));
                boolean lu      = curs.getInt(curs.getColumnIndexOrThrow("lu")) == 1;

                return Optional.of(new Book(id,titre,auteur,Genre.findGenreByLabel(genre),notes,lu));


            } while (curs.moveToNext());
        }

        curs.close();   this.db.close();
        return Optional.empty();

    }

    public ArrayList<Book> getReadBooks() {

        this.db = this.dbHelper.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();
        String[] col = {"id", "titre", "auteur", "genre", "notes", "lu"};
        String[] select = {};

        Cursor curs = this.db.query("Books", col, "lu=1", select, null, null, "id ASC");

        if (curs.moveToFirst()) {
            do {

                int id = (int) curs.getLong(curs.getColumnIndexOrThrow("id"));
                String titre = curs.getString(curs.getColumnIndexOrThrow("titre"));
                String auteur = curs.getString(curs.getColumnIndexOrThrow("auteur"));
                String genre = curs.getString(curs.getColumnIndexOrThrow("genre"));
                String notes = curs.getString(curs.getColumnIndexOrThrow("notes"));
                boolean lu = curs.getInt(curs.getColumnIndexOrThrow("lu")) == 1;

                books.add(new Book(id, titre, auteur, Genre.findGenreByLabel(genre), notes, lu));

            } while (curs.moveToNext());
        }

        curs.close();   this.db.close();
        return books;

    }

    public ArrayList<Book> getUnreadBooks() {

        this.db = this.dbHelper.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();
        String[] col = {"id", "titre", "auteur", "genre", "notes", "lu"};
        String[] select = {};

        Cursor curs = this.db.query("Books", col, "lu=0", select, null, null, "id ASC");

        if (curs.moveToFirst()) {
            do {

                int id = (int) curs.getLong(curs.getColumnIndexOrThrow("id"));
                String titre = curs.getString(curs.getColumnIndexOrThrow("titre"));
                String auteur = curs.getString(curs.getColumnIndexOrThrow("auteur"));
                String genre = curs.getString(curs.getColumnIndexOrThrow("genre"));
                String notes = curs.getString(curs.getColumnIndexOrThrow("notes"));
                boolean lu = curs.getInt(curs.getColumnIndexOrThrow("lu")) == 1;

                books.add(new Book(id, titre, auteur, Genre.findGenreByLabel(genre), notes, lu));

            } while (curs.moveToNext());
        }

        curs.close();    this.db.close();
        return books;

    }

    public boolean delete(int id) {

        this.db = this.dbHelper.getWritableDatabase();
        int rowcount = this.db.delete("Books","id=?", new String[]{id + ""});
        return rowcount > 0;
    }

}
