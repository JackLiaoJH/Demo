package com.liaojh.bunderdeo;

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


/**
 * @author LiaoJH
 * @DATE 15/11/22
 * @VERSION 1.0
 * @DESC TODO
 */
public class MessegerService extends Service {

    private static final String TAG = "MessegerService";
    private Handler mSeviceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constans.WHAT_CLIENT_TO_SERVICE:
                    Log.i(TAG,"receive from client:" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message m = Message.obtain(null,Constans.WHAT_SERVICE_TO_CLIENT);
                    Bundle b = new Bundle();
                    b.putString("reply","恩,我已收到...");
                    msg.setData(b);
                    try {
                        mMessenger.send(m);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    private Messenger mMessenger = new Messenger(mSeviceHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind..................");
        return mMessenger.getBinder();
    }


}
