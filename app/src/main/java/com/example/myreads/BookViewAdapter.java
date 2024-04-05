package com.example.myreads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookViewAdapter extends ArrayAdapter<BookView> {

    public BookViewAdapter(@NonNull Context context, ArrayList<BookView> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_view, parent, false);
        }

        BookView currentBookPosition = getItem(position);
        assert currentBookPosition != null;

        TextView titleView = currentItemView.findViewById(R.id.bookTitleView);
        titleView.setText(currentBookPosition.getBookTitle());

        TextView authorView = currentItemView.findViewById(R.id.bookAuthorView);
        authorView.setText(currentBookPosition.getBookAuthor());

        ImageView genreView = currentItemView.findViewById(R.id.bookGenreView);
        genreView.setImageResource(currentBookPosition.getBookGenreIcon());

        return currentItemView;

    }
}
