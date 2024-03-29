package com.example.myreads;

public class BookView {

    private int bookId;
    private String bookTitle;
    private int bookGenreIcon;
    private String bookAuthor;

    public BookView(Book book) {
        this.bookId = book.getId();
        this.bookTitle = book.getTitre();
        this.bookGenreIcon = book.getGenre().getDrawable();
        this.bookAuthor = book.getAuteur();
    }

    public int getBookId() { return this.bookId; };

    public String getBookTitle() { return this.bookTitle; }

    public int getBookGenreIcon() { return this.bookGenreIcon; }

    public String getBookAuthor() { return this.bookAuthor; }

}
