package me.synology.wookoo.shuttle;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class BluetoothTestActivity extends AppCompatActivity {
    private BluetoothSPP bt;//블루투스 객체
    String Address= "98:DA:60:01:47:D2";
    TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);
        log = findViewById(R.id.log);
        log.setText("탑승기록\n");
        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "차량과 연결되었습니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceDisconnected() {

            }

            @Override
            public void onDeviceConnectionFailed() {

            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                message = message.trim();
                String[] datas = message.split("/");
                String child = datas[0];
                String method = datas[1];
                Log.d("수신 메시지",message);
                if(child.equals("8953ca8e")){
                    child = "한정우";
                }
                else if (child.equals("a99a258d")){
                    child = "윤재욱";
                }
                else if (child.equals("696bd8d")){
                    child = "김기준";
                }
                else if (child.equals("d9b1fb8d")){
                    child = "김도현";
                }
                if(method.equals("on")){
                    method = "탑승";
                }else{
                    method = "하차";
                }
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = dateFormat.format(date);


                String logs = (log.getText().toString())+"\n"+child +" "+ method + " " + getTime;

                log.setText(logs);
                Toast.makeText(BluetoothTestActivity.this,child +"어린이가 " + method +" 했습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
            bt.connect(Address);
        }


    }
}