package cu.lee.chosun.capstone;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GeoActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    MapFragment fragment;
    EditText editPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        editPlace = (EditText) findViewById(R.id.editPlace);
        //프래그먼트 객체 생성
        fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        // 구글맵을 비동기적인 방식 코딩
        fragment.getMapAsync(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            search(); //
            }
        });
    }
        public void search(){ //View v 기억
        String place = editPlace.getText().toString();
        Geocoder coder = new Geocoder(this);
        List<Address> list = null;
        try{
            list = coder.getFromLocationName(place, 1);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        Address addr = list.get(0);
        double lat = addr.getLatitude(); //위도
        double log = addr.getLongitude(); // 경도

            LatLng geoPoint = new LatLng(lat, log); // 좌표
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(geoPoint,15)); // 카메라 이동, 줌레벨 설정
            // 해당 좌표에 마커 설정
            MarkerOptions marker = new MarkerOptions();
            marker.position(geoPoint);
            marker.title(editPlace.getText().toString());
            map.addMarker(marker);
    }




    // 지도가 완성되면 호출
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); // 지도 종류
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true); // 현재 위치
        map.getUiSettings().setZoomControlsEnabled(true); // 검색
    }

}
