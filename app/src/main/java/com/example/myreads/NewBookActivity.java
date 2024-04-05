package com.example.myreads;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class NewBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {

        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.activity_new_book);

        //setting up the genreSpinner
        Spinner genreSpin = findViewById(R.id.genreSpinner);
        List<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList,Genre.values());
        ArrayAdapter<Genre> spinnerAdapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, genreList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView= view.findViewById(android.R.id.text1);
                textView.setText(getString(genreList.get(position).getLabelId()));
                Log.i("nom",getString(genreList.get(position).getLabelId()));
                textView.setTextSize(20);
                return view;
            }
        };
        genreSpin.setAdapter(spinnerAdapter);

        //setting up the title field
        AutoCompleteTextView title = findViewById(R.id.editTitleText);
        String[] titles = {"1984","La ferme des animaux","Le gros caca"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        title.setAdapter(adapter);

        //setting up the radio btn selected by default
        RadioGroup statusRadioGrp = findViewById(R.id.radioGroup);
        statusRadioGrp.check(R.id.radioBtnCurrently);

        //setting up both buttons
        Button createBtn = findViewById(R.id.approveBtn);
        createBtn.setOnClickListener((v) -> {
            if (!checkForEmptyField()) {
                if (createBookFromFields()) {
                    finish();
                    Toast.makeText(NewBookActivity.this,R.string.bookAdded_str,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewBookActivity.this,R.string.error_str,Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NewBookActivity.this,R.string.missingTitleAuth_str,Toast.LENGTH_SHORT).show();
            }

        });

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener((v) -> finish());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu called");
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.mymenu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i(TAG, item.getItemId()+"");
        switch (item.getItemId()) {
            case R.id.menuAdd:
                Intent intent = new Intent(NewBookActivity.this,NewBookActivity.class);
                startActivity(intent);
                break;
            case R.id.menuQuit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean checkForEmptyField() {

        EditText bookTitle = findViewById(R.id.editTitleText);
        String title = String.valueOf(bookTitle.getText());
        boolean isTitleEmpty = title.isEmpty();

        EditText bookAuthor = findViewById(R.id.editAuthorText);
        String author = String.valueOf(bookAuthor.getText());
        boolean isAuthorEmpty = author.isEmpty();

        return isTitleEmpty || isAuthorEmpty;

    }

    boolean createBookFromFields() {

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao     = new DAOBook(db);

        EditText bookTitle = findViewById(R.id.editTitleText);
        String title = String.valueOf(bookTitle.getText());

        EditText bookAuthor = findViewById(R.id.editAuthorText);
        String author = String.valueOf(bookAuthor.getText());

        Spinner genreSpin = findViewById(R.id.genreSpinner);
        String genre = genreSpin.getSelectedItem().toString();

        EditText bookNotes = findViewById(R.id.editNotesText);
        String notes = String.valueOf(bookNotes.getText());

        RadioButton readButton = findViewById(R.id.radioBtnPastly);
        boolean read = readButton.isChecked();

        Book book = new Book(0,title,author,Genre.findGenreByLabel(genre),notes,read);

        returnBookIntent(book);
        return dao.add(book);

    }

    void returnBookIntent(Book b) {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("book",b);
        setResult(RESULT_OK, resultIntent);
    }

}
