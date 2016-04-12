package com.yihukurama.bluetoothclient;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yihukurama.bluetoothclient.controler.bluetooth.BluetoothCS;
import com.yihukurama.bluetoothclient.controler.bluetooth.BluetoothCallBack;
import com.yihukurama.bluetoothclient.controler.bluetooth.BluetoothManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    BluetoothManager bluetoothManager;
    BluetoothCallBack blueCallback;
    Button btnSend;
    Button btnConnect;
    BluetoothDevice device;

    BluetoothCS bcs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepare();
        initView();
        initData();
    }

    private void initView() {
        btnSend = (Button)findViewById(R.id.send);
        btnSend.setOnClickListener(this);

        btnConnect = (Button)findViewById(R.id.connect);
        btnConnect.setOnClickListener(this);
    }

    private void initData() {
        blueCallback = new BluetoothCallBack();
        bluetoothManager = BluetoothManager.getInstance(blueCallback);
        bcs = new BluetoothCS("1C:87:2C:9D:2A:38",LinkDetectedHandler);


    }

    private void prepare() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                String cmd = "Hello World!";
                bcs.sendMessageHandle(cmd);
                break;
            case R.id.connect:
                bcs.startClient();
                break;
        }
    }

    private Handler LinkDetectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1)//客户端
            {
                String mes = (String)msg.obj;
                Log.i("bluetooth", "client" + mes);
            }
            else//服务器端
            {
                String remes = (String)msg.obj;
                Log.i("bluetooth","server"+remes);
            }
        }

    };

}
