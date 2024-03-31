package com.example.myreads;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

        Spinner genreSpin = (Spinner) findViewById(R.id.genreSpinner);
        List<Genre> genreList = new ArrayList<>();
        Collections.addAll(genreList,Genre.values());

        ArrayAdapter<Genre> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genreList);
        genreSpin.setAdapter(spinnerAdapter);

        Button createBtn = (Button) findViewById(R.id.approveBtn);

        createBtn.setOnClickListener((v) -> {
            if (!checkForEmptyField()) {
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
            }
        });

        RadioGroup statusRadioGrp = (RadioGroup) findViewById(R.id.radioGroup);
        statusRadioGrp.check(R.id.radioBtnCurrently);

    }

    boolean checkForEmptyField() {

        EditText bookTitle = (EditText) findViewById(R.id.editTitleText);
        String title = String.valueOf(bookTitle.getText());
        boolean isTitleEmpty = title.isEmpty();

        EditText bookAuthor = (EditText) findViewById(R.id.editAuthorText);
        String author = String.valueOf(bookAuthor.getText());
        boolean isAuthorEmpty = author.isEmpty();

        return isTitleEmpty || isAuthorEmpty;

    }
}
