package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class YonetimIzinDetay extends ActionBarActivity implements View.OnClickListener {
    String formId;
    EditText redAciklama;

    public TextView txtBasvuruTarih,txtDetayGorev,txtIzinAciklama,txtIzinTuru,txtIzinBaslama,txtIzinBitis,txtIzinSure,txtIzinPersonel,txtIzinAdres;
SessionManager session;
    public TextView asd;
String user_id;
    Button btnReddet,btnKabulet;
    private ProgressDialog pDialog;

    public JSONObject json;
    Json jsonParser = new Json();

    private static final String url_form_details = "http://www.sihader.org/mehmet/izinOnaySayfasi.php";
    private static final String url_izin_kabul = "http://www.sihader.org/mehmet/izinOnay.php";
    private static final String url_izin_red = "http://www.sihader.org/mehmet/izinRed.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_BILGILER = "bilgiler";

    private static final String TAG_GOREV = "gorev";
    private static final String TAG_IZIN_TURU = "izinTuru";
    private static final String TAG_BASVURU_TARIH = "basvuruTarihi";
    private static final String TAG_BASLA_TARIH = "baslaTarihi";
    private static final String TAG_BITIS_TARIH = "bitisTarihi";
    private static final String TAG_IZIN_SURE= "izinSure";
    private static final String TAG_IZIN_ADRES= "izinAdresi";
    private static final String TAG_IZIN_ACİKLAMA= "izinAciklama";
    private static final String TAG_IZIN_PERSONEL= "izinPersonelAd";

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_reddet: {
                if(isOnline()) {
                    new izinRed().execute();
                }
                break;
            }
            case R.id.btn_kabulet: {
                if(isOnline()) {
                    new izinKabul().execute();
                }
                break;
            }



        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    class izinKabul extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(YonetimIzinDetay.this);
            pDialog.setMessage("İşlem Gerçekleştiriliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("userId", user_id));
            params2.add(new BasicNameValuePair("formId", formId));
            JSONObject json = jsonParser.makeHttpRequest(url_izin_kabul, "POST",params2);


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
                Toast.makeText(YonetimIzinDetay.this, file_url, Toast.LENGTH_LONG)
                        .show();
            }
            Intent i =  new Intent(getApplicationContext(),YonetimIzinKontrol.class);
            startActivity(i);
        }
    }




    class izinRed extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(YonetimIzinDetay.this);
            pDialog.setMessage("İşlem Gerçekleştiriliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String txtRed = redAciklama.getText().toString();


            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("userId", user_id));
            params2.add(new BasicNameValuePair("formId", formId));
            params2.add(new BasicNameValuePair("redAciklama", txtRed));


            JSONObject json = jsonParser.makeHttpRequest(url_izin_red, "POST",params2);


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
                Toast.makeText(YonetimIzinDetay.this, file_url, Toast.LENGTH_LONG)
                        .show();

                Intent i =  new Intent(getApplicationContext(),YonetimIzinKontrol.class);
                startActivity(i);
            }
        }
    }







    class GetUserDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(YonetimIzinDetay.this);
            pDialog.setMessage("Yönetim Detayları Yükleniyor..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("formId", formId));
            json = jsonParser.makeHttpRequest(
                    url_form_details, "POST", params1);
            runOnUiThread(new Runnable() {
                public void run() {

                    int success;
                    try {
                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {

                            JSONArray bilgiler = json.getJSONArray(TAG_BILGILER); // JSON Array

                            JSONObject item = bilgiler.getJSONObject(0);

                            String gorev =item.getString(TAG_GOREV);
                            String izin_turu =item.getString(TAG_IZIN_TURU);
                            String basvuru_tarih =item.getString(TAG_BASVURU_TARIH);
                            String basla_tarih =item.getString(TAG_BASLA_TARIH);
                            String bitis_tarih =item.getString(TAG_BITIS_TARIH);
                            String izin_adres =item.getString(TAG_IZIN_ADRES);
                            String izin_sure =item.getString(TAG_IZIN_SURE);
                            String izin_aciklama =item.getString(TAG_IZIN_ACİKLAMA);
                            String izin_personel =item.getString(TAG_IZIN_PERSONEL);

                            txtDetayGorev = (TextView) findViewById(R.id.txtYonetimGorev);
                            txtIzinAciklama = (TextView) findViewById(R.id.yonetimTxtIzinAciklama);
                            txtIzinTuru = (TextView) findViewById(R.id.yonetimTxtIzinTuru);
                            txtBasvuruTarih = (TextView) findViewById(R.id.yonetimTxtBasvuruTarihi);
                            txtIzinBaslama= (TextView) findViewById(R.id.yonetimTxtBaslamaTarihi);
                            txtIzinBitis= (TextView) findViewById(R.id.yonetimTxtBitisTarihi);
                            txtIzinSure= (TextView) findViewById(R.id.yonetimTxtIzinSuresi);
                            txtIzinAdres = (TextView) findViewById(R.id.yonetimTxtIzinAdres);
                            txtIzinPersonel= (TextView) findViewById(R.id.yonetimTxtIzinPersonel);

                            Log.d("personel adı "+ izin_personel,"");

                            txtBasvuruTarih.setText(basvuru_tarih);
                            txtDetayGorev.setText(gorev);
                            txtIzinAciklama.setText(izin_aciklama);
                            txtIzinTuru.setText(izin_turu);
                            txtIzinBaslama.setText(basla_tarih);
                            txtIzinBitis.setText(bitis_tarih);
                            txtIzinSure.setText(izin_sure);
                            txtIzinAdres.setText(izin_adres);
                            txtIzinPersonel.setText(izin_personel);


                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonetim_izin_detay);
        session = new SessionManager(getApplicationContext());
        user_id  = session.getUserId();
        Bundle extras = getIntent().getExtras();
        formId = extras.getString("formId");
        new GetUserDetails().execute();
        redAciklama = (EditText) findViewById(R.id.yonetimTxtRed);

        btnReddet = (Button) findViewById(R.id.btn_reddet);
        btnKabulet = (Button) findViewById(R.id.btn_kabulet);

        btnReddet.setOnClickListener(this);
        btnKabulet.setOnClickListener(this);

    }


}
