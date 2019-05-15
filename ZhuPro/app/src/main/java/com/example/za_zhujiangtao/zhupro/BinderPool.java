package com.example.za_zhujiangtao.zhupro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.za_zhujiangtao.zhupro.iml.ComputeImpl;
import com.example.za_zhujiangtao.zhupro.iml.SecurityCenterImpl;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private final static String TAG = "BinderPool";
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = BINDER_NONE + 1;
    public static final int BINDER_SECURITY_CENTER = BINDER_COMPUTE + 1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool mInstance;

    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool(Context context){
        mContext = context.getApplicationContext();
        connectBindServicePoolService();
    }

    public static BinderPool getInstance(Context context){
        if (mInstance == null){
            synchronized (BinderPool.class){
                if (mInstance == null){
                    mInstance = new BinderPool(context);
                }
            }
        }
        return mInstance;
    }

    private synchronized void connectBindServicePoolService(){
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int bindCode){
        IBinder iBinder = null;
        if (mBinderPool != null){
            try {
                iBinder = mBinderPool.queryBinder(bindCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return iBinder;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binder died");
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBindServicePoolService();
        }
    };

    public static class BindPoolImpl extends IBinderPool.Stub{

        public BindPoolImpl(){
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder iBinder = null;
            switch (binderCode){
                case BINDER_COMPUTE:
                    iBinder = new ComputeImpl();
                    break;
                case BINDER_SECURITY_CENTER:
                    iBinder = new SecurityCenterImpl();
                    break;
            }
            return iBinder;
        }
    }

}
