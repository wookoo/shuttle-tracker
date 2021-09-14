package me.synology.wookoo.shuttle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Marker marker;

    Button change_btn;

    EditText lat_text;
    EditText long_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        marker  = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lat = Double.parseDouble(String.valueOf(lat_text.getText()));
                double lon = Double.parseDouble(String.valueOf(long_text.getText()));
                Log.d("위도 경도 " , lat + " " + lon);
                marker.setPosition(new LatLng(lat,lon));
            }
        });


       // marker.setMap(naverMap);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    runOnUiThread(new Runnable() { //ui thread 에서 call 을 해야됨
                        @Override
                        public void run() {
                            marker.setPosition(new LatLng(37.57,126.978));
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {//콜백으로 돌려야

        marker.setMap(naverMap);


    }
}