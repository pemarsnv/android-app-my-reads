package com.example.myreads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
        DAOBook dao = new DAOBook(db);

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
        currentReadsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookView book = (BookView) parent.getItemAtPosition(position);
                openBookActions(book.getBookId());
                //Toast.makeText(MainActivity.this,book.getBookId(),Toast.LENGTH_SHORT).show();
            }
        });

        //display all of the past reads in the database

        this.pastReadsViewArray = new ArrayList<>();
        for (Book b :  dao.getReadBooks()) {
            pastReadsViewArray.add(new BookView(b));
        }

        this.pastBAdapter = new BookViewAdapter(this,pastReadsViewArray);
        ListView pastReadsView = findViewById(R.id.pastReadsListView);
        pastReadsView.setAdapter(this.pastBAdapter);

        pastReadsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookView book = (BookView) parent.getItemAtPosition(position);
                openBookActions(book.getBookId());
                //Toast.makeText(MainActivity.this,bookId+"",Toast.LENGTH_SHORT).show();
            }
        });

        Button newBtn = findViewById(R.id.newBookButton);

        newBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this,NewBookActivity.class);
            startActivity(intent);
        });

    }

    private void openBookActions(int bookId)  {
        Intent intent = new Intent(MainActivity.this,BookActionsActivity.class);
        intent.putExtra("bookId",bookId);
        startActivity(intent);
    }

}