package com.example.za_zhujiangtao.zhupro;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String bookName;
    private String author;

    public Book(String name, String author){
        this.bookName = name;
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookName);
        dest.writeString(this.author);
    }

    protected Book(Parcel in) {
        this.bookName = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
