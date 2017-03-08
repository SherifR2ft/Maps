package com.example.sherifr2ft.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    public Polyline SCU_Boundary ;

    //  SCU latitude and longitude coordinates
    LatLng SCU_Location = new LatLng(30.623109, 32.2729409);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //make Zoom (In/Out) Buttons appear
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //  SCU latitude and longitude coordinates
        //  LatLng SCU_Location = new LatLng(30.623109, 32.2729409);

        moveCameraPosition(SCU_Location);
        //Put SCU Boundary on Google map
        drawSCU_Boundary();
        // put func at dynamic zoom level detected
        handleZoom_GroundOverlay_Boundary();
// handle Zoom level between roundOverlay and Boundary when << Camera change >>
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {


                handleZoom_GroundOverlay_Boundary();

            }
        });


        //After Click on GroundOverlay
        // Zoom in SCU and remove GroundOverlay
        mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                moveCameraIn();
                /*need twice click to remove GroundOverlay
                  groundOverlay.remove(); */
                //cleared the complete set of overlays using
                mMap.clear();


                drawSCU_Boundary();


            }

        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sherifr2ft.maps/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sherifr2ft.maps/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /* Draw Boundary of SCU
          get latitude and longitude coordinates of pointes
          https://developers.google.com/maps/documentation/utilities/polylineutility*/
    public void drawSCU_Boundary() {
         SCU_Boundary  = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(30.617601379370974, 32.25452899932861), new LatLng(30.615727045601805, 32.25506544113159))
                .add(new LatLng(30.62100832664296, 32.28353977203369), new LatLng(30.615727045601805, 32.25506544113159))
                .add(new LatLng(30.62100832664296, 32.28353977203369), new LatLng(30.623944298832274, 32.28301137685776))
                .add(new LatLng(30.623944298832274, 32.28301137685776), new LatLng(30.627055593547315, 32.28206992149353))
                .add(new LatLng(30.627055593547315, 32.28206992149353), new LatLng(30.62797880715714, 32.280235290527344))
                .add(new LatLng(30.62797880715714, 32.280235290527344), new LatLng(30.62765568, 32.27997779))
                .add(new LatLng(30.62765568, 32.27997779), new LatLng(30.62802, 32.27558))
                .add(new LatLng(30.62802, 32.27558), new LatLng(30.62801, 32.27007))
                .add(new LatLng(30.62801, 32.27007), new LatLng(30.62832, 32.26871))
                .add(new LatLng(30.62832, 32.26871), new LatLng(30.62775, 32.26836))
                .add(new LatLng(30.62775, 32.26836), new LatLng(30.62778, 32.26513))
                .add(new LatLng(30.62778, 32.26513), new LatLng(30.62457, 32.26561))
                .add(new LatLng(30.62457, 32.26561), new LatLng(30.62108, 32.26666))
                .add(new LatLng(30.62108, 32.26666), new LatLng(30.61874, 32.25556))
                .add(new LatLng(30.61874, 32.25556), new LatLng(30.617601379370974, 32.25452899932861))
                .visible(true)
                .width(5)
                .color(Color.RED));}
        // .add(new LatLng(,),new LatLng(,))

//        //hide Boundary of SCU
        public void hideSCU_Boundary() {
            SCU_Boundary.setVisible(false);
        }






    /* Start app Camera ..
    then move the camera to the place and Zoom =15f
     bearing : Direction that the camera is pointing in, in degrees clockwise from north.
     tilt : The angle, in degrees, of the camera angle from the nadir (directly facing the Earth).".tilt(65.5f)"*/
    public void moveCameraPosition(LatLng location) {

        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(location)
                .bearing(270f).zoom(14f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
    }

    // move camera to focus on SCU more zoom in
    public void moveCameraIn() {

        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(SCU_Location)
                .bearing(270f).zoom(15f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
    }


    // Add an overlay to the map, retaining a handle to the GroundOverlay object.
    public void add_or_remove_GroundOverlay(LatLng location, Boolean condition) {
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .clickable(true)
                .transparency(.7f)


                .image(BitmapDescriptorFactory.fromResource(R.drawable.logo))
                        //position(LatLng location, float width, float height)
                .position(location, 2500f, 2500f);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(newarkMap);
        if (condition == Boolean.FALSE) {
            // remove a ground overlay
            imageOverlay.remove();

        } else {
            mMap.addGroundOverlay(newarkMap);
        }


    }

    public void handleZoom_GroundOverlay_Boundary() {


        float zoom = mMap.getCameraPosition().zoom;
        if (zoom < 15f) {
            add_or_remove_GroundOverlay(SCU_Location, Boolean.TRUE);

            if(
                    SCU_Boundary.isVisible()) {
                hideSCU_Boundary();
            }

        } else {
            add_or_remove_GroundOverlay(SCU_Location, Boolean.FALSE);
            mMap.clear();
            drawSCU_Boundary();

        }
    }


}
