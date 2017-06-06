package gpspractice.graduateproject.com.daumapitest;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends Activity implements MapView.MapViewEventListener, MapView.CurrentLocationEventListener {

    MapView mMapView;
    private MapView mapview;
    private android.widget.Button btnMyPosition;
    private Button btnMarkerToMyPos;
    private android.widget.EditText edPointX;
    private android.widget.EditText edPointY;
    private Button btnChangePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnChangePosition = (Button) findViewById(R.id.changePosition);
        this.edPointY = (EditText) findViewById(R.id.edPointY);
        this.edPointX = (EditText) findViewById(R.id.edPointX);
        this.btnMyPosition = (Button) findViewById(R.id.myPosition);
        this.mapview = (MapView) findViewById(R.id.map_view);
        this.btnMarkerToMyPos = (Button) findViewById(R.id.markerToMyPosition);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(getResources().getString(R.string.APIKEY));
//        ViewGroup mapGroup = (ViewGroup) findViewById(R.id.map_view);
//        mapGroup.addView(mMapView);
        mMapView.setMapViewEventListener(this);
        mMapView.setCurrentLocationEventListener(this);

        btnMyPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "슈우웅~ 나는 어디?!", Toast.LENGTH_SHORT).show();

                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

                edPointX.setText(String.valueOf(mMapView.getMapCenterPoint().getMapPointGeoCoord().latitude));
                edPointY.setText(String.valueOf(mMapView.getMapCenterPoint().getMapPointGeoCoord().longitude));
            }
        });

//        btnMarkerToMyPos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MapPOIItem marker = new MapPOIItem();
//                marker.setItemName("First");
//                marker.setTag(0);
//                marker.setMapPoint(mMapView.);
//                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//
//                mMapView.setShowCurrentLocationMarker(true);
//
//                mMapView.addPOIItem(marker);
//            }
//        });

        btnChangePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "위도와 경도를 계산해서 원하는 곳으로....갔나요?", Toast.LENGTH_SHORT).show();
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                MapPoint mp = MapPoint.mapPointWithGeoCoord(Float.parseFloat(edPointX.getText().toString()), Float.parseFloat(edPointY.getText().toString()));
                mMapView.setMapCenterPoint(mp, true);
            }
        });

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        MapPoint mp = MapPoint.mapPointWithGeoCoord(37, 127);
        mapView.setMapCenterPoint(mp, true);

        mMapView.setMapTileMode(MapView.MapTileMode.HD2X);
        edPointX.setText(String.valueOf(mMapView.getMapCenterPoint().getMapPointGeoCoord().latitude));
        edPointY.setText(String.valueOf(mMapView.getMapCenterPoint().getMapPointGeoCoord().longitude));


    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        edPointX.setText(String.valueOf(mapPoint.getMapPointGeoCoord().latitude));
        edPointY.setText(String.valueOf(mapPoint.getMapPointGeoCoord().longitude));

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {



    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

        Toast.makeText(this, "한번만 누르면 마커가 찍혀요~", Toast.LENGTH_SHORT).show();

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Check");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);

        marker.setCustomImageResourceId(R.drawable.marker_cus);
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        marker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);

        mMapView.setShowCurrentLocationMarker(true);

        mMapView.addPOIItem(marker);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        Toast.makeText(this, "두번 누르셨군요? ㅎㅎ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        Toast.makeText(this, "오~래 누르셨군요?", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
//        Toast.makeText(this, "드래그 시~작!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
//        Toast.makeText(this, "드래그 끝!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Toast.makeText(this, "완료!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, final MapPoint mapPoint, float v) {

        btnMarkerToMyPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "내가 있는 곳이 바로 내 구역이지!!", Toast.LENGTH_SHORT).show();

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName("First");
                marker.setTag(0);
                marker.setMapPoint(mapPoint);

                marker.setCustomImageResourceId(R.drawable.marker_cus);
                marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                marker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

//                mMapView.setShowCurrentLocationMarker(true);

                mMapView.addPOIItem(marker);
            }
        });

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
}