package com.victor.ipc.model;

import android.util.Log;

import com.victor.ipc.thread.RecevieThread;
import com.victor.ipc.thread.SendNameThread;
import com.victor.ipc.thread.SendThread;
import com.victor.ipc.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by victor on 2017/3/16.
 */
public class IPCSocketServer extends Thread {
    private String TAG = "IPCSocketServer";

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(1990);
            Log.e(TAG,"server already start(服务器已启动)...." + NetworkUtil.getHostIP() + ":1990");

            SendThread st = new SendThread();
            st.start();
            SendNameThread sn = new SendNameThread();
            sn.start();
            while (true) {
                Socket socket = server.accept();
                RecevieThread rt = new RecevieThread(socket);
                rt.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
