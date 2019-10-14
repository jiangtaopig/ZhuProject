package com.example.za_zhujiangtao.zhupro._binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/9/27
 */
public class IMediaPlayerService extends Binder {
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case 1001:
                data.enforceInterface("IMediaPlayerService");
                String path = data.readString();
                start(path);
                reply.writeString("我是执行完返回的结果");
                break;
            case 1002:
                stop();
                break;
        }

        return super.onTransact(code, data, reply, flags);
    }

    public void start(String path){

    }

    public void stop(){

//        IBinder mRemote = null;
//        String path = "/sdcard/media/xxx.mp4";
//        int code = 1001;
//        Parcel data = Parcel.obtain();
//        Parcel reply = Parcel.obtain();
//        data.writeInterfaceToken("IMediaPlayerService");
//        data.writeString(path);
//        mRemote.transact(code, data, reply, 0);
//        IBinder binder = reply.readStrongBinder();
//        reply.recycle();
//        data.recycle();
    }
}
