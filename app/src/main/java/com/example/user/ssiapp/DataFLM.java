package com.example.user.ssiapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataFLM extends Fragment implements ListView.OnItemClickListener{
    private ListView listView;

    private String JSON_STRING;

    private View rootView;


    public DataFLM() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_data_flm, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
        return rootView;
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
                String tid = jo.getString(konfigurasiBri.TAG_TID);
                String problem = jo.getString(konfigurasiBri.TAG_PROBLEM);
                String tgl_problem = jo.getString(konfigurasiBri.TAG_TGL_PROBLEM);
                String rtl_ticket = jo.getString(konfigurasiBri.TAG_UPDATE_RTL_TICKET);

                HashMap<String, String> employeesProblem = new HashMap<>();
                employeesProblem.put(konfigurasiBri.TAG_ID, id);
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
                rootView.getContext(), list, R.layout.list_item,
                new String[]{konfigurasiBri.TAG_TID, konfigurasiBri.TAG_PROBLEM, konfigurasiBri.TAG_TGL_PROBLEM, konfigurasiBri.TAG_UPDATE_RTL_TICKET},
                new int[]{R.id.tid, R.id.problem, R.id.tgl_problem, R.id.rtltikect, R.id.status_ssi, R.id.nama_tim, R.id.nomor_kontak, R.id.respon});

        listView.setAdapter(adapterProblem);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(rootView.getContext(), "Mengambil Data", "Mohon Tunggu...", false, false);
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
                String s = rh.sendGetRequest(konfigurasiBri.URL_GET_ALL_PROB_JTW);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        final String empId = map.get(konfigurasiBri.TAG_ID).toString();

        final CharSequence[] dialogitem = {"Update Data", "Detail Data", "Maps", "Kembali"};
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                switch (item){
                    case 0:
                        Intent i = new Intent(getContext(), Tampil.class);
                        i.putExtra(konfigurasiBri.EMP_ID, empId);
                        startActivity(i);
                        break;
                    case 1:
                        Intent in = new Intent(getContext(), TampilDetails.class);
                        in.putExtra(konfigurasiBri.EMP_ID, empId);
                        startActivity(in);
                        break;
                    case 2:
                        Intent inn = new Intent(getContext(), MapsActivity.class);
                        inn.putExtra(konfigurasiBri.EMP_ID, empId);
                        startActivity(inn);
                        break;
                    case 3:
                        break;
                }
            }
        });
        builder.create().show();

    }
}
