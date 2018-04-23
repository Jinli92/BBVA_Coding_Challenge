package b12app.vyom.com.bbvacodingchallenge.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.bbvacodingchallenge.R;
import b12app.vyom.com.bbvacodingchallenge.model.NearbyLocation;
import b12app.vyom.com.bbvacodingchallenge.network.ParseUrl;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "location update";
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private Double myLat, myLng;
    private Location currentLocation;
    private  SupportMapFragment mapFragment;
    private RequestQueue mQueue;
    private List<NearbyLocation> resultList;
    int count,c;
    private ToggleButton toggleButton;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setSupportActionBar(toolbar);
        toggleButton = toolbar.findViewById(R.id.toggle);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Toast.makeText(MapsActivity.this,"checked",Toast.LENGTH_SHORT).show();
                    Intent listIntent = new Intent(MapsActivity.this, ListActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("location_list", (ArrayList<? extends Parcelable>) resultList);
                    listIntent.putExtras(bundle);
                    startActivity(listIntent);

                } else {


                }
            }
        });
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        fusedLocationProviderApi = LocationServices.FusedLocationApi;

        mQueue = Volley.newRequestQueue(this);

        resultList = new ArrayList<>();



        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);



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

        // myLocationUpdate();
        this.mMap = googleMap;



        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(myLat, myLng);
        for(count = 0; count<resultList.size();count++ ){

            LatLng bbva = new LatLng(resultList.get(count).getLocation_lat(),resultList.get(count).getLocation_lng());
            Log.i(TAG, "onMapReady: bbva: "+bbva.latitude);
            mMap.addMarker(new MarkerOptions().position(bbva)
                    .title(resultList.get(count)
                            .getName()))
                    .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_bank_final));



        }



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i(TAG, "marker lat and lng: "+marker.getPosition().latitude);
                for(c = 0 ;c<resultList.size();c++){
                    if(marker.getPosition().latitude==resultList.get(c).getLocation_lat()
                            && marker.getPosition().longitude==resultList.get(c).getLocation_lng())
                    {
                        Intent intent = new Intent(MapsActivity.this, DetailsActivity.class);
                        intent.putExtra("name", resultList.get(c).getName());
                        intent.putExtra("formatted_address", resultList.get(c).getFormatted_address());
                        intent.putExtra("lat", resultList.get(c).getLocation_lat());
                        intent.putExtra("lng", resultList.get(c).getLocation_lng());
                        startActivity(intent);
                        Log.i(TAG, "onInfoWindowClick: " + intent.getStringExtra("formatted_address"));
                    }
                }
            }
        });

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("current location")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,9));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        myLocationUpdate();
    }

    private void myLocationUpdate() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;

        }
        else{

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);


        }
    }


    @Override
    public void onLocationChanged(Location location) {


        currentLocation = location;
        myLat = currentLocation.getLatitude();
        myLng = currentLocation.getLongitude();
        Log.i(TAG, "onLocationChanged: "+myLat+" "+myLng);
        ParseUrl parseUrl = new ParseUrl(mQueue);
        parseUrl.parseUrl(resultList,myLat,myLng);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
