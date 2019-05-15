package com.example.za_zhujiangtao.zhupro.iml;

import android.os.RemoteException;

import com.example.za_zhujiangtao.zhupro.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
