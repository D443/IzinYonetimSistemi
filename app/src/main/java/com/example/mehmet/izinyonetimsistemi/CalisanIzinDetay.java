package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CalisanIzinDetay extends ActionBarActivity {

    public TextView txtBasvuruTarih,txtYoneticiAciklama,txtDetayGorev,txtIzinAciklama,txtIzinTuru,txtIzinBaslama,txtIzinBitis,txtIzinSure,txtIzinPersonel,txtIzinSureTur,txtIzinAdres;

    Button btnBack;
    private ProgressDialog pDialog;

    public JSONObject json;
    Json jsonParser = new Json();

    private static final String url_form_details = "http://www.sihader.org/mehmet/izinForm_goruntule.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BILGILER = "bilgiler";
    private static final String TAG_RED = "redAciklama";
    private static final String TAG_GOREV = "gorev";
    private static final String TAG_IZIN_TURU = "izinTuru";
    private static final String TAG_BASVURU_TARIH = "basvuruTarihi";
    private static final String TAG_BASLA_TARIH = "baslaTarihi";
    private static final String TAG_BITIS_TARIH = "bitisTarihi";
    private static final String TAG_IZIN_SURE= "izinSure";
    private static final String TAG_IZIN_ADRES= "izinAdresi";
    private static final String TAG_IZIN_ACİKLAMA= "izinAciklama";
    private static final String TAG_IZIN_PERSONEL= "izinPersonelAd";





    class GetUserDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CalisanIzinDetay.this);
            pDialog.setMessage("Çalışan Detayları Yükleniyor..");
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

                            String red = item.getString(TAG_RED);
                            String gorev =item.getString(TAG_GOREV);
                            String izin_turu =item.getString(TAG_IZIN_TURU);
                            String basvuru_tarih =item.getString(TAG_BASVURU_TARIH);
                            String basla_tarih =item.getString(TAG_BASLA_TARIH);
                            String bitis_tarih =item.getString(TAG_BITIS_TARIH);
                            String izin_adres =item.getString(TAG_IZIN_ADRES);
                            String izin_sure =item.getString(TAG_IZIN_SURE);
                            String izin_aciklama =item.getString(TAG_IZIN_ACİKLAMA);
                            String izin_personel =item.getString(TAG_IZIN_PERSONEL);

                            txtYoneticiAciklama = (TextView) findViewById(R.id.yonetici_aciklama);
                            txtDetayGorev = (TextView) findViewById(R.id.detayGorev);
                            txtIzinAciklama = (TextView) findViewById(R.id.izinAciklama);
                            txtIzinTuru = (TextView) findViewById(R.id.izinTuru);
                            txtBasvuruTarih = (TextView) findViewById(R.id.basvuruTarihi);
                            txtIzinBaslama= (TextView) findViewById(R.id.izinBaslamaTarihi);
                            txtIzinBitis= (TextView) findViewById(R.id.izinBitisTarihi);
                            txtIzinSure= (TextView) findViewById(R.id.izinSure);
                            txtIzinAdres = (TextView) findViewById(R.id.izinAdres);
                            txtIzinPersonel= (TextView) findViewById(R.id.izinPersonel);


                            if(red.equals("yok")){
                                red = "İzniniz Üst Yönetici Tarafından Onaylandı." ;
                            }

                            txtBasvuruTarih.setText(basvuru_tarih);
                            txtYoneticiAciklama.setText(red);
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





    public String formId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calisan_izin_detay);

        Bundle extras = getIntent().getExtras();
        formId = extras.getString("formId");

        new GetUserDetails().execute();

    }

    public void geriButon(View view) {
        Intent i = new Intent(CalisanIzinDetay.this,CalisanIzinKontrol.class);
        startActivity(i);
    }

    }

