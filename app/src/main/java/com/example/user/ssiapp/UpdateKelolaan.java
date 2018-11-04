package com.example.user.ssiapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateKelolaan extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewTid;
    private EditText editTextLokasi;
    private EditText editTextSentra;
    private EditText editTextWilayah;
    private EditText editTextKodeUker;
    private EditText editTextMerkMesin;
    private EditText editTextDenom;
    private EditText editTextPagu;
    private EditText editTextSerialNumber;
    private EditText editTextJarkom;
    private EditText editTextDB;
    private EditText editTextServiceHour;
    private EditText editTextStatusAtm;
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private Button buttonUpdate;
    private Button buttonLatLng;

    private String stringid;

    private FusedLocationProviderClient client;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String stringLat;
    private String stringLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kelolaan);

        getSupportActionBar().hide();

        client = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        stringid = intent.getStringExtra(konfigurasiKelolaan.EMP_ID);

        setupControls();

        buttonUpdate.setOnClickListener(this);
        buttonLatLng.setOnClickListener(this);
        getEmployee();
    }

    private void setupControls() {
        textViewTid = (TextView) findViewById(R.id.tvTid);
        editTextLokasi = (EditText) findViewById(R.id.etLokasi);
        editTextSentra = (EditText) findViewById(R.id.etSentra);
        editTextWilayah = (EditText) findViewById(R.id.etWilayah);
        editTextKodeUker = (EditText) findViewById(R.id.etKodeUker);
        editTextMerkMesin = (EditText) findViewById(R.id.etMesin);
        editTextDenom = (EditText) findViewById(R.id.etDenom);
        editTextPagu = (EditText) findViewById(R.id.etPagu);
        editTextSerialNumber = (EditText) findViewById(R.id.etSerialNumber);
        editTextJarkom = (EditText) findViewById(R.id.etJarkom);
        editTextDB = (EditText) findViewById(R.id.etDb);
        editTextServiceHour = (EditText) findViewById(R.id.etServHour);
        editTextStatusAtm = (EditText) findViewById(R.id.etStatusAtm);
        textViewLatitude = (TextView) findViewById(R.id.tvLatitude);
        textViewLongitude = (TextView) findViewById(R.id.tvLongitude);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonLatLng = (Button) findViewById(R.id.buttonLatLng);
    }

    public void getLatLng(){
        try {
            checkLocationPermission();
            client.getLastLocation().addOnSuccessListener(UpdateKelolaan.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        stringLat = String.valueOf(latLng.latitude);
                        stringLng = String.valueOf(latLng.longitude);
                        textViewLatitude.setText(stringLat);
                        textViewLongitude.setText(stringLng);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(Tampil.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasiKelolaan.URL_GET_EMP, stringid);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }


    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasiKelolaan.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String tid = c.getString(konfigurasiKelolaan.TAG_TID);
            String lokasi = c.getString(konfigurasiKelolaan.TAG_LOKASI);
            String cpc = c.getString(konfigurasiKelolaan.TAG_CPC);
            String wilayah = c.getString(konfigurasiKelolaan.TAG_WILAYAH);
            String kodeUker = c.getString(konfigurasiKelolaan.TAG_KODE_UKER);
            String merkMesin = c.getString(konfigurasiKelolaan.TAG_MERK_MESIN);
            String denom = c.getString(konfigurasiKelolaan.TAG_DENOM);
            String pagu = c.getString(konfigurasiKelolaan.TAG_PAGU);
            String snMesin = c.getString(konfigurasiKelolaan.TAG_SN_MESIN);
            String jarkom = c.getString(konfigurasiKelolaan.TAG_JARKOM);
            String db = c.getString(konfigurasiKelolaan.TAG_DB);
            String servHour = c.getString(konfigurasiKelolaan.TAG_SERV_HOUR);
            String garansi = c.getString(konfigurasiKelolaan.TAG_GARANSI);
            String lat = c.getString(konfigurasiKelolaan.TAG_LAT);
            String lon = c.getString(konfigurasiKelolaan.TAG_LON);

            textViewTid.setText(tid);
            editTextLokasi.setText(lokasi);
            editTextSentra.setText(cpc);
            editTextWilayah.setText(wilayah);
            editTextKodeUker.setText(kodeUker);
            editTextMerkMesin.setText(merkMesin);
            editTextDenom.setText(denom);
            editTextPagu.setText(pagu);
            editTextSerialNumber.setText(snMesin);
            editTextJarkom.setText(jarkom);
            editTextDB.setText(db);
            editTextServiceHour.setText(servHour);
            editTextStatusAtm.setText(garansi);
            textViewLatitude.setText(lat);
            textViewLongitude.setText(lon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateEmployee() {
        final String tid = textViewTid.getText().toString().trim();
        final String lokasi = editTextLokasi.getText().toString().trim().toUpperCase();
        final String sentra = editTextSentra.getText().toString().trim().toUpperCase();
        final String wilayah = editTextWilayah.getText().toString().trim().toUpperCase();
        final String kodeUker = editTextKodeUker.getText().toString().trim();
        final String merkMesin = editTextMerkMesin.getText().toString().trim().toUpperCase();
        final String denom = editTextDenom.getText().toString().trim();
        final String pagu = editTextPagu.getText().toString().trim();
        final String serialNumber = editTextSerialNumber.getText().toString().trim().toUpperCase();
        final String jarkom = editTextJarkom.getText().toString().trim().toUpperCase();
        final String db = editTextDB.getText().toString().trim().toUpperCase();
        final String servHour = editTextServiceHour.getText().toString().trim().toUpperCase();
        final String garansi = editTextStatusAtm.getText().toString().trim().toUpperCase();
        final String lat = textViewLatitude.getText().toString().trim();
        final String lon = textViewLongitude.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            //ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(Tampil.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(UpdateKelolaan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiKelolaan.KEY_EMP_ID, stringid);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_TID, tid);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_LOKASI, lokasi);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_CPC, sentra);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_WILAYAH, wilayah);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_KODE_UKER, kodeUker);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_MERK_MESIN, merkMesin);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_DENOM, denom);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_PAGU, pagu);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_SN_MESIN, serialNumber);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_JARKOM, jarkom);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_DB, db);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_SERV_HOUR, servHour);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_GARANSI, garansi);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_LAT, lat);
                hashMap.put(konfigurasiKelolaan.KEY_EMP_LON, lon);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiKelolaan.URL_UPDATE_EMP, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        //ue.execute(bitmap);
    }

    private void confirmUpdateEmployee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin ?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateEmployee();
                        //startActivity(new Intent(Tampil.this, MenuUtama.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == buttonLatLng){
            getLatLng();
        } else if (v == buttonUpdate) {
            confirmUpdateEmployee();
        }
    }
}
