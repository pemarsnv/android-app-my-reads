package com.example.myreads;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<BookView> currentReadsViewArray;
    public ArrayList<BookView> pastReadsViewArray;
    public BookViewAdapter currentBAdapter;
    public BookViewAdapter pastBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao     = new DAOBook(db);

        //display all of the current reads in the database

        //convert all the current books into book views and put them into an array
        this.currentReadsViewArray  = new ArrayList<>();
        for (Book b : dao.getUnreadBooks()) {
            currentReadsViewArray.add(new BookView(b));
        }

        //create the ListView and set its adapter with a new adapter using the book views array
        this.currentBAdapter = new BookViewAdapter(this,currentReadsViewArray);
        ListView currentReadsView = (ListView) findViewById(R.id.currentReadslistView);
        currentReadsView.setAdapter(this.currentBAdapter);

        //display all of the past reads in the database

        this.pastReadsViewArray = new ArrayList<>();
        for (Book b :  dao.getReadBooks()) {
            pastReadsViewArray.add(new BookView(b));
        }

        this.pastBAdapter = new BookViewAdapter(this,pastReadsViewArray);
        ListView pastReadsView = (ListView) findViewById(R.id.pastReadsListView);
        pastReadsView.setAdapter(this.pastBAdapter);

        Button newBtn = (Button) findViewById(R.id.newBookButton);

        newBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this,NewBookActivity.class);
            startActivity(intent);
        });

    }



}