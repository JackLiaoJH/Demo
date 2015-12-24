package com.liaojh.aidldemoservice;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public class AidlDemoService extends Service {
    private static final String TAG = AidlDemoService.class.getSimpleName();
    private CopyOnWriteArrayList<User> userLIst = new CopyOnWriteArrayList<>();
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    //使用这个无法反注册IOnAddNewUserListener
    //private CopyOnWriteArrayList<IOnAddNewUserListener> mListener = new CopyOnWriteArrayList<>();
    //需要使用下面这个类来实现进程间对象之间的解注册功能(这个不是list)
    private RemoteCallbackList<IOnAddNewUserListener> callbackList = new RemoteCallbackList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userLIst.add(new User("jack", "nan"));
        userLIst.add(new User("mait", "woman"));

        //启动一个线程每隔5秒过去刷新一次
        new Thread(new AddUserThread(this)).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        atomicBoolean.set(true);
    }

    private Binder mBinder = new IUserManager.Stub() {

        @Override
        public void addUser(User user) throws RemoteException {

            userLIst.add(user);
        }

        @Override
        public List<User> getUsers() throws RemoteException {
            return userLIst;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void registerListener(IOnAddNewUserListener listener) throws RemoteException {
          /*  if (!mListener.contains(listener)) {
                mListener.add(listener);
            } else {
                Log.d(TAG, "The " + mListener + " already exit!");
            }
            Log.d(TAG, "mListener.size():" + mListener.size());*/
            callbackList.register(listener);
            Log.d(TAG, "register  :" + callbackList.getRegisteredCallbackCount());
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterListener(IOnAddNewUserListener listener) throws RemoteException {
           /* if (mListener.contains(listener)) {
                mListener.remove(listener);
            } else {
                Log.d(TAG, "The " + mListener + " already remove!");
            }
            Log.d(TAG, "mListener.size():" + mListener.size());*/
            callbackList.unregister(listener);
            Log.d(TAG, "unregister  :" + callbackList.getRegisteredCallbackCount());
        }
    };

    private void onNewUserAdd(User user) throws RemoteException {
        userLIst.add(user);
        Log.d(TAG, "add user :" + user.toString());
       /* for (IOnAddNewUserListener listener : mListener) {
            listener.onAddNewUser(user);
            Log.d(TAG, "notify listener " + mListener);
        }*/
        int N = callbackList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnAddNewUserListener l = callbackList.getBroadcastItem(i);
            if (l != null) {
                l.onAddNewUser(user);
                Log.d(TAG, "notify listener " + l);
            }
        }

        callbackList.finishBroadcast();
    }

    private static class AddUserThread extends Thread {
        private int index = 0;
        private WeakReference<AidlDemoService> weakReference = null;

        public AddUserThread(AidlDemoService service) {
            if (weakReference == null) {
                weakReference = new WeakReference<>(service);
            }
        }

        @Override
        public void run() {
            super.run();
            while (!atomicBoolean.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                User user = new User();
                user.name = "name" + index++;
                if (index % 2 == 0) {
                    user.sex = "man";
                } else {
                    user.sex = "woman";
                }
                AidlDemoService demoService = weakReference.get();
                try {
                    demoService.onNewUserAdd(user);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
