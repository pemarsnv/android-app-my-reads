package com.example.myreads;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public ArrayList<BookView> currentReadsViewArray;
    public ArrayList<Book> currentReadsArray;
    public ArrayList<BookView> pastReadsViewArray;
    public ArrayList<Book> pastReadsArray;
    public BookViewAdapter currentBAdapter;
    public BookViewAdapter pastBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao     = new DAOBook(db);

        for (Book b : dao.getAll()) {
            dao.delete(b.getId());
        }

        dao.add(new Book(0,"Blackwater","Michael McDowell",Genre.FANTAISIE,"le livre fav d'enzo",true));
        dao.add(new Book(0,"Solo Leveling","Chugong ",Genre.MANHWA,"le livre fav d'ibrahim",false));
        dao.add(new Book(0,"La servante écarlate","Margaret Atwood",Genre.DYSTOPIE,"le livre fav de pema",false));
        dao.add(new Book(0,"La ferme des animaux","George Orwell",Genre.POLITIQUE,"le livre fav de willem",true));

        this.currentReadsArray = dao.getUnreadBooks();
        this.currentReadsViewArray = new ArrayList<>();

        for (Book b : this.currentReadsArray) {
            currentReadsViewArray.add(new BookView(b));
        }

        this.currentBAdapter = new BookViewAdapter(this,currentReadsViewArray);

        ListView currentReadsView = (ListView) findViewById(R.id.currentReadslistView);

        currentReadsView.setAdapter(this.currentBAdapter);

        this.pastReadsArray = dao.getReadBooks();
        this.pastReadsViewArray = new ArrayList<>();

        for (Book b : this.pastReadsArray) {
            pastReadsViewArray.add(new BookView(b));
        }

        this.pastBAdapter = new BookViewAdapter(this,pastReadsViewArray);

        ListView pastReadsView = (ListView) findViewById(R.id.pastReadsListView);

        pastReadsView.setAdapter(this.pastBAdapter);

    }

}