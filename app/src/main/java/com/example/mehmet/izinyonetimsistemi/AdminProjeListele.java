package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapter.Kisiler;
import adapter.ProjeListviewAdapter;


public class AdminProjeListele extends ActionBarActivity {


    ListView list;
    ProjeListviewAdapter adapter;
    EditText editsearch;
    public String[] isimler;
    Button guncelle;
    ArrayList<Kisiler> arraylist = new ArrayList<Kisiler>();
    public ProgressDialog pDialog;
    Json jsonParser = new Json();
    public JSONObject json;

    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    private static final String TAG_PROJE = "projeler";
    private static final String TAG_PROJE_ADI = "proje_adi";
    private static final String TAG_PROJE_ID = "proje_id";

    public ArrayList<String> proje_ids, proje_ads;
    public JSONArray proje;

    private static final String url_proje_listele = "http://www.sihader.org/mehmet/proje_listele.php";






    class fillAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminProjeListele.this);
            pDialog.setMessage("Projeler Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            json = jsonParser.makeHttpRequest(url_proje_listele, "POST", params1);

            try {
                proje = json.getJSONArray(TAG_PROJE);

                proje_ids = new ArrayList<String>();
                proje_ads = new ArrayList<String>();

                for (int i = 0; i < proje.length(); i++) {
                    JSONObject item = proje.getJSONObject(i);
                    String projeadi = item.getString(TAG_PROJE_ADI);
                    String projeid = item.getString(TAG_PROJE_ID);

                    proje_ids.add(projeid);
                    proje_ads.add(projeadi);
                }


                for (int i = 0; i < proje_ads.size(); i++) {
                    Kisiler kisi = new Kisiler(proje_ads.get(i), proje_ids.get(i));
                    arraylist.add(kisi);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void args) {

            pDialog.dismiss();
            list = (ListView) findViewById(R.id.liste);

            // Pass results to ListViewAdapter Class
            adapter = new ProjeListviewAdapter(AdminProjeListele.this, arraylist);

            // Binds the Adapter to the ListView
            list.setAdapter(adapter);

            editsearch = (EditText) findViewById(R.id.search);

            // Capture Text in EditText
            editsearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // Pass results to ListViewAdapter Class
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                }
            });


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listele);
        new fillAdapter().execute();

        list = (ListView) findViewById(R.id.liste);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(AdminProjeListele.this,"asdasd", Toast.LENGTH_LONG).show();
            }
        });


    }
}