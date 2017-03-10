package com.victor.ipc.model;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MessengerServerService extends Service {
    private static final int RECEIVE_MESSAGE_CODE = 0x0001;
    private static final int SEND_MESSAGE_CODE = 0x0002;

    private Messenger clientMessenger;
    private Messenger serviceMessenger;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == RECEIVE_MESSAGE_CODE){
                Bundle data = msg.getData();
                if(data != null){
                    String str = data.getString("msg");
                    Toast.makeText(getApplicationContext(),"MessengerServerService received " + str, Toast.LENGTH_SHORT).show();
                }
                clientMessenger = msg.replyTo;//这个Message是在客户端中创建的
                if(clientMessenger!=null){
                    Message msgToClient = Message.obtain();
                    msgToClient.what = SEND_MESSAGE_CODE;
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","客户端，我接收到你的消息了，这是我回应给你的，看到了吗？");
                    msgToClient.setData(bundle);
                    try {
                        clientMessenger.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public MessengerServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return serviceMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceMessenger = new Messenger(mHandler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clientMessenger = null;
    }


}
