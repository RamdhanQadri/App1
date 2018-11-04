package com.example.user.ssiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

public class MainActivityRpl extends AppCompatActivity implements View.OnClickListener {
    java.util.Date tgl = new java.util.Date();
    private SimpleDateFormat formattgl = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private String tanggal = formattgl.format(tgl);

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextTidRpl;
    private EditText editTextNamaTimRpl;
    private EditText editTextNomorKontakRpl;
    private Spinner spinnerDenom;
    private Spinner spinnerPagu;
    private Spinner spinnerAdmin;
    private String editTextStatusRpl;
    private String editTextWktInsertRpl;

    private Button buttonAdd;

    private String editTextDenom = "";
    private String editTextPagu = "";
    private String editTextAdmin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rpl);

        getSupportActionBar().setTitle("Input Replenish");

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

        //Inisialisasi
        editTextTidRpl = (EditText) findViewById(R.id.editTextTidRpl);
        editTextNamaTimRpl = (EditText) findViewById(R.id.editTextNamaTimRpl);
        editTextNomorKontakRpl = (EditText) findViewById(R.id.editTextNomorKontakRpl);
        spinnerDenom = (Spinner) findViewById(R.id.label_spinner_denom);
        spinnerPagu = (Spinner) findViewById(R.id.label_spinner_pagu);
        spinnerAdmin = (Spinner) findViewById(R.id.label_spinner_admin);
        editTextStatusRpl = ("ON PROGRES");
        editTextWktInsertRpl = tanggal + " " + jam + ":" + menit + ":" + detik;

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(this);

        //Spinner Denom
        ArrayAdapter<CharSequence> adapterDenom = ArrayAdapter.createFromResource(this, R.array.labels_array_denom, android.R.layout.simple_spinner_item);
        adapterDenom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerDenom != null) {
            spinnerDenom.setAdapter(adapterDenom);
        }

        spinnerDenom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editTextDenom = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner Pagu
        ArrayAdapter<CharSequence> adapterPagu = ArrayAdapter.createFromResource(this, R.array.labels_array_pagu, android.R.layout.simple_spinner_item);
        adapterPagu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerPagu != null) {
            spinnerPagu.setAdapter(adapterPagu);
        }

        spinnerPagu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editTextPagu = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner Admin
        ArrayAdapter<CharSequence> adapterAdmin = ArrayAdapter.createFromResource(this, R.array.labels_array_admin, android.R.layout.simple_spinner_item);
        adapterAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerPagu != null) {
            spinnerPagu.setAdapter(adapterAdmin);
        }

        spinnerPagu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editTextAdmin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee() {

        final String tid = editTextTidRpl.getText().toString().trim();
        final String namaTim = editTextNamaTimRpl.getText().toString().trim().toUpperCase();
        final String nomorKontak = editTextNomorKontakRpl.getText().toString().trim();
        final String denom = editTextDenom;
        final String pagu = editTextPagu;
        final String admin = editTextAdmin;
        final String status = editTextStatusRpl;
        final String waktuInsert = editTextWktInsertRpl;

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivityRpl.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivityRpl.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_TID_RPL, tid);
                params.put(konfigurasi.KEY_EMP_NAMA_TIM_RPL, namaTim);
                params.put(konfigurasi.KEY_EMP_NOMOR_KONTAK_RPL, nomorKontak);
                params.put(konfigurasi.KEY_EMP_DENOM_RPL, denom);
                params.put(konfigurasi.KEY_EMP_PAGU_RPL, pagu);
                params.put(konfigurasi.KEY_EMP_STATUS_ADMIN_RPL, admin);
                params.put(konfigurasi.KEY_EMP_STATUS_RPL, status);
                params.put(konfigurasi.KEY_EMP_WAKTU_INSERT_RPL, waktuInsert);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_RPL, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        addEmployee();
        //bersih();
        startActivity(new Intent(this, MenuUtama.class));
    }
}
