package com.example.za_zhujiangtao.zhupro;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = "com.example.za_zhujiangtao.zhupro.book.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private SQLiteDatabase mDb;
    private Context mContext;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private String getTableName(Uri uri){
        String tabName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tabName = BookDbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tabName = BookDbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tabName;
    }

    private void initProviderData(){
        mDb = new BookDbOpenHelper(getContext()).getWritableDatabase();
        mDb.execSQL("delete from "+BookDbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+BookDbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(1, 'android','小李');");
        mDb.execSQL("insert into book values(4, 'java','小王');");
        mDb.execSQL("insert into book values(3, 'js','小猪');");
        mDb.execSQL("insert into user values(1, '丁泽',1);");
        mDb.execSQL("insert into user values(2, '春梅',0);");
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        mContext = getContext();
        //实际中不建议在主线程中进行耗时的数据库操作，这里只是为了演示
        initProviderData();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query, uri = "+uri.toString());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported Uri "+uri.toString());
        }
        return mDb.query(table, projection, selection, selectionArgs, null, null,sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported Uri "+uri.toString());
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported Uri "+uri.toString());
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0){
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported Uri "+uri.toString());
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0){
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
