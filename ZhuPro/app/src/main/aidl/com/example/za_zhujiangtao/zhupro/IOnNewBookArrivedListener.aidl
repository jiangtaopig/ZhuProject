// IOnNewBookArrivedListener.aidl
package com.example.za_zhujiangtao.zhupro;
import com.example.za_zhujiangtao.zhupro.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
