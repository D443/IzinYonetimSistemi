package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdminProjeEkle extends ActionBarActivity implements View.OnClickListener{

    public ArrayAdapter<String> projeYoneticiAdapter,departmanAdapter;
    public ProgressDialog pDialog;
    private static final String url_get_spinner = "http://www.sihader.org/mehmet/get_proje.php";
    private static final String url_proje_ekle = "http://www.sihader.org/mehmet/proje_add.php";
    public JSONObject json;
    Json jsonParser = new Json();
    public JSONArray proje,departman;

    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    private static final String TAG_PROJE= "projeler";
    private static final String TAG_PROJE_ADI = "proje_adi";
    private static final String TAG_PROJE_ID = "proje_id";

    private static final String TAG_DEPARTMAN= "departmanlar";
    private static final String TAG_DEPARTMAN_ADI = "departman_adi";
    private static final String TAG_DEPARTMAN_ID = "departman_id";

    public int spn_proje_item,spn_departman_item;

    Button proje_kaydet;
    public ArrayList<String> proje_ids,proje_ads,departman_ids,departman_ads;
    EditText projeAdi;
    public String str_departman,str_proje;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proje_ekle :
                Spinner spinner_proje_yonetici = (Spinner) findViewById(R.id.spn_proje_yonetici);
                Spinner spinner_departman_adi = (Spinner) findViewById(R.id.spn_departman_adi);

                spn_proje_item =  spinner_proje_yonetici.getSelectedItemPosition();
                spn_departman_item  = spinner_departman_adi.getSelectedItemPosition();

                if (departman_ads.size() == 0 ){
                    Toast.makeText(AdminProjeEkle.this,"Departman Adı Eksik",Toast.LENGTH_LONG
                    );
                }
                if (proje_ads.size() == 0 ){
                    Toast.makeText(AdminProjeEkle.this,"Proje Yöneticisi Eksik",Toast.LENGTH_LONG
                    );
                }

                else new projeAdd().execute();


        }

    }


    class projeAdd extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminProjeEkle.this);
            pDialog.setMessage("Proje Ekleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String txtadi = projeAdi.getText().toString();
            str_departman = departman_ids.get(spn_departman_item);
            str_proje = proje_ids.get(spn_proje_item);

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("adi", txtadi));
            params2.add(new BasicNameValuePair("departman", str_departman));
            params2.add(new BasicNameValuePair("proje", str_proje));

            JSONObject json = jsonParser.makeHttpRequest(url_proje_ekle, "POST",params2);
            try {

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return json.getString(TAG_MESSAGE);
                }
                else {
                    return json.getString(TAG_MESSAGE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(AdminProjeEkle.this, file_url, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }




    class getSpinner extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminProjeEkle.this);
            pDialog.setMessage("Sayfa Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            json = jsonParser.makeHttpRequest(url_get_spinner, "POST", params1);

            try {

                proje = json.getJSONArray(TAG_PROJE);
                departman = json.getJSONArray(TAG_DEPARTMAN);

                proje_ids = new ArrayList<String>();
                proje_ads = new ArrayList<String>();

                departman_ids = new ArrayList<String>();
                departman_ads = new ArrayList<String>();




                for (int i = 0; i < proje.length(); i++) {
                    JSONObject item = proje.getJSONObject(i);
                    String projeadi = item.getString(TAG_PROJE_ADI);
                    String projenid =item.getString(TAG_PROJE_ID);

                    proje_ids.add(projenid);
                    proje_ads.add(projeadi);
                }

                for (int i = 0; i < departman.length(); i++) {
                    JSONObject item = departman.getJSONObject(i);
                    String departmanadi = item.getString(TAG_DEPARTMAN_ADI);
                    String departmannid =item.getString(TAG_DEPARTMAN_ID);

                    departman_ids.add(departmannid);
                    departman_ads.add(departmanadi);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {

            Spinner spinner_proje_yonetici = (Spinner) findViewById(R.id.spn_proje_yonetici);
            Spinner spinner_departman = (Spinner) findViewById(R.id.spn_departman_adi);

            projeYoneticiAdapter = new ArrayAdapter<String>(AdminProjeEkle.this, android.R.layout.simple_spinner_item, proje_ads);
            spinner_proje_yonetici.setAdapter(projeYoneticiAdapter);

            departmanAdapter = new ArrayAdapter<String>(AdminProjeEkle.this, android.R.layout.simple_spinner_item, departman_ads);
            spinner_departman.setAdapter(departmanAdapter);

            pDialog.dismiss();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_proje_ekle);

        projeAdi = (EditText) findViewById(R.id.txt_proje_adi);
        proje_kaydet = (Button) findViewById(R.id.btn_proje_ekle);
        proje_kaydet.setOnClickListener(this);
        new getSpinner().execute();

    }


}
