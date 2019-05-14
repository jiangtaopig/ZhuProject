package com.example.za_zhujiangtao.zhupro;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IPCActivity extends AppCompatActivity {
    private final static String TAG = "IPCActivity";

    @BindView(R.id.send_msg)
    Button mSendMsg;

    @BindView(R.id.send_aidl_msg)
    Button mSendAidlMsg;

    @BindView(R.id.provider_ipc)
    Button mProviderIpc;

    private Messenger mMessenger;
    private IBookManager mBookManager;
    private boolean mMessengerConnected;

    private static final String URI = "content://com.example.za_zhujiangtao.zhupro.book.provider";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_layout);
        ButterKnife.bind(this);

        mSendMsg.setOnClickListener(v -> {
            Intent intent = new Intent(this, MessengerService.class);
            bindService(intent, mMessengerConnection, Context.BIND_AUTO_CREATE);
        });

        mSendAidlMsg.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookManagerService.class);
            bindService(intent, mAidlConnect, Context.BIND_AUTO_CREATE);
        });

        mProviderIpc.setOnClickListener(v -> {
            Uri uri = Uri.parse(URI);
//            getContentResolver().query(uri, null, null, null, null);
        });
    }

    @OnClick(R.id.query)
    protected void query(){
        Uri uri = Uri.parse(URI+"/book");
        transferBook(uri);
    }

    @OnClick(R.id.insert)
    protected void insert(){
        Uri uri = Uri.parse(URI+"/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "倚天屠龙记");
        values.put("author","金庸");

        getContentResolver().insert(uri, values);
        transferBook(uri);
    }

    @OnClick(R.id.update)
    protected void update(){
        Uri uri = Uri.parse(URI+"/book");
        ContentValues values = new ContentValues();
        values.put("_id", 8);
        values.put("name", "韦小宝");
        values.put("author","金庸");
        getContentResolver().update(uri, values, "_id=?", new String[]{"6"});
        transferBook(uri);
    }

    @OnClick(R.id.delete)
    protected void delete(){
        Uri uri = Uri.parse(URI+"/book");
        getContentResolver().delete(uri, "name=?", new String[]{"倚天屠龙记"});
        transferBook(uri);
    }

    private void transferBook(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "name", "author"}, null, null, null);
        while (cursor.moveToNext()){
            Log.d(TAG, "name = "+cursor.getString(cursor.getColumnIndex("name"))+", author = "+cursor.getString(cursor.getColumnIndex("author")));
        }
        cursor.close();
    }

    private ServiceConnection mAidlConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            mBookManager = bookManager;
            try {
                List<Book> books = bookManager.getAllBooks();
                for (Book book : books){
                    Log.e("IPCActivity", "book name = "+book.getBookName()+", author = "+book.getAuthor());
                }
                Log.e("IPCActivity","..........................");
                        Book book = new Book("鬼吹灯", "南派三叔");
                bookManager.addBook(book);
                List<Book> books2 = bookManager.getAllBooks();
                for (Book bk : books2){
                    Log.e("IPCActivity", "book name = "+bk.getBookName()+", author = "+bk.getAuthor());
                }
                mBookManager.registerListener(mOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {//这个是在Binder线程池中执行，不能直接更新UI
            Log.e("IPCActivity", "....new book : "+book.getBookName() + ", threadName = "+Thread.currentThread().getName());
        }
    };

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessengerConnected = true;
            mMessenger = new Messenger(service);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            msg.replyTo = mGetReplayMessenger;
            Bundle data = new Bundle();
            data.putString("msg", "hello, world");
            msg.setData(data);

            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mGetReplayMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.e("IPCActivity", "receive msg from service: "+msg.getData().getString("replay"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMessengerConnected){
            unbindService(mMessengerConnection);
        }
        unbindService(mAidlConnect);

        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()){
            try {
                Log.e("IPCActivity", "unregister listener :"+mOnNewBookArrivedListener);
                mBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
