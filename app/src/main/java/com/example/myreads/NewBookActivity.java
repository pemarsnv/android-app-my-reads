package com.example.myreads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
        ArrayAdapter<Genre> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genreList);
        genreSpin.setAdapter(spinnerAdapter);

        //setting up the title field
        AutoCompleteTextView title = findViewById(R.id.editTitleText);
        String[] titles = {"1984","La ferme des animaux","Le gros caca"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        title.setAdapter(adapter);

        title.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(NewBookActivity.this,title.getText(),Toast.LENGTH_SHORT).show());

        //setting up the radio btn selected by default
        RadioGroup statusRadioGrp = findViewById(R.id.radioGroup);
        statusRadioGrp.check(R.id.radioBtnCurrently);

        //setting up both buttons
        Button createBtn = findViewById(R.id.approveBtn);
        createBtn.setOnClickListener((v) -> {
            if (!checkForEmptyField()) {
                if (createBookFromFields()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(NewBookActivity.this,"Livre ajouté",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewBookActivity.this,"Une erreur s'est produite",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NewBookActivity.this,"Vous devez renseigner le titre et l'auteur",Toast.LENGTH_SHORT).show();
            }

        });

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener((v) -> {
            finish();
        });



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

        return dao.add(book);

    }

}
