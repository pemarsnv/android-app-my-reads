package com.example.myreads;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {
        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.book_details_view);

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao = new DAOBook(db);

        int bookid = getIntent().getIntExtra("bookId",0);
        Book book = null;
        if (dao.getById(bookid).isPresent()) {
            book = dao.getById(bookid).get();
        } else {
            finish();
        }

        TextView title = (TextView) findViewById(R.id.bookDetailsTitle);
        TextView author = (TextView) findViewById(R.id.bookDetailsAuthor);
        TextView genre = (TextView) findViewById(R.id.bookDetailsGenre);
        TextView note = (TextView) findViewById(R.id.bookDetailsNote);
        TextView status = (TextView) findViewById(R.id.bookDetailsStatus);
        title.setText(book.getTitre());
        author.setText(book.getAuteur());
        genre.setText(book.getGenre().toString());
        note.setText(book.getNotes());
        status.setText(book.isLu() ? R.string.book_details_reading_state_already_read : R.string.book_details_reading_state_reading);

        Button btnCancel = (Button) findViewById(R.id.bookDetailsCancelBtn);

        btnCancel.setOnClickListener((v) -> {
            finish();
            Intent intent = new Intent(this, BookActionsActivity.class);
            intent.putExtra("bookId", bookid);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.mymenu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                Intent intent = new Intent(BookDetailsActivity.this,NewBookActivity.class);
                startActivity(intent);
                break;
            case R.id.menuQuit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

