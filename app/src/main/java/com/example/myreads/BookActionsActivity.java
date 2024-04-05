package com.example.myreads;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
            Intent intent = new Intent(this,BookDetailsActivity.class);
            intent.putExtra("bookId", id);
            startActivity(intent);
            finish();
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
            AlertDialog.Builder builder = getDeleteBuilder(dao);
            AlertDialog dialog = builder.create();
            dialog.show();
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
                Intent intent = new Intent(BookActionsActivity.this,NewBookActivity.class);
                startActivity(intent);
                break;
            case R.id.menuQuit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private AlertDialog.Builder getDeleteBuilder(DAOBook dao) {

        //préparation générale du builder
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActionsActivity.this);
        builder.setCancelable(true);
        builder.setTitle(R.string.title_deleteDialogStr);
        builder.setMessage(R.string.msg_deleteDialogStr);

        //préparation du bouton confirmer du builder
        builder.setPositiveButton(R.string.confirm_buttonStr,
                (dialog, which) -> {
                    dao.delete(book.getId());
                    finish();
                    dialog.dismiss();
                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(BookActionsActivity.this,R.string.bookDeleted_str,Toast.LENGTH_SHORT).show() ;
                });

        //préparation du bouton annuler du builder
        builder.setNegativeButton(R.string.cancel_buttonStr,
                (dialog, which) -> dialog.dismiss());

        return builder;

    }

}
