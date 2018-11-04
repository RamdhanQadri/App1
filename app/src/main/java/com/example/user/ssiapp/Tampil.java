package com.example.user.ssiapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Tampil extends AppCompatActivity implements View.OnClickListener {

    private String editTextId;
    private TextView editTextTid;
    private String editTextSn;
    private String editTextMesin;
    private String editTextTipeMesin;
    private String editTextNamaTim;
    private String editTextNomorKontak;
    private String editTextProblem;
    private Spinner spinnerStatus;
    private EditText editTextSuksesTrx;
    private String editTextWktInsert;

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonCamera;

    private String id;
    private String editTextStatus = "";

    private static final int CAMERA_REQUEST_CODE = 2222;
    private ImageView imageView;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        getSupportActionBar().setTitle("Update Problem");

        Intent intent = getIntent();

        //id = intent.getStringExtra(konfigurasi.EMP_ID);
        id = intent.getStringExtra(konfigurasiBri.EMP_ID);

        //editTextId = (EditText) findViewById(R.id.editTextId);
        editTextTid = (TextView) findViewById(R.id.editTextTid);
        //editTextSn = (EditText) findViewById(R.id.editTextSn);
        //editTextMesin = (EditText) findViewById(R.id.editTextMesin);
        //editTextTipeMesin = (EditText) findViewById(R.id.editTextTipeMesin);
        //editTextNamaTim = (EditText) findViewById(R.id.editTextNamaTim);
        //editTextNomorKontak = (EditText) findViewById(R.id.editTextNomorKontak);
        //editTextProblem = (EditText) findViewById(R.id.editTextProblem);
        spinnerStatus = (Spinner) findViewById(R.id.label_spinner_status);
        editTextSuksesTrx = (EditText) findViewById(R.id.editTextSuksesTrx);
        //editTextWktInsert = (EditText) findViewById(R.id.editTextWktInsert);
        imageView = (ImageView) findViewById(R.id.imageView);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonCamera = (Button) findViewById(R.id.buttonCamera);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonCamera.setOnClickListener(this);

        editTextId = id;

        getEmployee();

        //SPINNER Problem
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.labels_array_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerStatus != null) {
            spinnerStatus.setAdapter(adapterStatus);
        }
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {
                editTextStatus = adapterView.getItemAtPosition(ii).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Tampil.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP, id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String tid = c.getString(konfigurasi.TAG_TID);
            String sn = c.getString(konfigurasi.TAG_SN);
            String mesin = c.getString(konfigurasi.TAG_MESIN);
            String tipe_mesin = c.getString(konfigurasi.TAG_TIPE_MESIN);
            String nama_tim = c.getString(konfigurasi.TAG_NAMA_TIM);
            String nomor_kontak = c.getString(konfigurasi.TAG_NOMOR_KONTAK);
            String problem = c.getString(konfigurasi.TAG_PROBLEM);
            String status = c.getString(konfigurasi.TAG_STATUS);
            String sukses_trx = c.getString(konfigurasi.TAG_SUKSES_TRX);
            String wkt_insert = c.getString(konfigurasi.TAG_WKT_INSERT);

            editTextTid.setText(tid);
            editTextSn = sn;
            editTextMesin = mesin;
            editTextTipeMesin = tipe_mesin;
            editTextNamaTim = nama_tim;
            editTextNomorKontak = nomor_kontak;
            editTextProblem = problem;
            editTextStatus = status;
            editTextSuksesTrx.setText(sukses_trx);
            editTextWktInsert = wkt_insert;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateEmployee() {
        final String tid = editTextTid.getText().toString().trim();
        final String sn = editTextSn;
        final String mesin = editTextMesin;
        final String tipe_mesin = editTextTipeMesin;
        final String nama_tim = editTextNamaTim;
        final String nomor_kontak = editTextNomorKontak;
        final String problem = editTextProblem;
        final String status = editTextStatus;
        final String sukses_trx = editTextSuksesTrx.getText().toString().trim();
        final String wkt_insert = editTextWktInsert;

        class UpdateEmployee extends AsyncTask<Bitmap, Void, String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Tampil.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Tampil.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID, id);
                hashMap.put(konfigurasi.KEY_EMP_TID, tid);
                hashMap.put(konfigurasi.KEY_EMP_SN, sn);
                hashMap.put(konfigurasi.KEY_EMP_MESIN, mesin);
                hashMap.put(konfigurasi.KEY_EMP_TIPE_MESIN, tipe_mesin);
                hashMap.put(konfigurasi.KEY_EMP_NAMA_TIM, nama_tim);
                hashMap.put(konfigurasi.KEY_EMP_NOMOR_KONTAK, nomor_kontak);
                hashMap.put(konfigurasi.KEY_EMP_PROBLEM, problem);
                hashMap.put(konfigurasi.KEY_EMP_STATUS, status);
                hashMap.put(konfigurasi.KEY_EMP_SUKSES_TRX, sukses_trx);
                hashMap.put(konfigurasi.KEY_EMP_WKT_INSERT, wkt_insert);
                hashMap.put(konfigurasi.KEY_EMP_IMAGE, uploadImage);

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP, hashMap);

                return s;

            }

        }

        UpdateEmployee ue = new UpdateEmployee();
        //ue.execute();
        ue.execute(bitmap);
    }

    private void deleteEmployee() {
        class DeleteEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Tampil.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Tampil.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin ?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(Tampil.this, TampilSemua.class));
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

    private void confirmUpdateEmployee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin ?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateEmployee();
                        startActivity(new Intent(Tampil.this, MenuUtama.class));
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
        if (v == buttonCamera){
            showFileChooser();
        }
        if (v == buttonUpdate) {
            if (editTextStatus.equals("ON PROGRES")){
                Toast toast = Toast.makeText(Tampil.this, " SILAHKAN PILIH STATUS ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            }else if (editTextSuksesTrx.length() == 0){
                Toast toast = Toast.makeText(Tampil.this, " MASUKAN SUKSES TRANSAKSI ", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorToastError);
                toast.show();
                return;
            }else {
                confirmUpdateEmployee();
            }
        }

        if (v == buttonDelete) {
            //confirmDeleteEmployee();
            Toast toast = Toast.makeText(Tampil.this, " GAGAL!! SILAHKAN KONFIRMASI ADMIN ", Toast.LENGTH_LONG);
            View view = toast.getView();
            view.setBackgroundResource(R.color.colorToastWarning);
            toast.show();
            return;
        }
    }
}
