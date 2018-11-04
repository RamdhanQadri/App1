package com.example.user.ssiapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilSemua extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua);

        getSupportActionBar().setTitle("List Problem");

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    private void showEmployee() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_ID);
                String tid = jo.getString(konfigurasi.TAG_TID);
                String tim = jo.getString(konfigurasi.TAG_NAMA_TIM);
                String status = jo.getString(konfigurasi.TAG_STATUS);

                HashMap<String, String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID, id);
                employees.put(konfigurasi.TAG_TID, tid);
                employees.put(konfigurasi.TAG_NAMA_TIM, tim);
                employees.put(konfigurasi.TAG_STATUS, status);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ListAdapter adapter = new SimpleAdapter(
                //TampilSemua.this, list, R.layout.list_item,
                //new String[]{konfigurasi.TAG_TID, konfigurasi.TAG_NAMA_TIM, konfigurasi.TAG_STATUS},
                //new int[]{R.id.tid, R.id.namaTim, R.id.status});

        //listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemua.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        final String empId = map.get(konfigurasi.TAG_ID).toString();

        final CharSequence[] dialogitem = {"Update Data", "Detail Data", "Kembali"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TampilSemua.this);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), Tampil.class);
                                i.putExtra(konfigurasi.EMP_ID, empId);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), TampilDetails.class);
                                in.putExtra(konfigurasi.EMP_ID, empId);
                                startActivity(in);
                                break;
                            case 2:
                                break;
                        }
                    }
                });
        builder.create().show();

        //Intent intent = new Intent(this, Tampil.class);
        //HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        //String empId = map.get(konfigurasi.TAG_ID).toString();
        //intent.putExtra(konfigurasi.EMP_ID, empId);
        //startActivity(intent);
    }
}
