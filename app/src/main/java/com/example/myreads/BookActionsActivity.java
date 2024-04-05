package com.example.myreads;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BookActionsActivity extends AppCompatActivity {

    private Book book; //le livre que l'on manipule

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_actions);

        //appel de la bdd
        BookDbHelper db = new BookDbHelper(this);
        DAOBook dao = new DAOBook(db);

        //récupération du livre manipulé
        int id = getIntent().getIntExtra("bookId",0);
        if (dao.getById(id).isPresent()) {
            this.book = dao.getById(id).get();
        } else {
            //en cas d'erreur de récupération du livre, on ferme la page
            finish();
        }

        //mise à j des textes de l'auteur et du titre
        TextView textTitle = findViewById(R.id.textTitleView);
        textTitle.setText(this.book.getTitre());

        TextView textAuthor = findViewById(R.id.textAuthorView);
        textAuthor.setText(this.book.getAuteur());

        //récupération du bouton de lecture et instanciation du listener
        Button btnRead = findViewById(R.id.btnReadState);

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

        //récupération du bouton de suppression
        Button btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener((v) -> {
            AlertDialog.Builder builder = getDeleteBuilder(dao);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        //récupération du bouton de retour
        Button btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener((v) -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

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
