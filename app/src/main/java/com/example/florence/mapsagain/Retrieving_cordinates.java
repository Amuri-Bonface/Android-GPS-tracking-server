package com.example.florence.mapsagain;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.florence.mapsagain.R.id.map;

//public class MainActivity  extends AppCompatActivity {
public class Retrieving_cordinates extends FragmentActivity implements OnMapReadyCallback {
    private static final String URL = "http://boggy-dispatcher.000webhostapp.com/nannie_track/marketers_retrieve.php";

    private TextView t, tt;
    private LocationManager locationManager;
    private LocationListener listener;
    private GoogleMap mMap;
    private EditText address;
    private Double xx, yy;
    private String y, token;
    private Marker currentLocationMarker;
    private String pphone;
    private String ffname;
    private String tarehe;
    private String activate;

    private String ssname;
    private String owner_email = null;
    private TextView mTextField;
    private CountDownTimer myCountDownTimer;

    @Override
    public void onPause(){
        super.onPause();
        myCountDownTimer.cancel();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.marketers_retrieve);
             // Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                //startService(i);
            Button b=(Button)findViewById(R.id.button3);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // String url3 = "https://www.4shared.com/s/fKA_ujYoCca";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                   // i.setData(Uri.parse(url3));
                    startActivity(i);


                }
            });
            Button client=(Button)findViewById(R.id.button4);
            client.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url3 = "https://www.4shared.com/s/fqo_nSXsuca";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url3));
                    startActivity(i);

                }
            });

            runtime_permissions();
            gettingCordinates();
            }
        catch(Exception e){
                //  Toast.makeText(getActivity(), "Google Maps problem", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onMapReady (GoogleMap googleMap){

            mMap = googleMap;

            Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(Retrieving_cordinates.this).getAccounts();
            for (Account account : accounts) {
                if (gmailPattern.matcher(account.name).matches()) {
                    owner_email = account.name;
                }
            }

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {


                        jsonObject = new JSONObject(response);
                        String lat = jsonObject.getString("latitude");
                        String lon = jsonObject.getString("longitude");
                        pphone = jsonObject.getString("phone");
                        ffname = jsonObject.getString("fname");
                        ssname = jsonObject.getString("sname");
                        tarehe = jsonObject.getString("tarehe");
                        activate= jsonObject.getString("activate");
                        tt=(TextView)findViewById(R.id.textView2);
                        tt.setText(activate);
                        //convert string latitude to double
                        Double latitude1 = Double.parseDouble(lat);
                        Double longitude1 = Double.parseDouble(lon);

                        LatLng buda = new LatLng(latitude1, longitude1);
                        CameraPosition position = CameraPosition.builder()
                                .target(buda)
                                .zoom(15)
                                .bearing(0)
                                .tilt(0)
                                .build();
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mMap.setTrafficEnabled(true);
                        mMap.setBuildingsEnabled(true);
                        mMap.getUiSettings().setZoomControlsEnabled(true);


                        //lets add updated marker
                        mMap.addMarker(new MarkerOptions().position(buda).title(ffname + " " + ssname + ": " + pphone + " " + tarehe));
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("owner_email", owner_email);
                    return hashMap;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest1);
        }

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent myIntent = new Intent(Retrieving_cordinates.this, Retrieving_cordinates.class);
                        startActivity(myIntent);
                        myCountDownTimer.cancel();
                        finish();
                        return true;
                    case R.id.navigation_dashboard:
                        Intent i = new Intent(Retrieving_cordinates.this, Register.class);
                        startActivity(i);
                        myCountDownTimer.cancel();
                        finish();
                        return true;
                    case R.id.navigation_personal:
                        Intent x = new Intent(Retrieving_cordinates.this,Retrieve_call_sms.class);
                        startActivity(x);
                        myCountDownTimer.cancel();
                        finish();
                   }
                return false;
           }

        };

        public void gettingCordinates() {
            try{

            mTextField = (TextView) findViewById(R.id.mTextField);
            myCountDownTimer = new CountDownTimer(1000000, 20000) {

                public void onTick(long millisUntilFinished) {

                    Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
                    Account[] accounts = AccountManager.get(Retrieving_cordinates.this).getAccounts();
                    for (Account account : accounts) {
                        if (gmailPattern.matcher(account.name).matches()) {
                            owner_email = account.name;
                        }
                    }

                    //navigation
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                    //Finding the map on the Activity
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(map);
                    mapFragment.getMapAsync(Retrieving_cordinates.this);


                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {


                                jsonObject = new JSONObject(response);
                                String lat = jsonObject.getString("latitude");
                                String lon = jsonObject.getString("longitude");
                                pphone = jsonObject.getString("phone");
                                ffname = jsonObject.getString("fname");
                                ssname = jsonObject.getString("sname");
                                tarehe = jsonObject.getString("tarehe");
                                activate= jsonObject.getString("activate");
                                tt=(TextView)findViewById(R.id.textView2);
                                tt.setText(activate);

                                //convert string latitude to double
                                Double latitude1 = Double.parseDouble(lat);
                                Double longitude1 = Double.parseDouble(lon);

                                LatLng buda = new LatLng(36.86485, longitude1);
                                CameraPosition position = CameraPosition.builder()
                                        .target(buda)
                                        .zoom(15)
                                        .bearing(0)
                                        .tilt(0)
                                        .build();

                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                mMap.setTrafficEnabled(true);
                                mMap.setBuildingsEnabled(true);
                                mMap.getUiSettings().setZoomControlsEnabled(true);

                                //lets add updated marker
                                mMap.addMarker(new MarkerOptions().position(buda).title(ffname + " " + ssname + ": " + pphone + " " + tarehe));
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("owner_email", owner_email);
                            return hashMap;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(stringRequest1);

                    mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    mTextField.setText("done!");
                   gettingCordinates();
                }
            }.start();
        }catch(Exception e){}
        }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},100);
            return true;
        }

        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                runtime_permissions();

            } else {
                runtime_permissions();
            }
        }
    }
    }


