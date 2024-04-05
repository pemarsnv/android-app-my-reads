package com.example.myreads;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookActionsActivity extends AppCompatActivity {

    private Book book;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_actions);

        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao = new DAOBook(db);

        int id = getIntent().getIntExtra("bookId",0);
        if (dao.getById(id).isPresent()) {
            this.book = dao.getById(id).get();
        } else {
            finish();
        }

        TextView textTitle = (TextView) findViewById(R.id.textTitleView);
        textTitle.setText(this.book.getTitre());

        TextView textAuthor = (TextView) findViewById(R.id.textAuthorView);
        textAuthor.setText(this.book.getAuteur());

        Button btnDetails = (Button) findViewById(R.id.btnDetails);

        btnDetails.setOnClickListener((v) -> {
            finish();
            Intent intent = new Intent(this,BookDetailsActivity.class);
            intent.putExtra("bookId", id);
            startActivity(intent);
        });

        Button btnRead = (Button) findViewById(R.id.btnReadState);

        if (this.book.isLu()) {
            btnRead.setText(getResources().getString(R.string.startReading_buttonStr));
        } else {
            btnRead.setText((getResources().getString(R.string.doneReading_buttonStr)));
        }

        btnRead.setOnClickListener((v ) -> {
            this.book.setLu(!this.book.isLu());
            dao.delete(book.getId());
            dao.add(book);
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener((v) -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener((v) -> {
            dao.delete(book.getId());
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

    }

}
