package com.dong.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.dong.server.CommandServer;
import com.dong.util.AppUtils;

/**
 * Created by zengwendong on 17/3/31.
 */
public class AssistService extends Service implements CommandServer.HandlerMessageListener {

    private CommandServer commandServer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            AppUtils.toast(msg.obj.toString());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (commandServer == null || !commandServer.isRunning()) {
            commandServer = new CommandServer(8088);
            commandServer.setHandlerMessageListener(this);
            commandServer.start();
            sendMessage("ClientServer启动成功");
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (commandServer != null) {
            commandServer.stop();
        }
    }

    @Override
    public void receiveMessage(String message) {
        sendMessage(message);
    }

    private void sendMessage(String message) {
        handler.obtainMessage(0, message).sendToTarget();
    }
}
