package com.sdu.andrumbrella;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView errorMessage;
    private EditText countryCode;
    private EditText cityName;
    private Switch metricSwitch;
    private Button searchButton;
    private Geocoder geocoder;
    private Button locationButton;
    private LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryCode = findViewById(R.id.countryCode);
        cityName = findViewById(R.id.cityName);
        metricSwitch = findViewById(R.id.metric);
        searchButton = findViewById(R.id.search);
        errorMessage = findViewById(R.id.errorMessage);
        locationButton = findViewById(R.id.location);

        countryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    hideErrorMessage();
                    countryCode.setHint("");
                } else {
                    countryCode.setHint("Country Code");
                }
            }
        });

        cityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    hideErrorMessage();
                    cityName.setHint("");
                } else {
                    cityName.setHint("City");
                }
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideErrorMessage();
                if (!countryCode.getText().toString().equals("") && !cityName.getText().toString().equals("")) {
                    Intent startSearch = new Intent(MainActivity.this, UpcomingDays.class);
                    startSearch.putExtra("COUNTRY_CODE", countryCode.getText().toString());
                    startSearch.putExtra("CITY_NAME", cityName.getText().toString());
                    startSearch.putExtra("SWITCH_STATE", metricSwitch.isChecked());
                    startActivity(startSearch);
                } else {
                    showErrorMessage();
                }
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            }
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                String[] data = currentLocation(location.getLatitude(), location.getLongitude());
                cityName.setText(data[0]);
                countryCode.setText(data[1]);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Location not Found, make sure you have your GPS turned On", Toast.LENGTH_SHORT).show();
            }
        }
    }
    });

}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            String[] data = currentLocation(location.getLatitude(), location.getLongitude());
                            cityName.setText(data[0]);
                            countryCode.setText(data[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Location not Found, make sure you have your GPS turned On", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }
    }

    public String[] currentLocation(double lat, double lon) {
        String[] loc = new String[2];

        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                loc[0] = addressList.get(0).getLocality();
                loc[1] = addressList.get(0).getCountryCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loc;
    }


    public void showErrorMessage() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage() {
        errorMessage.setVisibility(View.INVISIBLE);
    }
}
