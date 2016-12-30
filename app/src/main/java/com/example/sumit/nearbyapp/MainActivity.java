package com.example.sumit.nearbyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String latitude,longitude;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GPSTracker gps = new GPSTracker(this);
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading . . . ");




        if(gps.canGetLocation()){

            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

            data_list  = new ArrayList<>();
            load_data_from_server();
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter = new CustomAdapter(this,data_list);
            recyclerView.setAdapter(adapter);
        }

        else{

            gps.showSettingsAlert();

        }

    }
    private void load_data_from_server() {
        String url = "http://vga.ramstertech.com/loc/location.php?latitude="+latitude+"&longitude="+longitude+"";
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray jsonarray = new JSONArray(response);
                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String name = jsonobject.getString("name");
                                Double latitude = jsonobject.getDouble("latitude");
                                Double longitude = jsonobject.getDouble("longitude");
                                Double distance = jsonobject.getDouble("distance");

                               MyData data = new MyData(name.toString(),latitude,longitude,distance);

                               data_list.add(data);
                               adapter.notifyDataSetChanged();
                               pd.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // pd.hide();

                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
