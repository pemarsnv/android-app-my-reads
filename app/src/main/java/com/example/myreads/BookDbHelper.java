package com.example.myreads;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";

    public final String SQL_CREATE = "CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT NOT NULL, auteur TEXT NOT NULL, genre TEXT NOT NULL, notes TEXT NOT NULL, lu INTEGER NOT NULL)";
    public final String SQL_DELETE = "DROP TABLE IF EXISTS Books";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

}
