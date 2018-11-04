package com.example.user.ssiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TampilDetails extends AppCompatActivity {

    private String editTextId;
    private TextView editTextTid;
    private TextView editTextSn;
    private TextView editTextMesin;
    private TextView editTextTipeMesin;
    private TextView editTextNamaTim;
    private TextView editTextNomorKontak;
    private TextView editTextProblem;
    private TextView editTextStatus;
    private TextView editTextSuksesTrx;
    private TextView editTextWktInsert;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_details);

        getSupportActionBar().setTitle("Detail Data");

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID);

        //editTextId = (TextView) findViewById(R.id.editTextId);
        editTextTid = (TextView) findViewById(R.id.editTextTid);
        editTextSn = (TextView) findViewById(R.id.editTextSn);
        editTextMesin = (TextView) findViewById(R.id.editTextMesin);
        editTextTipeMesin = (TextView) findViewById(R.id.editTextTipeMesin);
        editTextNamaTim = (TextView) findViewById(R.id.editTextNamaTim);
        editTextNomorKontak = (TextView) findViewById(R.id.editTextNomorKontak);
        editTextProblem = (TextView) findViewById(R.id.editTextProblem);
        editTextStatus = (TextView) findViewById(R.id.editTextStatus);
        editTextSuksesTrx = (TextView) findViewById(R.id.editTextSuksesTrx);
        editTextWktInsert = (TextView) findViewById(R.id.editTextWktInsert);

        editTextId = id;

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetails.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
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
            editTextSn.setText(sn);
            editTextMesin.setText(mesin);
            editTextTipeMesin.setText(tipe_mesin);
            editTextNamaTim.setText(nama_tim);
            editTextNomorKontak.setText(nomor_kontak);
            editTextProblem.setText(problem);
            editTextStatus.setText(status);
            editTextSuksesTrx.setText(sukses_trx);
            editTextWktInsert.setText(wkt_insert);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
