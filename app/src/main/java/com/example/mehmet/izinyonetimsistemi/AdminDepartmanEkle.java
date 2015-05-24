package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class AdminDepartmanEkle extends ActionBarActivity implements View.OnClickListener {

    public ProgressDialog pDialog;
    public Button departman_kaydet;
    public ArrayAdapter<String> departmanAdapter;

    public JSONObject json;
    Json jsonParser = new Json();
    public JSONArray departman;
    public ArrayList<String> departman_ids,departman_ads;
    EditText departmanAdi;
    public String str_departman;
    public int spn_item;
    private static final String url_departman_add = "http://www.sihader.org/mehmet/departman_add.php";
    private static final String url_get_departman = "http://www.sihader.org/mehmet/get_departman.php";
    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    private static final String TAG_KISI= "kisiler";
    private static final String TAG_KISI_ADI = "kisi_adi";
    private static final String TAG_KISI_ID = "kisi_id";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_departman_ekle :
                Spinner spinner_departman = (Spinner) findViewById(R.id.spinner_departman);
                spn_item =  spinner_departman.getSelectedItemPosition();

                if (departman_ads.size() == 0 ){
                    Toast.makeText(AdminDepartmanEkle.this,"Departman Eksik",Toast.LENGTH_LONG
                    );
                }
                else new departman_add().execute();



        }

    }

    class getSpinner extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminDepartmanEkle.this);
            pDialog.setMessage("Sayfa Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params7 = new ArrayList<NameValuePair>();

            json = jsonParser.makeHttpRequest(url_get_departman, "POST", params7);

            try {

                departman = json.getJSONArray(TAG_KISI);

                departman_ids = new ArrayList<String>();
                departman_ads = new ArrayList<String>();

                for (int i = 0; i < departman.length(); i++) {
                    JSONObject item = departman.getJSONObject(i);
                    String departmanadi = item.getString(TAG_KISI_ADI);
                    String departmanid =item.getString(TAG_KISI_ID);

                    departman_ids.add(departmanid);
                    departman_ads.add(departmanadi);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {

            Spinner spinner_departman = (Spinner) findViewById(R.id.spinner_departman);

            departmanAdapter = new ArrayAdapter<String>(AdminDepartmanEkle.this, android.R.layout.simple_spinner_item, departman_ads);
            spinner_departman.setAdapter(departmanAdapter);

            pDialog.dismiss();
        }

    }

    class departman_add extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminDepartmanEkle.this);
            pDialog.setMessage("Departman Ekleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String txtadi = departmanAdi.getText().toString();
            str_departman = departman_ids.get(spn_item);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("adi", txtadi));
            params.add(new BasicNameValuePair("departman", str_departman));

            JSONObject json = jsonParser.makeHttpRequest(url_departman_add, "POST",params);


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
                Toast.makeText(AdminDepartmanEkle.this, file_url, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_departman_ekle);

        departman_kaydet = (Button) findViewById(R.id.btn_departman_ekle);
        departman_kaydet.setOnClickListener(this);

        departmanAdi = (EditText) findViewById(R.id.edit_departman_adi);
        new getSpinner().execute();


    }






}
