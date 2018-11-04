package com.example.user.ssiapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MapsActivity extends AbstractMapActivity implements OnMapReadyCallback, ListView.OnItemClickListener {

    java.util.Date tgl = new java.util.Date();
    private SimpleDateFormat formattgl = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private String tanggal = formattgl.format(tgl);

    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FusedLocationProviderClient client;

    private String JSON_STRING;

    LatLng posisi, latLngRespon;
    String id, title;

    CameraPosition cameraPosition;

    Timer t;
    TimerTask task;
    final Handler _handler = new Handler();


    //variabel update
    private String idAkti;
    private String stringNama;
    private String stringNpp;
    private String stringSentra;
    private String stringLat;
    private String stringLon;
    private String stringLagin;
    private String stringLogout;
    private String stringStatus;
    private String stringLatProb;
    private String stringLonProb;
    private String stringKeterangan;
    TextView mTidTujuan, mTidSnippet;

    //Control--
    Button btnOpenFolder, btnChat, btnRefresh, btnSiap;


    //action
    LinearLayout divAction, divFlmAction;
    TextView lblAction;
    EditText etDeskripsi, etVoltase, etGrounding;
    Spinner spinnerCctv, spinnerSuhuRuangan, spinnerAntiSkiming;
    String stringCctv = "";
    String stringSuhuRuangan = "";
    String stringAntiSkiming = "";
    Button btnSubmit;

    float mToLat, mToLng;
    PolylineOptions mPolyline;
    String TAG = "MapsActivity";

    //chat
    private ListView lvChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Intent intent = getIntent();
        idAkti = intent.getStringExtra(konfigurasiAktivitas.EMP_ID);


        client = LocationServices.getFusedLocationProviderClient(this);
        requestPermisi();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupControls();
        setupButtonClick();
        getEmployee();
        spinnerControls();

        //stopTimer();

        mTidTujuan.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_flm:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_rpl:
                startActivity(new Intent(this, MainActivityRpl.class));
                return true;
            case R.id.action_maps:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.action_refresh:
                startActivity(new Intent(this, MenuUtama.class));
                finish();
                //return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupControls() {
        divFlmAction = (LinearLayout) findViewById(R.id.divFlmAction);
        divAction = (LinearLayout) findViewById(R.id.divAction);
        divAction.setVisibility(View.GONE);
        lvChat = (ListView) findViewById(R.id.lvChat);
        lvChat.setOnItemClickListener(this);
        lvChat.setVisibility(View.GONE);
        lblAction = (TextView) findViewById(R.id.lblAction);
        spinnerCctv = (Spinner) findViewById(R.id.spCctv);
        spinnerSuhuRuangan = (Spinner) findViewById(R.id.spSuhuRuangan);
        spinnerAntiSkiming = (Spinner) findViewById(R.id.spAntiSkiming);
        etDeskripsi = (EditText) findViewById(R.id.etDeskripsi);
        etVoltase = (EditText) findViewById(R.id.etVoltase);
        etGrounding = (EditText) findViewById(R.id.etGrounding);
        mTidTujuan = (TextView) findViewById(R.id.tidTujuan);
        mTidSnippet = (TextView) findViewById(R.id.tidSnippet);
        btnOpenFolder = (Button) findViewById(R.id.btnOpenFolder);
        btnSiap = (Button) findViewById(R.id.btnSiap);
        btnRefresh = (Button) findViewById(R.id.btnRefesh);
        btnRefresh.setVisibility(View.GONE);
        btnChat = (Button) findViewById(R.id.btnChat);
        btnSubmit = (Button) findViewById(R.id.btSubmit);
        btnChat.setVisibility(View.GONE);
    }

    private void setupButtonClick() {
        btnSiap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTidTujuan.length() == 0) {
                    Toast.makeText(MapsActivity.this, "Silihkan Pilih TID", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    btnSiap.setVisibility(View.GONE);
                    btnRefresh.setVisibility(View.VISIBLE);
                    getLatLng();
                    updateEmployee();
                    lvChat.setVisibility(View.GONE);
                }
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRefresh.setVisibility(View.GONE);
                btnChat.setVisibility(View.VISIBLE);
                confirmUpdateEmployee();
                lvChat.setVisibility(View.GONE);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                report();
                lvChat.setVisibility(View.GONE);
            }
        });
        btnOpenFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFolder();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDeskripsi.length() == 0) {
                    Toast.makeText(MapsActivity.this, "Masukan Deskripsi Pekerjaan..", Toast.LENGTH_LONG).show();
                    return;
                } else if (etVoltase.length() == 0) {
                    Toast.makeText(MapsActivity.this, "Masukan Voltase..", Toast.LENGTH_LONG).show();
                    return;
                } else if (etGrounding.length() == 0) {
                    Toast.makeText(MapsActivity.this, "Masukan Grounding..", Toast.LENGTH_LONG).show();
                    return;
                } else if (stringCctv.equals("Cek CCTV")) {
                    Toast.makeText(MapsActivity.this, "Pilih Cek CCTV..", Toast.LENGTH_LONG).show();
                    return;
                } else if (stringSuhuRuangan.equals("Suhu Ruangan")) {
                    Toast.makeText(MapsActivity.this, "Pilih Suhu Ruangan..", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    updateEmployeeReportLokasiDone();
                    addEmployee();
                    divAction.setVisibility(View.GONE);
                    divFlmAction.setVisibility(View.VISIBLE);
                    btnSiap.setVisibility(View.VISIBLE);
                    btnChat.setVisibility(View.GONE);
                    btnRefresh.setVisibility(View.GONE);
                    lvChat.setVisibility(View.GONE);
                    mTidTujuan.setText("");
                    mTidSnippet.setText("");
                }
            }
        });
    }

    public void openFolder() {
        final CharSequence[] dialogitem = {"List Problem", "List Problem NT", "List FLM", "List Kelolaan", "Chat", "Kembali"};
        AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Pilihan");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), ListProblem.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent ii = new Intent(getApplicationContext(), ListProblemNT.class);
                        startActivity(ii);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Progres", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Intent iii = new Intent(getApplicationContext(), ListKelolaan.class);
                        startActivity(iii);
                        break;
                    case 4:
                        lvChat.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        break;
                }
            }
        });
        builderProb.create().show();
    }

    public void report() {
        final CharSequence[] dialogitem = {"Done", "Pending", "Kembali"};
        final AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Problem");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        divAction.setVisibility(View.VISIBLE);
                        divFlmAction.setVisibility(View.GONE);
                        break;
                    case 1:
                        reportPending();
                        break;
                    case 2:
                        //updateEmployeeReportLokasiSlm();
                        //addEmployee();
                        break;
                }
            }
        });
        builderProb.create().show();
    }

    public void reportPending() {
        final CharSequence[] dialogitem = {"SLM BRI", "SLM SSI", "Kembali"};
        final AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Pending Pengerjaan");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        reportPendingSLMBRI();
                        break;
                    case 1:
                        reportPendingSLMSSI();
                        break;
                    case 2:
                        report();
                        break;
                }
            }
        });
        builderProb.create().show();
    }

    public void reportPendingSLMSSI() {
        final CharSequence[] dialogitem = {"Part Non Garansi", "Kembali"};
        final AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Problem");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        updateEmployeeReportLokasiPending();
                        addEmployee();
                        break;
                    case 1:
                        reportPending();
                        break;
                }
            }
        });
        builderProb.create().show();
    }

    public void reportPendingSLMBRI() {
        final CharSequence[] dialogitem = {
                "Part Garansi",
                "Network",
                "Electrikal",
                "Kembali"
        };
        final AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Problem");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        updateEmployeeReportLokasiPending();
                        addEmployee();
                        break;
                    case 1:
                        updateEmployeeReportLokasiPending();
                        addEmployee();
                        break;
                    case 2:
                        updateEmployeeReportLokasiPending();
                        addEmployee();
                        break;
                    case 3:
                        reportPending();
                        break;
                }
            }
        });
        builderProb.create().show();
    }

    public void spinnerControls() {
        //SPINNER CCTV
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_cctv, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerCctv != null) {
            spinnerCctv.setAdapter(adapter);
        }

        spinnerCctv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stringCctv = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SPINNER SUHU RUANGAN
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this, R.array.labels_suhu_ruangan, android.R.layout.simple_spinner_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerSuhuRuangan != null) {
            spinnerSuhuRuangan.setAdapter(adapterS);
        }

        spinnerSuhuRuangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stringSuhuRuangan = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SPINNER ANTI SKIMING
        ArrayAdapter<CharSequence> adapterSA = ArrayAdapter.createFromResource(this, R.array.labels_antiSkiming, android.R.layout.simple_spinner_item);
        adapterSA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerAntiSkiming != null) {
            spinnerAntiSkiming.setAdapter(adapterSA);
        }

        spinnerAntiSkiming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stringAntiSkiming = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        posisi = new LatLng(-6.310650, 106.927482);
        mMap.addMarker(new MarkerOptions().position(posisi).title("SSI").snippet("JATIWARNA").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_kantor)));
        cameraPosition = new CameraPosition.Builder().target(posisi).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        timer();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markerTid = marker.getTitle();
                String markerSnippet = marker.getSnippet();
                String markerLatProb = String.valueOf(marker.getPosition().latitude);
                String markerLonProb = String.valueOf(marker.getPosition().longitude);

                mTidTujuan.setText(markerTid);
                mTidSnippet.setText(markerSnippet);
                stringLatProb = markerLatProb;
                stringLonProb = markerLonProb;
                stringKeterangan = markerSnippet;
                return true;
            }
        });
    }


    private void showEmployee() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultProblem = jsonObject.getJSONArray(konfigurasiBri.TAG_JSON_ARRAY);
            for (int i = 0; i < resultProblem.length(); i++) {
                JSONObject jo = resultProblem.getJSONObject(i);
                String title = jo.getString(konfigurasiBri.TAG_TID);
                String snippetLokasi = jo.getString(konfigurasiBri.TAG_LOKASI);
                String snippet = jo.getString(konfigurasiBri.TAG_PROBLEM);
                String snippetTiket = jo.getString(konfigurasiBri.TAG_UPDATE_RTL_TICKET);
                String snippetWaktuInsert = jo.getString(konfigurasiBri.TAG_WAKTU_INSERT);
                String snippetTglProblem = jo.getString(konfigurasiBri.TAG_TGL_PROBLEM);
                String snippetLastTunai = jo.getString(konfigurasiBri.TAG_LAST_SUKSES);
                LatLng latLng = new LatLng(Double.parseDouble(jo.getString(konfigurasiBri.TAG_LATITUDE)), Double.parseDouble(jo.getString(konfigurasiBri.TAG_LONGITUDE)));
                addMaker(latLng, title, snippetLokasi, snippet, snippetTiket, snippetWaktuInsert, snippetTglProblem, snippetLastTunai);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeProbIn() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultProblem = jsonObject.getJSONArray(konfigurasiBri.TAG_JSON_ARRAY);
            for (int i = 0; i < resultProblem.length(); i++) {
                JSONObject jo = resultProblem.getJSONObject(i);
                String titleIn = jo.getString(konfigurasiBri.TAG_TID);
                String snippetLokasiIn = jo.getString(konfigurasiBri.TAG_LOKASI);
                String snippetIn = jo.getString(konfigurasiBri.TAG_PROBLEM);
                String snippetTiketIn = jo.getString(konfigurasiBri.TAG_UPDATE_RTL_TICKET);
                String snippetWaktuInsertIn = jo.getString(konfigurasiBri.TAG_WAKTU_INSERT);
                String snippetTglProblemIn = jo.getString(konfigurasiBri.TAG_TGL_PROBLEM);
                String snippetLastTunaiIn = jo.getString(konfigurasiBri.TAG_LAST_SUKSES);
                LatLng latLngIn = new LatLng(Double.parseDouble(jo.getString(konfigurasiBri.TAG_LATITUDE)), Double.parseDouble(jo.getString(konfigurasiBri.TAG_LONGITUDE)));
                addMakerIn(latLngIn, titleIn, snippetLokasiIn, snippetIn, snippetTiketIn, snippetWaktuInsertIn, snippetTglProblemIn, snippetLastTunaiIn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showEmployeeNt1D() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultProblem = jsonObject.getJSONArray(konfigurasiBri.TAG_JSON_ARRAY);
            for (int i = 0; i < resultProblem.length(); i++) {
                JSONObject jo = resultProblem.getJSONObject(i);
                String titleNT = jo.getString(konfigurasiBri.TAG_TID);
                String snippetLokasiNT = jo.getString(konfigurasiBri.TAG_LOKASI);
                String snippetNT = jo.getString(konfigurasiBri.TAG_PROBLEM);
                String snippetTiketNT = jo.getString(konfigurasiBri.TAG_UPDATE_RTL_TICKET);
                String snippetWaktuInsertNT = jo.getString(konfigurasiBri.TAG_WAKTU_INSERT);
                String snippetTglProblemNT = jo.getString(konfigurasiBri.TAG_TGL_PROBLEM);
                String snippetLastTunaiNT = jo.getString(konfigurasiBri.TAG_LAST_SUKSES);
                LatLng latLngNT = new LatLng(Double.parseDouble(jo.getString(konfigurasiBri.TAG_LATITUDE)), Double.parseDouble(jo.getString(konfigurasiBri.TAG_LONGITUDE)));
                addMakerNT(latLngNT, titleNT, snippetLokasiNT, snippetNT, snippetTiketNT, snippetWaktuInsertNT, snippetTglProblemNT, snippetLastTunaiNT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeUser() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultUser = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);
            for (int i = 0; i < resultUser.length(); i++) {
                JSONObject jo = resultUser.getJSONObject(i);
                String titleUser = jo.getString(konfigurasiAktivitas.TAG_NAMA);
                String titleUserTid = jo.getString(konfigurasiAktivitas.TAG_TID);
                LatLng latLngUser = new LatLng(Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LAT)), Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LON)));
                addMakerUser(latLngUser, titleUser, titleUserTid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeDirection() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultUser = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);
            for (int i = 0; i < resultUser.length(); i++) {
                JSONObject jo = resultUser.getJSONObject(i);
                LatLng latLngUserDirection = new LatLng(Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LAT)), Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LON)));
                LatLng latLngUserTidDirection = new LatLng(Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LAT_TID_PROB)), Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LON_TID_PROB)));
                getDirectionMap(latLngUserDirection, latLngUserTidDirection);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeTidTujuan() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultUser = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);

            for (int i = 0; i < resultUser.length(); i++) {
                JSONObject jo = resultUser.getJSONObject(i);
                String titleUserTujuan = jo.getString(konfigurasiAktivitas.TAG_NAMA);
                String titleUserTidTujuan = jo.getString(konfigurasiAktivitas.TAG_TID);
                LatLng latLngUserTid = new LatLng(Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LAT_TID_PROB)), Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LON_TID_PROB)));
                addMakerUserTujuan(latLngUserTid, titleUserTujuan, titleUserTidTujuan);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeTidProgres() {
        try {
            JSONObject jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultUser = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);

            for (int i = 0; i < resultUser.length(); i++) {
                JSONObject jo = resultUser.getJSONObject(i);
                String titleUserProgres = jo.getString(konfigurasiAktivitas.TAG_NAMA);
                String titleUserTidProgres = jo.getString(konfigurasiAktivitas.TAG_TID);
                LatLng latLngUserProgres = new LatLng(Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LAT_TID_PROB)), Double.parseDouble(jo.getString(konfigurasiAktivitas.TAG_LON_TID_PROB)));
                addMakerUserProgres(latLngUserProgres, titleUserProgres, titleUserTidProgres);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addMaker(LatLng latLng, String title, String snippetLokasi, String snippet, String snippetTiket, String snippetWaktuInsert, String snippetTglProblem, String snippetLastTunai) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(
                        "Lokasi : " + snippetLokasi + "\n" +
                                "Problem : " + snippet + "( " + snippetTiket + " )" + "\n" +
                                "Waktu Insert : " + snippetWaktuInsert + "\n" +
                                "Tgl Problem : " + snippetTglProblem + "\n" +
                                "Last Tunai : " + snippetLastTunai)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name_atm)));
    }

    private void addMakerIn(LatLng latLngIn, String titleIn, String snippetLokasiIn, String snippetIn, String snippetTiketIn, String snippetWaktuInsertIn, String snippetTglProblemIn, String snippetLastTunaiIn) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngIn)
                .title(titleIn)
                .snippet(
                        "Lokasi : " + snippetLokasiIn + "\n" +
                                "Problem : " + snippetIn + "( " + snippetTiketIn + " )" + "\n" +
                                "Waktu Insert : " + snippetWaktuInsertIn + "\n" +
                                "Tgl Problem : " + snippetTglProblemIn + "\n" +
                                "Last Tunai : " + snippetLastTunaiIn)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name_atm_in)));
    }

    private void addMakerNT(LatLng latLngNT, String titleNT, String snippetLokasiNT, String snippetNT, String snippetTiketNT, String snippetWaktuInsertNT, String snippetTglProblemNT, String snippetLastTunaiNT) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngNT)
                .title(titleNT)
                .snippet(
                        "Lokasi : " + snippetLokasiNT + "\n" +
                                "Problem : " + snippetNT + "( " + "No TRX" + snippetTiketNT + " )" + "\n" +
                                "Waktu Insert : " + snippetWaktuInsertNT + "\n" +
                                "Tgl Problem : " + snippetTglProblemNT + "\n" +
                                "Last Tunai : " + snippetLastTunaiNT)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_atm_nt1d)));
    }


    private void addMakerUser(LatLng latLngUser, String titleUser, String titleUserTid) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngUser)
                .title(titleUser)
                .snippet(titleUserTid)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_user)));
    }

    private void addMakerUserTujuan(LatLng latLngUserTid, String titleUserTujuan, String titleUserTidTujuan) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngUserTid)
                .title(titleUserTujuan)
                .snippet(titleUserTidTujuan)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_target_tid)));
    }

    private void addMakerUserProgres(LatLng latLngUserProgres, String titleUserProgres, String titleUserTidProgres) {
        mMap.addMarker(new MarkerOptions()
                .position(latLngUserProgres)
                .title(titleUserProgres)
                .snippet(titleUserTidProgres)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_progres_tid)));
    }


    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiBri.URL_GET_ALL_PROB_JTW);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getJSONIn() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeProbIn();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiBri.URL_GET_ALL_PROB_JTW_IN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getJSONNT() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeNt1D();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiBri.URL_GET_ALL_PROB_JTW_NT);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void getJSONUser() {
        class GetJSONUser extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeUser();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiAktivitas.URL_GET_ALL_USER_JTW);
                return s;
            }
        }
        GetJSONUser gju = new GetJSONUser();
        gju.execute();
    }

    private void getJSONDirection() {
        class GetJSONUser extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeDirection();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiAktivitas.URL_GET_ALL_USER_JTW_DIRECTION);
                return s;
            }
        }
        GetJSONUser gju = new GetJSONUser();
        gju.execute();
    }

    private void getJSONUserTidTujuan() {
        class GetJSONUser extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeTidTujuan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiAktivitas.URL_GET_ALL_USER_JTW_TID);
                return s;
            }
        }
        GetJSONUser gju = new GetJSONUser();
        gju.execute();
    }

    private void getJSONUserTidProgres() {
        class GetJSONUser extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeTidProgres();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiAktivitas.URL_GET_ALL_USER_JTW_TID_PROGRES);
                return s;
            }
        }
        GetJSONUser gju = new GetJSONUser();
        gju.execute();
    }


    public void getLatLng() {
        try {
            checkLocationPermission();
            client.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latLngRespon = new LatLng(location.getLatitude(), location.getLongitude());
                        stringLat = String.valueOf(latLngRespon.latitude);
                        stringLon = String.valueOf(latLngRespon.longitude);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestPermisi() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
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

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasiAktivitas.URL_GET_EMP, idAkti);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String stId = c.getString(konfigurasiAktivitas.TAG_ID);
            String stNama = c.getString(konfigurasiAktivitas.TAG_NAMA);
            String stNpp = c.getString(konfigurasiAktivitas.TAG_NPP);
            String stSentra = c.getString(konfigurasiAktivitas.TAG_SENTRA);
            String stLat = c.getString(konfigurasiAktivitas.TAG_LAT);
            String stLon = c.getString(konfigurasiAktivitas.TAG_LON);
            String stLogin = c.getString(konfigurasiAktivitas.TAG_LOGIN);
            String stLogout = c.getString(konfigurasiAktivitas.TAG_LOGOUT);
            String stStatus = c.getString(konfigurasiAktivitas.TAG_STATUS);
            String stTid = c.getString(konfigurasiAktivitas.TAG_TID);
            String stLatTidProb = c.getString(konfigurasiAktivitas.TAG_LAT_TID_PROB);
            String stLonTidProb = c.getString(konfigurasiAktivitas.TAG_LON_TID_PROB);

            idAkti = stId;
            stringNama = stNama;
            stringNpp = stNpp;
            stringSentra = stSentra;
            stringLat = stLat;
            stringLon = stLon;
            stringLagin = stLogin;
            stringLogout = stLogout;
            stringStatus = stStatus;
            mTidTujuan.setText(stTid);
            stringLatProb = stLatTidProb;
            stringLonProb = stLonTidProb;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
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

        final String sNama = stringNama;
        final String sNpp = stringNpp;
        final String sSentra = stringSentra;
        final String sLat = stringLat;
        final String sLon = stringLon;
        final String sLogin = stringLagin;
        final String sLogout = stringLogout;
        final String sStatus = ("MENUJU LOKASI");
        final String sTid = mTidTujuan.getText().toString().trim();
        final String sLatTidProb = stringLatProb;
        final String sLonTidProb = stringLonProb;
        final String sResponProb = tanggal + " " + jam + ":" + menit + ":" + detik;
        final String sProgresLokasi = ("-");
        final String sReportLokasi = ("-");
        final String sKeterangan = stringKeterangan;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ID, idAkti);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NAMA, sNama);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NPP, sNpp);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_SENTRA, sSentra);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT, sLat);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON, sLon);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGIN, sLogin);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, sLogout);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_STATUS, sStatus);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_TID, sTid);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, sLatTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, sLonTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, sResponProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_PROGRES_LOKASI, sProgresLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_REPORT_LOKASI, sReportLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_KETERANGAN, sKeterangan);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiAktivitas.URL_UPDATE_EMP, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        //ue.execute(bitmap);
    }

    private void updateEmployeeLatLngUser() {
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

        final String sNama = stringNama;
        final String sNpp = stringNpp;
        final String sSentra = stringSentra;
        final String sLat = stringLat;
        final String sLon = stringLon;
        final String sLogin = stringLagin;
        final String sLogout = stringLogout;
        final String sStatus = stringStatus;
        final String sTid = mTidTujuan.getText().toString().trim();
        final String sLatTidProb = stringLatProb;
        final String sLonTidProb = stringLonProb;
        final String sResponProb = tanggal + " " + jam + ":" + menit + ":" + detik;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ID, idAkti);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NAMA, sNama);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NPP, sNpp);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_SENTRA, sSentra);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT, sLat);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON, sLon);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGIN, sLogin);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, sLogout);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_STATUS, sStatus);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_TID, sTid);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, sLatTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, sLonTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, sResponProb);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiAktivitas.URL_UPDATE_EMP_LATLNG_USER, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void updateEmployeeProgresLokasi() {
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

        final String sNama = stringNama;
        final String sNpp = stringNpp;
        final String sSentra = stringSentra;
        final String sLat = stringLat;
        final String sLon = stringLon;
        final String sLogin = stringLagin;
        final String sLogout = stringLogout;
        final String sStatus = ("PROGRES LOKASI");
        final String sTid = mTidTujuan.getText().toString().trim();
        final String sLatTidProb = stringLatProb;
        final String sLonTidProb = stringLonProb;
        final String sResponProb = ("-");
        final String sProgresLokasi = tanggal + " " + jam + ":" + menit + ":" + detik;
        final String sReportLokasi = ("-");
        final String sKeterangan = stringKeterangan;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ID, idAkti);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NAMA, sNama);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NPP, sNpp);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_SENTRA, sSentra);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT, sLat);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON, sLon);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGIN, sLogin);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, sLogout);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_STATUS, sStatus);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_TID, sTid);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, sLatTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, sLonTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, sResponProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_PROGRES_LOKASI, sProgresLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_REPORT_LOKASI, sReportLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_KETERANGAN, sKeterangan);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiAktivitas.URL_UPDATE_EMP_PROGRES_LOKASI, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        //ue.execute(bitmap);
    }

    private void updateEmployeeReportLokasiDone() {
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

        final String sNama = stringNama;
        final String sNpp = stringNpp;
        final String sSentra = stringSentra;
        final String sLat = stringLat;
        final String sLon = stringLon;
        final String sLogin = stringLagin;
        final String sLogout = stringLogout;
        final String sStatus = ("DONE");
        final String sTid = mTidTujuan.getText().toString().trim();
        final String sLatTidProb = stringLatProb;
        final String sLonTidProb = stringLonProb;
        final String sResponProb = ("-");
        final String sProgresLokasi = ("-");
        final String sReportLokasi = tanggal + " " + jam + ":" + menit + ":" + detik;
        final String sKeterangan = stringKeterangan;
        final String sDeskripsi = etDeskripsi.getText().toString().toUpperCase();
        final String sVoltase = etVoltase.getText().toString().toUpperCase();
        final String sGrounding = etGrounding.getText().toString().toUpperCase();
        final String sCctv = stringCctv;
        final String sAc = stringSuhuRuangan;
        final String sAntiSkiming = stringAntiSkiming;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ID, idAkti);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NAMA, sNama);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NPP, sNpp);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_SENTRA, sSentra);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT, sLat);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON, sLon);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGIN, sLogin);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, sLogout);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_STATUS, sStatus);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_TID, sTid);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, sLatTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, sLonTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, sResponProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_PROGRES_LOKASI, sProgresLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_REPORT_LOKASI, sReportLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_KETERANGAN, sKeterangan);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_DESKRIPSI, sDeskripsi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_VOLTASE, sVoltase);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_GROUNDING, sGrounding);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_CCTV, sCctv);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_AC, sAc);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ANTI_SKIMING, sAntiSkiming);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiAktivitas.URL_UPDATE_EMP_REPORT_LOKASI, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        //ue.execute(bitmap);
    }

    private void updateEmployeeReportLokasiPending() {
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

        final String sNama = stringNama;
        final String sNpp = stringNpp;
        final String sSentra = stringSentra;
        final String sLat = stringLat;
        final String sLon = stringLon;
        final String sLogin = stringLagin;
        final String sLogout = stringLogout;
        final String sStatus = ("PENDING");
        final String sTid = mTidTujuan.getText().toString().trim();
        final String sLatTidProb = stringLatProb;
        final String sLonTidProb = stringLonProb;
        final String sResponProb = ("-");
        final String sProgresLokasi = ("-");
        final String sReportLokasi = tanggal + " " + jam + ":" + menit + ":" + detik;
        final String sKeterangan = stringKeterangan;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                //Bitmap bitmap = params[0];
                //String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasiAktivitas.KEY_EMP_ID, idAkti);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NAMA, sNama);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_NPP, sNpp);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_SENTRA, sSentra);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT, sLat);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON, sLon);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGIN, sLogin);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LOGOUT, sLogout);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_STATUS, sStatus);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_TID, sTid);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LAT_TID_PROB, sLatTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_LON_TID_PROB, sLonTidProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_RESPON_PROB, sResponProb);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_PROGRES_LOKASI, sProgresLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_REPORT_LOKASI, sReportLokasi);
                hashMap.put(konfigurasiAktivitas.KEY_EMP_KETERANGAN, sKeterangan);
                //hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasiAktivitas.URL_UPDATE_EMP_REPORT_LOKASI, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
        //ue.execute(bitmap);
    }

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

        final String nama = stringNama;
        final String npp = stringNpp;
        final String kl = stringSentra;
        final String lat = stringLat;
        final String lon = stringLon;
        final String login = tanggal + " " + jam + ":" + menit + ":" + detik;
        final String logout = stringLogout;
        final String status = ("AKTIF");
        final String tid = ("");
        final String latTidProb = stringLat;
        final String lonTidProb = stringLon;
        final String responProb = ("");

        class AddEmployee extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
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

    private void confirmUpdateEmployee() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Sudah di Lokasi Tid : " + mTidTujuan.getText() + " ?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateEmployeeProgresLokasi();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        btnRefresh.setVisibility(View.VISIBLE);
                        btnChat.setVisibility(View.GONE);
                        return;
                    }
                });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void DrawRoutePoly() {
        if (mPolyline == null) {
            Log.d(TAG, "mPolyline is null");
            return;
        }
        Polyline line = mMap.addPolyline(mPolyline);
    }


    private void getDirectionMap(LatLng from, LatLng to) {
        mToLat = (float) to.latitude;
        mToLng = (float) to.longitude;

        float mDistance = getDistanceKm(from, to);

        Document document = null;
        try {
            document = new GMapV2Direction().getDocument(from, to, "drive");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (document == null) {
            Toast.makeText(getBaseContext(), "Unable DrawRoute", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<LatLng> oLat = new GMapV2Direction().getDirection(document);

        mPolyline = new PolylineOptions();
        for (int i = 0; i < oLat.size(); i++) {
            mPolyline.add(oLat.get(i));
        }
        mPolyline.width(5);
        mPolyline.color(Color.BLUE);

        DrawRoutePoly();
    }

    public String getDistance(LatLng my_latlong, LatLng frnd_latlong) {
        float distance = getDistanceFloat(my_latlong, frnd_latlong);
        String dist = distance + " M";

        if (distance > 1000.0f) {
            distance = distance / 1000.0f;
            dist = distance + " KM";
        }
        return dist;
    }

    public float getDistanceKm(LatLng my_latlong, LatLng frnd_latlong) {
        float distance = getDistanceFloat(my_latlong, frnd_latlong);
        if (distance > 1000.0f) {
            distance = distance / 1000.0f;
        } else {
            distance = 1.0f;
        }
        return distance;

    }

    public float getDistanceFloat(LatLng my_latlong, LatLng frnd_latlong) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        float distance = l1.distanceTo(l2);
        return distance;
    }

    public void timer() {
        if (t == null) {
            t = new Timer();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMap.clear();
                        posisi = new LatLng(-6.310650, 106.927482);
                        mMap.addMarker(new MarkerOptions().position(posisi).title("SSI").snippet("JATIWARNA").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_kantor)));
                        getLatLng();
                        updateEmployeeLatLngUser();
                        getJSON();
                        getJSONIn();
                        getJSONNT();
                        getJSONUser();
                        //getJSONDirection();
                        //DrawRoutePoly();
                        //getJSONUserTidTujuan();
                        //getJSONUserTidProgres();
                        getJSONChatRespon();
                    }
                });

            }
        };
        t.schedule(task, 0, 10000);
    }

    public void stopTimer() {
        if (task != null) {
            Log.d("Timer", "Timer Cancel");
            t.cancel();
            t = null;
        }
    }

    private void showEmployeeChatRespon() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultAktivitas = jsonObject.getJSONArray(konfigurasiAktivitas.TAG_JSON_ARRAY);

            for (int i = 0; i < resultAktivitas.length(); i++) {
                JSONObject jo = resultAktivitas.getJSONObject(i);
                String id = jo.getString(konfigurasiAktivitas.TAG_ID);
                String nama = jo.getString(konfigurasiAktivitas.TAG_NAMA);
                String status = jo.getString(konfigurasiAktivitas.TAG_STATUS);
                String tid = jo.getString(konfigurasiAktivitas.TAG_TID);
                String waktu = jo.getString(konfigurasiAktivitas.TAG_RESPON_PROB);

                HashMap<String, String> employeesAktivitas = new HashMap<>();
                employeesAktivitas.put(konfigurasiAktivitas.TAG_ID, id);
                employeesAktivitas.put(konfigurasiAktivitas.TAG_NAMA, nama);
                employeesAktivitas.put(konfigurasiAktivitas.TAG_STATUS, status);
                employeesAktivitas.put(konfigurasiAktivitas.TAG_TID, tid);
                employeesAktivitas.put(konfigurasiAktivitas.TAG_RESPON_PROB, waktu);
                list.add(employeesAktivitas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapterProblem = new SimpleAdapter(
                MapsActivity.this, list, R.layout.activity_list_view_aktivitas_chat,
                new String[]{konfigurasiAktivitas.TAG_NAMA, konfigurasiAktivitas.TAG_STATUS, konfigurasiAktivitas.TAG_TID, konfigurasiAktivitas.TAG_RESPON_PROB},
                new int[]{R.id.nama, R.id.status, R.id.tid, R.id.waktu});

        lvChat.setAdapter(adapterProblem);
    }

    private void getJSONChatRespon() {
        class GetJSONChatRespon extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployeeChatRespon();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiAktivitas.URL_GET_EMP_ALL);
                return s;
            }
        }
        GetJSONChatRespon gjChatRespon = new GetJSONChatRespon();
        gjChatRespon.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> mapProblem = (HashMap) parent.getItemAtPosition(position);
        final String empId = mapProblem.get(konfigurasiAktivitas.TAG_ID).toString();

        final CharSequence[] dialogitem = {"Delete", "Kembali"};
        AlertDialog.Builder builderProb = new AlertDialog.Builder(MapsActivity.this);
        builderProb.setTitle("Pilihan");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(MapsActivity.this, "Maintenance", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        break;
                }
            }
        });
        builderProb.create().show();
    }
}
