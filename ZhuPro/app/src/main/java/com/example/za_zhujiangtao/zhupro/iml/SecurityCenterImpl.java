package com.example.za_zhujiangtao.zhupro.iml;

import android.os.RemoteException;

import com.example.za_zhujiangtao.zhupro.ISecurityCenter;

public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private final static char SECRET_CODE = '^';
    @Override
    public String encrypt(String content) throws RemoteException {
        char [] chars = content.toCharArray();
        for (int i=0; i<chars.length; i++){
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
