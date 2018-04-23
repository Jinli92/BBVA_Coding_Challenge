package b12app.vyom.com.bbvacodingchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import b12app.vyom.com.bbvacodingchallenge.R;

public class DetailsActivity extends AppCompatActivity {

    TextView tvName,tvAddress,tvLatitude,tvLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        tvAddress.setText(intent.getStringExtra("formatted_address"));
        tvLatitude.setText("Latitude "+String.valueOf(intent.getDoubleExtra("lat",0)));
        tvLongitude.setText("Longitude "+String.valueOf(intent.getDoubleExtra("lng",0)));

    }
}
