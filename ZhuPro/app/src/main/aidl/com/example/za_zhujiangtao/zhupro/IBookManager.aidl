// IBookManager.aidl
package com.example.za_zhujiangtao.zhupro;
import com.example.za_zhujiangtao.zhupro.Book;
import com.example.za_zhujiangtao.zhupro.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getAllBooks();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
