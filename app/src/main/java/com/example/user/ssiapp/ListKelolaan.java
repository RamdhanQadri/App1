package com.example.user.ssiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ListKelolaan extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kelolaan);

        getSupportActionBar().hide();

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showEmployee() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray resultProblem = jsonObject.getJSONArray(konfigurasiKelolaan.TAG_JSON_ARRAY);

            for (int i = 0; i < resultProblem.length(); i++) {
                JSONObject jo = resultProblem.getJSONObject(i);
                String id = jo.getString(konfigurasiKelolaan.TAG_ID);
                String tid = jo.getString(konfigurasiKelolaan.TAG_TID);
                String lokasi = jo.getString(konfigurasiKelolaan.TAG_LOKASI);
                String sn = jo.getString(konfigurasiKelolaan.TAG_SN_MESIN);

                HashMap<String, String> employeesProblem = new HashMap<>();
                employeesProblem.put(konfigurasiKelolaan.TAG_ID, id);
                employeesProblem.put(konfigurasiKelolaan.TAG_TID, tid);
                employeesProblem.put(konfigurasiKelolaan.TAG_LOKASI, lokasi);
                employeesProblem.put(konfigurasiKelolaan.TAG_SN_MESIN, sn);
                list.add(employeesProblem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapterProblem = new SimpleAdapter(
                ListKelolaan.this, list, R.layout.activity_list_view_kelolaan,
                new String[]{konfigurasiKelolaan.TAG_TID, konfigurasiKelolaan.TAG_LOKASI, konfigurasiKelolaan.TAG_SN_MESIN},
                new int[]{R.id.tid, R.id.lokasi, R.id.serial_number});

        listView.setAdapter(adapterProblem);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(TampilSemua.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasiKelolaan.URL_GET_ALL_KELOLAAN_JTW);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> mapProblem = (HashMap) parent.getItemAtPosition(position);
        final String empId = mapProblem.get(konfigurasiKelolaan.TAG_ID).toString();

        final CharSequence[] dialogitem = {"Update Data", "List Problem", "Kembali"};
        AlertDialog.Builder builderProb = new AlertDialog.Builder(ListKelolaan.this);
        builderProb.setTitle("Pilihan");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        Intent i = new Intent(ListKelolaan.this, UpdateKelolaan.class);
                        i.putExtra(konfigurasiKelolaan.EMP_ID, empId);
                        startActivity(i);
                        finish();
                        break;
                    case 1:
                        Intent ii = new Intent(ListKelolaan.this, ListProblem.class);
                        startActivity(ii);
                        finish();
                        break;
                    case 2:
                        break;
                }
            }
        });
        builderProb.create().show();
    }
}
