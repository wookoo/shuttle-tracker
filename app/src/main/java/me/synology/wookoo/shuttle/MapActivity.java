package me.synology.wookoo.shuttle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Marker marker;

    Button change_btn;
    Button map_lock_btn;
    Button to_bus_btn;

    EditText lat_text;
    EditText long_text;
    NaverMap naverMap = null;
    boolean lock_map = false;
    double lat = 37.5670135;
    double lon = 126.9783740;
    //private ObjectInputStream in;
    //private ObjectOutputStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        mapFragment.getMapAsync(this); //항상 콜백으로 돌려야됨됨

        lat_text = findViewById(R.id.latitude_text);
        long_text = findViewById(R.id.longitude_text);
        change_btn = findViewById(R.id.chage_marker_btn);
        map_lock_btn = findViewById(R.id.button_lock_map);
        to_bus_btn = findViewById(R.id.button_to_bus);

        marker  = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_bus));

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lat = Double.parseDouble(String.valueOf(lat_text.getText()));
                lon = Double.parseDouble(String.valueOf(long_text.getText()));
                Log.d("위도 경도 " , lat + " " + lon);
                marker.setPosition(new LatLng(lat,lon));
                if(lock_map){
                    naverMap.moveCamera(CameraUpdate.scrollTo(new LatLng(lat,lon)));
                }


            }
        });

        map_lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lock_map){//지도 잠금된 경우
                    map_lock_btn.setText("지도 잠금");
                    lock_map = !lock_map;
                    Toast.makeText(MapActivity.this,"지도가 자동으로 움직이지 않습니다",Toast.LENGTH_SHORT).show();
                    

                }
                else{
                    map_lock_btn.setText("지도 잠금 해제");
                    lock_map = !lock_map;
                    Toast.makeText(MapActivity.this,"지도가 자동으로 움직입니다",Toast.LENGTH_SHORT).show();


                }


            }
        });

        to_bus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naverMap.moveCamera(CameraUpdate.scrollTo(new LatLng(lat,lon)));
                Toast.makeText(MapActivity.this,"지도중심이 버스 위치로 이동합니다",Toast.LENGTH_SHORT).show();

            }
        });


        /*



        // marker.setMap(naverMap);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.1.2",8787);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                while(true){
                    try {
                        String received = (String) in.readObject();
                        String[] temp = received.split(",");
                        Log.d("받아온 값",received);
                        if(naverMap != null){
                            double lat = Double.parseDouble(temp[0]);
                            double lon = Double.parseDouble(temp[1]);
                            Log.d("받아온 값",lat + " " + lon);
                            runOnUiThread(new Runnable() { //메인 스레드에서 돌려야됨
                                @Override
                                public void run() {
                                    marker.setPosition(new LatLng(lat,lon));
                                    naverMap.moveCamera(CameraUpdate.scrollTo(new LatLng(lat,lon)));
                                }
                            });

                        }



                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

         */



    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {//콜백으로 돌려야
        this.naverMap = naverMap;
        marker.setMap(naverMap);

    }
}