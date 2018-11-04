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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    java.util.Date tgl = new java.util.Date();
    private SimpleDateFormat formattgl = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private String tanggal = formattgl.format(tgl);

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextTid;
    private EditText editTextSn;
    private Spinner spinnerMesin;
    private Spinner spinnerTipeMesin;
    private EditText editTextNamaTim;
    private EditText editTextNomorKontak;
    private Spinner spinnerProblem;
    private String editTextStatus;
    private String editTextSuksesTrx;
    private String editTextWktInsert;
    //private TextView getEditTextTimeInsert;

    private Button buttonAdd;
    private Button buttonView;

    private String editTextMesin = "";
    private String editTextTipeMesin = "";
    private String editTextProblem = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Input Problem");

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

        //Inisialisasi dari View
        editTextTid = (EditText) findViewById(R.id.editTextTid);
        editTextSn = (EditText) findViewById(R.id.editTextSn);
        spinnerMesin = (Spinner) findViewById(R.id.label_spinner_mesin);
        spinnerTipeMesin = (Spinner) findViewById(R.id.label_spinner_tipe_mesin);
        editTextNamaTim = (EditText) findViewById(R.id.editTextNamaTim);
        editTextNomorKontak = (EditText) findViewById(R.id.editTextNomorKontak);
        spinnerProblem = (Spinner) findViewById(R.id.label_spinner_problem);
        //editTextStatus = (EditText) findViewById(R.id.editTextStatus);
        //editTextSuksesTrx = (EditText) findViewById(R.id.editTextSuksesTrx);
        //editTextWktInsert = (EditText) findViewById(R.id.editTextWktInsert);
        editTextStatus = ("ON PROGRES");
        editTextSuksesTrx = ("");
        editTextWktInsert = tanggal + " " + jam + ":" + menit + ":" + detik;

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);

        //SPINNER MESIN
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array_mesin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerMesin != null) {
            spinnerMesin.setAdapter(adapter);
        }

        spinnerMesin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editTextMesin = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SPINNER TIPE MESIN
        ArrayAdapter<CharSequence> adapterMesin = ArrayAdapter.createFromResource(this, R.array.labels_array_tipe_mesin, android.R.layout.simple_spinner_item);
        adapterMesin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerTipeMesin != null) {
            spinnerTipeMesin.setAdapter(adapterMesin);
        }
        spinnerTipeMesin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {
                editTextTipeMesin = adapterView.getItemAtPosition(ii).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SPINNER Problem
        ArrayAdapter<CharSequence> adapterProb = ArrayAdapter.createFromResource(this, R.array.labels_array_problem, android.R.layout.simple_spinner_item);
        adapterProb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerProblem != null) {
            spinnerProblem.setAdapter(adapterProb);
        }
        spinnerProblem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {
                editTextProblem = adapterView.getItemAtPosition(ii).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee() {

        final String tid = editTextTid.getText().toString().trim();
        final String sn = editTextSn.getText().toString().trim().toUpperCase();
        final String mesin = editTextMesin;
        final String tipe_mesin = editTextTipeMesin;
        final String nama_tim = editTextNamaTim.getText().toString().trim().toUpperCase();
        final String nomor_kontak = editTextNomorKontak.getText().toString().trim();
        final String problem = editTextProblem;
        final String status = editTextStatus;
        final String sukses_trx = editTextSuksesTrx;
        final String wkt_insert = editTextWktInsert;

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_TID, tid);
                params.put(konfigurasi.KEY_EMP_SN, sn);
                params.put(konfigurasi.KEY_EMP_MESIN, mesin);
                params.put(konfigurasi.KEY_EMP_TIPE_MESIN, tipe_mesin);
                params.put(konfigurasi.KEY_EMP_NAMA_TIM, nama_tim);
                params.put(konfigurasi.KEY_EMP_NOMOR_KONTAK, nomor_kontak);
                params.put(konfigurasi.KEY_EMP_PROBLEM, problem);
                params.put(konfigurasi.KEY_EMP_STATUS, status);
                params.put(konfigurasi.KEY_EMP_SUKSES_TRX, sukses_trx);
                params.put(konfigurasi.KEY_EMP_WKT_INSERT, wkt_insert);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd) {
            if (editTextTid.length() == 0) {
                Toast toast = Toast.makeText(MainActivity.this, " Masukan Tid ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextSn.length() == 0) {
                Toast toast = Toast.makeText(MainActivity.this, " Masukan Serial Number ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextNamaTim.length() == 0) {
                Toast toast = Toast.makeText(MainActivity.this, " Masukan Nama Tim ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextNomorKontak.length() == 0) {
                Toast toast = Toast.makeText(MainActivity.this, " Masukan Nomor Kontak ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextMesin.equals("PILIH")) {
                Toast toast = Toast.makeText(MainActivity.this, " SILAHKAN PILIH MESIN ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextTipeMesin.equals("PILIH")) {
                Toast toast = Toast.makeText(MainActivity.this, " SILAHKAN PILIH TIPE MESIN ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else if (editTextProblem.equals("PILIH")) {
                Toast toast = Toast.makeText(MainActivity.this, " SILAHKAN PILIH PROBLEM ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            } else {
                addEmployee();
                bersih();
                startActivity(new Intent(this, MenuUtama.class));
            }
        }
    }

    private void bersih() {
        editTextTid.setText("");
        editTextSn.setText("");
        editTextNamaTim.setText("");
        editTextNomorKontak.setText("");
        editTextMesin.equals("PILIH");
        editTextTipeMesin.equals("PILIH");
        editTextProblem.equals("PILIH");
    }


}
