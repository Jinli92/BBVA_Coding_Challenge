package b12app.vyom.com.bbvacodingchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.bbvacodingchallenge.R;
import b12app.vyom.com.bbvacodingchallenge.adapters.LocationListAdapter;
import b12app.vyom.com.bbvacodingchallenge.model.NearbyLocation;

public class ListActivity extends AppCompatActivity {

    private static final String TAG ="list tag";
    private double lat, lng;
    private List<NearbyLocation> locationList;
    private RequestQueue mQueue;
    private ListView listView;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.lvLocations);

        Intent intent = getIntent();
        locationList = new ArrayList<>();
        locationList = intent.getParcelableArrayListExtra("location_list");

        LocationListAdapter locationListAdapter = new LocationListAdapter(locationList,this);
        listView.setAdapter(locationListAdapter);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarList);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setSupportActionBar(toolbar);
        toggleButton = toolbar.findViewById(R.id.toggleList);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Intent mapIntent = new Intent(ListActivity.this, MapsActivity.class);

                    startActivity(mapIntent);

                } else {
                    Intent mapIntent = new Intent(ListActivity.this, ListActivity.class);

                    startActivity(mapIntent);


                }
            }
        });



        Log.i(TAG, "list activity: "+locationList);



    }
}
