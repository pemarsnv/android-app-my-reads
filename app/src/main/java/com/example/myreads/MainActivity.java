package com.example.myreads;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<BookView> currentReadsViewArray;
    public ArrayList<BookView> pastReadsViewArray;
    public BookViewAdapter currentBAdapter;
    public BookViewAdapter pastBAdapter;
    public static final int        REQ_CODE_NOUVEAU_LIVRE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao = new DAOBook(db);



        //display all of the current reads in the database

        //convert all the current books into book views and put them into an array
        this.currentReadsViewArray  = new ArrayList<>();
        for (Book b : dao.getUnreadBooks()) {
            currentReadsViewArray.add(new BookView(b));
        }

        //create the ListView and set its adapter with a new adapter using the book views array
        this.currentBAdapter = new BookViewAdapter(this,currentReadsViewArray);
        ListView currentReadsView = findViewById(R.id.currentReadslistView);
        currentReadsView.setAdapter(this.currentBAdapter);
        currentReadsView.setOnItemClickListener((parent, view, position, id) -> {
            BookView book = (BookView) parent.getItemAtPosition(position);
            openBookActions(book.getBookId());
        });

        //display all of the past reads in the database

        this.pastReadsViewArray = new ArrayList<>();
        for (Book b :  dao.getReadBooks()) {
            pastReadsViewArray.add(new BookView(b));
        }

        this.pastBAdapter = new BookViewAdapter(this,pastReadsViewArray);
        ListView pastReadsView = findViewById(R.id.pastReadsListView);
        pastReadsView.setAdapter(this.pastBAdapter);

        pastReadsView.setOnItemClickListener((parent, view, position, id) -> {
            BookView book = (BookView) parent.getItemAtPosition(position);
            openBookActions(book.getBookId());
        });

        Button newBtn = findViewById(R.id.newBookButton);

        newBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this,NewBookActivity.class);
            startActivityForResult(intent,REQ_CODE_NOUVEAU_LIVRE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu called");
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.mymenu, menu);
        MenuItem item = menu.findItem(R.id.menuAdd);
        item.setVisible(false);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.exit(0);
        return super.onOptionsItemSelected(item);
    }

    private void openBookActions(int bookId)  {
        Intent intent = new Intent(MainActivity.this,BookActionsActivity.class);
        intent.putExtra("bookId",bookId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_NOUVEAU_LIVRE && resultCode == RESULT_OK && data != null) {
            Book book = data.getParcelableExtra("book");
            assert book != null;
            openBookActions(book.getId());
        }
    }

    public Context getContext() {
        return this;
    }

}