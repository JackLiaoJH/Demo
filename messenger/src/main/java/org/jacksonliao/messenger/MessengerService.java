package org.jacksonliao.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author JacksonLiao
 * @version 1.0
 * @since 2017/1/3
 */
public class MessengerService extends Service {

    private Messenger messenger = new Messenger(new WebViewHandler(MessengerService.this));

    static class WebViewHandler extends Handler {
        private WeakReference<MessengerService> mReference;

        WebViewHandler(MessengerService messengerService) {
            mReference = new WeakReference<>(messengerService);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (mReference != null) {
                        MessengerService service = mReference.get();
                        if (service != null) {
                            Bundle msgData = msg.getData();
                            String url = msgData.getString("url");
                            Toast.makeText(service.getApplicationContext(), "receive:"/*+ msg.obj*/ + url, Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
