package com.example.za_zhujiangtao.zhupro;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_CLIENT:
                    Log.e(TAG, "receive msg from client: "+msg.getData().getString("msg"));
                    //收到客户端的消息并回复
                    Messenger client = msg.replyTo;
                    Message replayMsg = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("replay", "嗯，你的消息我已经收到了，稍后回复你");
                    replayMsg.setData(bundle);
                    try{
                        client.send(replayMsg);
                    }catch (RemoteException e){

                    }
                    break;
            }
        }
    }


    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
