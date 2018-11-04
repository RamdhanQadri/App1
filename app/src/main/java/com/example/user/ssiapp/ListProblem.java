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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListProblem extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);

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
            JSONArray resultProblem = jsonObject.getJSONArray(konfigurasiBri.TAG_JSON_ARRAY);

            for (int i = 0; i < resultProblem.length(); i++) {
                JSONObject jo = resultProblem.getJSONObject(i);
                String id = jo.getString(konfigurasiBri.TAG_ID);
                String lokasi = jo.getString(konfigurasiBri.TAG_LOKASI);
                String tid = jo.getString(konfigurasiBri.TAG_TID);
                String problem = jo.getString(konfigurasiBri.TAG_PROBLEM);
                String tgl_problem = jo.getString(konfigurasiBri.TAG_TGL_PROBLEM);
                String rtl_ticket = jo.getString(konfigurasiBri.TAG_UPDATE_RTL_TICKET);

                HashMap<String, String> employeesProblem = new HashMap<>();
                employeesProblem.put(konfigurasiBri.TAG_ID, id);
                employeesProblem.put(konfigurasiBri.TAG_LOKASI, lokasi);
                employeesProblem.put(konfigurasiBri.TAG_TID, tid);
                employeesProblem.put(konfigurasiBri.TAG_PROBLEM, problem);
                employeesProblem.put(konfigurasiBri.TAG_TGL_PROBLEM, tgl_problem);
                employeesProblem.put(konfigurasiBri.TAG_UPDATE_RTL_TICKET, rtl_ticket);
                list.add(employeesProblem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapterProblem = new SimpleAdapter(
                ListProblem.this, list, R.layout.activity_list_view_bri,
                new String[]{konfigurasiBri.TAG_TID, konfigurasiBri.TAG_LOKASI, konfigurasiBri.TAG_PROBLEM, konfigurasiBri.TAG_TGL_PROBLEM, konfigurasiBri.TAG_UPDATE_RTL_TICKET},
                new int[]{R.id.tid, R.id.lokasi, R.id.problem, R.id.tgl_problem, R.id.rtltikect});

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
                String s = rh.sendGetRequest(konfigurasiBri.URL_GET_PROB_JTW);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //HashMap<String, String> mapProblem = (HashMap) parent.getItemAtPosition(position);
        //final String empId = mapProblem.get(konfigurasiBri.TAG_ID).toString();

        final CharSequence[] dialogitem = {"List Kelolaan", "Maps", "Kembali"};
        AlertDialog.Builder builderProb = new AlertDialog.Builder(ListProblem.this);
        builderProb.setTitle("Pilihan");
        builderProb.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item) {
                    case 0:
                        Intent intent = new Intent(ListProblem.this, ListKelolaan.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent intents= new Intent(ListProblem.this, MapsActivity.class);
                        startActivity(intents);
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
