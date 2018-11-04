package com.example.user.ssiapp;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Login extends AppCompatActivity {
    java.util.Date tgl = new java.util.Date();
    private SimpleDateFormat formattgl = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private String tanggal = formattgl.format(tgl);

    private EditText userName;
    private EditText password;
    private String stringNama;
    private String stringNpp;
    private Spinner spinnerSentra;
    private String stringLat;
    private String stringLng;
    private String stringLogin;
    private String stringLogout;
    private String stringStatus;
    private String stringTid;
    private String stringLatTidProb;
    private String stringLonTidProb;
    private String stringResponProb;

    private String stringSentra = "";

    String user, pass, cpc;


    //-------------
    private FusedLocationProviderClient client;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ssi);

        client = LocationServices.getFusedLocationProviderClient(this);

        requestPermisi();

        userName = (EditText) findViewById(R.id.edittext_username);
        password = (EditText) findViewById(R.id.edittext_password);
        spinnerSentra = (Spinner) findViewById(R.id.label_spinner_sentra);
        stringNama = "";
        stringNpp = "";
        stringLogin = "";
        stringLogout = ("-");
        stringStatus = ("AKTIF");
        stringTid = ("");
        stringLatTidProb = ("-");
        stringLonTidProb = ("-");
        stringResponProb = ("-");


        //SPINNER SENTRA
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array_sentra, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerSentra != null) {
            spinnerSentra.setAdapter(adapter);
        }

        spinnerSentra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stringSentra = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getLatLng() {
        try {
            checkLocationPermission();
            client.getLastLocation().addOnSuccessListener(Login.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        stringLat = String.valueOf(latLng.latitude);
                        stringLng = String.valueOf(latLng.longitude);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkLocationPermission() {
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

    public void loginMasuk(View view) {
        if (userName.length() == 0 && password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Masukan Username dan Password", Toast.LENGTH_LONG).show();
            return;
        } else if (userName.length() == 0) {

        } else if (stringSentra.equals("Pilih")) {
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Sentra", Toast.LENGTH_LONG).show();
            return;
        } else if (stringLat == null) {
            confirmUpdateEmployee();
            return;
        } else {
            //id = konfigurasiAktivitas.TAG_ID.toString();
            user = userName.getText().toString();
            pass = password.getText().toString();
            cpc = stringSentra;

            login(user, pass, cpc);
        }
    }


    private void confirmUpdateEmployee() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Lokasi Perangkat Kamu Sudah Aktif");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        getLatLng();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        return;
                    }
                });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void requestPermisi() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    }

    private void login(final String user, String pass, final String cpc) {
        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                //super.onPostExecute(s);
                String string = s.trim();
                loadingDialog.dismiss();
                if (string.equalsIgnoreCase("Sukses")) {
                    final String emp_id = konfigurasiAktivitas.TAG_ID;

                    Intent intent = new Intent(Login.this, MapsActivity.class);
                    //Intent intent = new Intent(Login.this, ListKelolaan.class);
                    intent.putExtra(konfigurasiAktivitas.EMP_ID, emp_id);
                    startActivity(intent);
                    addEmployee();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password or Sentra", Toast.LENGTH_LONG).show();
                    bersih();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                String user = strings[0];
                String pass = strings[1];
                String cpc = strings[2];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user", user));
                nameValuePairs.add(new BasicNameValuePair("pass", pass));
                nameValuePairs.add(new BasicNameValuePair("sentra", cpc));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://localhos/Android/selectLogin.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(user, pass, cpc);
    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee() {
        String nolJam = "", nolMenit = "", nolDetik = "";

        java.util.Date dateTime = new java.util.Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if (nilai_jam <= 9) nolJam = "0";
        if (nilai_menit <= 9) nolMenit = "0";
        if (nilai_detik <= 9) nolDetik = "0";

        String jam = nolJam + Integer.toString(nilai_jam);
        String menit = nolMenit + Integer.toString(nilai_menit);
        String detik = nolDetik + Integer.toString(nilai_detik);

        stringLogin = tanggal + " " + jam + ":" + menit + ":" + detik;

        final String nama = userName.getText().toString().toUpperCase().trim();
        final String npp = password.getText().toString().trim();
        final String kl = stringSentra;
        final String lat = stringLat;
        final String lon = stringLng;
        final String login = stringLogin;
        final String logout = stringLogout;
        final String status = stringStatus;
        final String tid = stringTid;
        final String latTidProb = stringLat;
        final String lonTidProb = stringLng;
        final String responProb = stringResponProb;

        class AddEmployee extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(Login.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(Login.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasiAktivitas.KEY_EMP_NAMA, nama);
                params.put(konfigurasiAktivitas.KEY_EMP_NPP, npp);
                params.put(konfigurasiAktivitas.KEY_EMP_SENTRA, kl);
                params.put(konfigurasiAktivitas.KEY_EMP_LAT, lat);
                params.put(konfigurasiAktivitas.KEY_EMP_LON, lon);
                params.put(konfigurasiAktivitas.KEY_EMP_LOGIN, login);
                params.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, logout);
                params.put(konfigurasiAktivitas.KEY_EMP_STATUS, status);
                params.put(konfigurasiAktivitas.KEY_EMP_TID, tid);
                params.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, latTidProb);
                params.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, lonTidProb);
                params.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, responProb);

                RequestHandler rh = new RequestHandler();
                String stringres = rh.sendPostRequest(konfigurasiAktivitas.URL_ADD, params);
                return stringres;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void bersih() {
        userName.setText("");
        password.setText("");
        stringSentra.equals("Sentra");
    }
}
