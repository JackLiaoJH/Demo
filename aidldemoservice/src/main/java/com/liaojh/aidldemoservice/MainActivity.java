package com.liaojh.aidldemoservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    IUserManager iUserManager;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iUserManager = IUserManager.Stub.asInterface(service);

            try {
                List<User> users = iUserManager.getUsers();
                for (User user : users) {
                    Log.i("dddd", user.name + "," + user.sex);
                }

                iUserManager.registerListener(addNewUserListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, AidlDemoService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        if (iUserManager != null && iUserManager.asBinder().isBinderAlive()) {
            try {
                iUserManager.unregisterListener(addNewUserListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.i("dddd", "add user:" + msg.obj.toString());
                    break;
            }
        }
    };

    private IOnAddNewUserListener addNewUserListener = new IOnAddNewUserListener.Stub() {

        @Override
        public void onAddNewUser(User user) throws RemoteException {
            mHandler.obtainMessage(1, user).sendToTarget();
        }
    };
}
