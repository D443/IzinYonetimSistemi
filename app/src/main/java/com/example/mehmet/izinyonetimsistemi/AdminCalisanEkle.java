package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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

import static android.view.View.OnClickListener;


public class AdminCalisanEkle extends ActionBarActivity implements OnClickListener {

    public Button kaydet;
    EditText adi,soyadi,eposta,sifre;
    public Spinner spinner_unvan,spinner_yetki,spinner_ust_yonetici;
    public ProgressDialog pDialog;
    public int spn_unvan,spn_yetki,spn_yonetici;
    Json jsonParser = new Json();
    public JSONObject json;
    public JSONObject json2;
    public ArrayList<String> unvan_ids,unvan_ads,yetki_ads,yetki_ids,yonetici_ids,yonetici_ads;
    public int sira ;
    private static final String url_save = "http://www.sihader.org/mehmet/calisan_add.php";
    private static final String url_get_spinner = "http://www.sihader.org/mehmet/get_calisan.php";
    private static final String url_get_yonetim = "http://www.sihader.org/mehmet/get_yonetim.php";

    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    private static final String TAG_UNVAN= "unvanlar";
    private static final String TAG_UNVAN_ADI = "unvan_adi";
    private static final String TAG_UNVAN_ID = "unvan_id";

    private static final String TAG_YONETICI= "yoneticiler";
    private static final String TAG_YONETICI_ADI = "yonetici_adi";
    private static final String TAG_YONETICI_ID = "yonetici_id";

    private static final String TAG_YETKI= "yetkiler";
    private static final String TAG_YETKI_ADI = "yetki_adi";
    private static final String TAG_YETKI_ID = "yetki_id";



    public JSONArray unvan,yetki,yonetici;
    public ArrayAdapter<String> unvanAdapter;
    public ArrayAdapter<String> yetkiAdapter;
    public ArrayAdapter<String> yoneticiAdapter;

    public String str_unvan,str_yetki,str_yonetici;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ekle: {
                spinner_unvan = (Spinner) findViewById(R.id.spinner_unvan);
                spinner_yetki = (Spinner) findViewById(R.id.spinner_yetki);
                spinner_ust_yonetici = (Spinner) findViewById(R.id.spinner_ust_yonetici);

                spn_unvan = spinner_unvan.getSelectedItemPosition();
                spn_yetki = spinner_yetki.getSelectedItemPosition();
                spn_yonetici = spinner_ust_yonetici.getSelectedItemPosition();

                if (unvan_ads.size() == 0 ){
                    Toast.makeText(AdminCalisanEkle.this,"Unvan Eksik",Toast.LENGTH_LONG
                    );
                }
                if (yetki_ads.size() == 0 ){
                    Toast.makeText(AdminCalisanEkle.this,"Yetki Eksik",Toast.LENGTH_LONG
                    );
                }
                if (yonetici_ads.size() == 0 ){
                    Toast.makeText(AdminCalisanEkle.this,"Ust Yonetici Eksik",Toast.LENGTH_LONG
                    );
                }
                else new calisan_add().execute();


            }
            break;
            default:
                break;
        }
    }

    class calisan_add extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminCalisanEkle.this);
            pDialog.setMessage("Yeni Kullanıcı Ekleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String txtadi = adi.getText().toString();
            String txtsoyadi = soyadi.getText().toString();
            String txteposta = eposta.getText().toString();
            String txtsifre = sifre.getText().toString();

            str_unvan = unvan_ids.get(spn_unvan);
            str_yetki = yetki_ids.get(spn_yetki);
            str_yonetici = yonetici_ids.get(spn_yonetici);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("adi", txtadi));
            params.add(new BasicNameValuePair("soyadi", txtsoyadi));
            params.add(new BasicNameValuePair("eposta", txteposta));
            params.add(new BasicNameValuePair("sifre", txtsifre));
            params.add(new BasicNameValuePair("unvan", str_unvan));
            params.add(new BasicNameValuePair("yetki", str_yetki));
            params.add(new BasicNameValuePair("yonetici", str_yonetici));

            JSONObject json = jsonParser.makeHttpRequest(url_save, "POST",params);

            Log.d("json gönderme","");

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
                Toast.makeText(AdminCalisanEkle.this, file_url, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    class getSpinner extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminCalisanEkle.this);
            pDialog.setMessage("Sayfa Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params7 = new ArrayList<NameValuePair>();

          //  params7.add(new BasicNameValuePair("userID", "asd"));
            json2 = jsonParser.makeHttpRequest(url_get_spinner, "POST", params7);

            try {


                unvan = json2.getJSONArray(TAG_UNVAN);
                yetki = json2.getJSONArray(TAG_YETKI);

                unvan_ids = new ArrayList<String>();
                unvan_ads = new ArrayList<String>();

                yetki_ads = new ArrayList<String>();
                yetki_ids = new ArrayList<String>();

                Log.d("unvan loglari "+ unvan,"");
                Log.d("yetki loglari"+ yetki ,"");

                for (int i = 0; i < unvan.length(); i++) {
                    JSONObject item = unvan.getJSONObject(i);
                    String unvanadi = item.getString(TAG_UNVAN_ADI);
                    String unvanid =item.getString(TAG_UNVAN_ID);

                    unvan_ids.add(unvanid);
                    unvan_ads.add(unvanadi);
                }

                for (int i = 0; i < yetki.length(); i++) {
                    JSONObject item2 = yetki.getJSONObject(i);
                    String yetkiadi = item2.getString(TAG_YETKI_ADI);
                    String yetkiid =item2.getString(TAG_YETKI_ID);

                    yetki_ids.add(yetkiid);
                    yetki_ads.add(yetkiadi);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {

            Spinner spinner_unvanlar = (Spinner) findViewById(R.id.spinner_unvan);
            Spinner spinner_yetkiler = (Spinner) findViewById(R.id.spinner_yetki);

            unvanAdapter = new ArrayAdapter<String>(AdminCalisanEkle.this, android.R.layout.simple_spinner_item, unvan_ads);
            spinner_unvanlar.setAdapter(unvanAdapter);

            yetkiAdapter = new ArrayAdapter<String>(AdminCalisanEkle.this, android.R.layout.simple_spinner_item, yetki_ads);
            spinner_yetkiler.setAdapter(yetkiAdapter);

            pDialog.dismiss();
        }

    }

    class getYonetici extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminCalisanEkle.this);
            pDialog.setMessage("Yöneticiler Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            String sira_sayisi = Integer.toString(sira);
            List<NameValuePair> params7 = new ArrayList<NameValuePair>();
            params7.add(new BasicNameValuePair("sirasi", sira_sayisi));
            json2 = jsonParser.makeHttpRequest(url_get_yonetim, "POST", params7);

            Log.d("json2" + json2,"");

            try {

                yonetici = json2.getJSONArray(TAG_YONETICI);

                yonetici_ads = new ArrayList<String>();
                yonetici_ids = new ArrayList<String>();

                for (int i = 0; i < yonetici.length(); i++) {
                    JSONObject item3 = yonetici.getJSONObject(i);
                    String yoneticiadi = item3.getString(TAG_YONETICI_ADI);
                    String yoneticiid =item3.getString(TAG_YONETICI_ID);

                    yonetici_ads.add(yoneticiadi);
                    yonetici_ids.add(yoneticiid);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {

            Spinner spinner_ust_yonetici = (Spinner) findViewById(R.id.spinner_ust_yonetici);

            yoneticiAdapter = new ArrayAdapter<String>(AdminCalisanEkle.this, android.R.layout.simple_spinner_item, yonetici_ads);
            spinner_ust_yonetici.setAdapter(yoneticiAdapter);

            pDialog.dismiss();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_calisan_ekle);

        kaydet = (Button) findViewById(R.id.btn_ekle);
        kaydet.setOnClickListener(this);

        new getSpinner().execute();
        spinner_yetki = (Spinner)findViewById(R.id.spinner_yetki);
        adi = (EditText) findViewById(R.id.editText);
        soyadi = (EditText) findViewById(R.id.editText2);
        eposta = (EditText) findViewById(R.id.editText3);
        sifre = (EditText) findViewById(R.id.editText4);
        spinner_yetki.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            sira = i +1 ;
        new getYonetici().execute();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }








}
