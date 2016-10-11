/*
 * Copyright (C) 2014 Akexorcist
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dong.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.base.SwipeBackLayout;
import com.dong.bluetooth.BluetoothSPP;
import com.dong.bluetooth.BluetoothState;
import com.dong.bluetooth.DeviceList;

public class TerminalActivity extends BaseActivity implements OnClickListener {
    BluetoothSPP bt;

    TextView tv_status, tv_read;
    EditText et_message;

    @Override
    protected int getContentView() {
        return R.layout.activity_terminal;
    }

    protected void onCreate() {
        tv_read = (TextView)findViewById(R.id.tv_read);
        tv_status = (TextView)findViewById(R.id.tv_status);
        et_message = (EditText)findViewById(R.id.et_message);
        findViewById(R.id.btn_devices_1).setOnClickListener(this);
        findViewById(R.id.btn_devices_2).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);

        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                tv_read.append(message + "\n");
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                tv_status.setText("Status : Not connect");
            }

            public void onDeviceConnectionFailed() {
                tv_status.setText("Status : Connection failed");
            }

            public void onDeviceConnected(String name, String address) {
                tv_status.setText("Status : Connected to " + name);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_send:
                if(bt.isServiceAvailable() && et_message.getText().length() != 0) {
                    bt.send(et_message.getText().toString(), true);
                    et_message.setText("");
                }
                break;
            case R.id.btn_devices_1:
                bt.setDeviceTarget(BluetoothState.DEVICE_ANDROID);
			/*
			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
    			bt.disconnect();*/
                Intent intent1 = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent1, BluetoothState.REQUEST_CONNECT_DEVICE);
                break;
            case R.id.btn_devices_2:
                bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);
			/*
			if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
    			bt.disconnect();*/
                Intent intent2 = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent2, BluetoothState.REQUEST_CONNECT_DEVICE);
                break;
            default:
                break;
        }

    }
}
