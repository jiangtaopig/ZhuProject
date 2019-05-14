package com.example.za_zhujiangtao.zhupro;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private AtomicBoolean mServiceDestroyed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    //这样是无法解注册的，因为这里传的mOnNewBookArrivedListener在服务端会被生成不同的对象，所以要用RemoteCallbackListener
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListener = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListener = new RemoteCallbackList<>();


    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getAllBooks() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.register(listener);
            int size =  mListener.beginBroadcast();
            Log.e(TAG, "registerListener size = "+size);
            mListener.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.unregister(listener);
            int size =  mListener.beginBroadcast();
            Log.e(TAG, "unregister listener succeed");
            Log.e(TAG, "unregisterListener size = "+size);
            mListener.finishBroadcast();
        }

    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Book book1 = new Book("android艺术探索", "任玉刚");
        Book book2 = new Book("安徒生童话","安徒生");

        mBookList.add(book1);
        mBookList.add(book2);

        //每隔10s添加一本新书，然后通知客户端
        Observable.interval(10*1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (!mServiceDestroyed.get()){
                        String bookName = "神雕英雄传"+(int)(Math.random()*10 + 1);
                        Book book = new Book(bookName, "金庸");
                        try {
                            onNewBookArrived(book);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceDestroyed.set(true);
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        int size =  mListener.beginBroadcast();
        Log.e(TAG, "onNewBookArrived , notify listener ,size = "+size);
        if (size <= 0){
            return;
        }

        mBookList.add(book);
        for (int i = 0; i <size; i++){
            IOnNewBookArrivedListener listener = mListener.getBroadcastItem(i);
            if (listener != null){
                listener.onNewBookArrived(book);
            }
        }
        mListener.finishBroadcast();
    }
}
