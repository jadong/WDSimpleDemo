package com.dong.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.server.CommandServer;

/**
 * Created by zengwendong on 17/3/17.
 */
public class MessageActivity extends BaseActivity implements CommandServer.HandlerMessageListener {

    private TextView tv_message;
    private CommandServer commandServer;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = msg.obj != null ? msg.obj.toString() : "null";
            tv_message.append(message + "/n");
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate() {

        tv_message = (TextView) findViewById(R.id.tv_message);

        commandServer = new CommandServer(8088);
        commandServer.start();
    }

    @Override
    public void receiveMessage(String message) {
        handler.obtainMessage(0, message).sendToTarget();
    }
}
