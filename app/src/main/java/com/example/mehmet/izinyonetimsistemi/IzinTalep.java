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


public class IzinTalep extends ActionBarActivity implements View.OnClickListener{


    private ProgressDialog pDialog;
    public ArrayAdapter<String> CalisanAdapter;
    public Spinner izin_turu,sure_turu;
    public String spn_izin_item,spn_sure_item,str_personel_id,user_id;
    public int spn_personel_item ;
    public JSONObject json;
    Json jsonParser = new Json();
    public SessionManager session ;
    private static final String TAG_KISI= "kisiler";
    private static final String TAG_KISI_ADI = "kisi_adi";
    private static final String TAG_KISI_ID = "kisi_id";
    public ArrayList<String> kisi_ids,kisi_ads;
    public JSONArray kisi;

    private static final String TAG_DERSLER = "dersler";
    private static final String TAG_DERSADI = "dersAdi";
    public Button btnSave,btnBack;
    public String spnDonem;
    public String spnYil;

    public EditText gorev,aciklama,baslamaTarih,bitisTarih,izinSure,adres,erisimAd,erisimSoyad,erisimAdres,erisimTel,erisimYakinlik;

    private static final String url_izin_talep= "http://www.sihader.org/mehmet/izinForm_add.php";
    private static final String url_get_spinner = "http://www.sihader.org/mehmet/calisan_listele.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public int success;

    class fillAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IzinTalep.this);
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
                kisi = json.getJSONArray(TAG_KISI);

                kisi_ids = new ArrayList<String>();
                kisi_ads = new ArrayList<String>();

                for (int i = 0; i < kisi.length(); i++) {
                    JSONObject item = kisi.getJSONObject(i);
                    String kisiadi = item.getString(TAG_KISI_ADI);
                    String kisiid =item.getString(TAG_KISI_ID);

                    kisi_ids.add(kisiid);
                    kisi_ads.add(kisiadi);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {
            pDialog.dismiss();
            Spinner spn_personel = (Spinner) findViewById(R.id.spnPersonel);

            CalisanAdapter = new ArrayAdapter<String>(IzinTalep.this, android.R.layout.simple_spinner_item, kisi_ads);
            spn_personel.setAdapter(CalisanAdapter);

        }

    }


    private class IzinKaydet  extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IzinTalep.this);
            pDialog.setMessage("İzin Talebiniz Kaydediliyor Lütfen Bekleyiniz ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String strGorev = gorev.getText().toString();
            String strAciklama = aciklama.getText().toString();

            str_personel_id = kisi_ids.get(spn_personel_item);
            String strBaslamaTarih = baslamaTarih.getText().toString();
            String strBitisTarih = bitisTarih.getText().toString();
            String strIzinSure = izinSure.getText().toString();
            String strAdres = adres.getText().toString();
            String strErisimAd = erisimAd.getText().toString();
            String strErisimSoyad = erisimSoyad.getText().toString();
            String strErisimAdres = erisimAdres.getText().toString();
            String strErisimTel = erisimTel.getText().toString();
            String strErisimYakinlik = erisimYakinlik.getText().toString();


            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

            params3.add(new BasicNameValuePair("gorevi", strGorev));
            params3.add(new BasicNameValuePair("izinTuru", spn_izin_item));
            params3.add(new BasicNameValuePair("baslaTarihi", strBaslamaTarih));
            params3.add(new BasicNameValuePair("bitisTarihi", strBitisTarih));
            params3.add(new BasicNameValuePair("izinSuresi", strIzinSure  ));
            params3.add(new BasicNameValuePair("izinZamani", spn_sure_item));
            params3.add(new BasicNameValuePair("izinAdresi", strAdres));
            params3.add(new BasicNameValuePair("izinAciklama", strAciklama));
            params3.add(new BasicNameValuePair("izinPersonelId", str_personel_id));
            params3.add(new BasicNameValuePair("adi", strErisimAd));
            params3.add(new BasicNameValuePair("soyadi", strErisimSoyad));
            params3.add(new BasicNameValuePair("adres", strErisimAdres));
            params3.add(new BasicNameValuePair("tel", strErisimTel));
            params3.add(new BasicNameValuePair("yakinlik", strErisimYakinlik));
            params3.add(new BasicNameValuePair("userId", user_id));


            JSONObject json = jsonParser.makeHttpRequest(url_izin_talep, "POST", params3);

            // check json success tag
            try {
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
            if (success == 1) {
                Toast.makeText(IzinTalep.this, "İzin Kaydınız Başarıyla Eklenmiştir.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(IzinTalep.this, "İzin Kaydınız Eklenememiştir.", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIzinKaydet:
                Spinner spinner_izin_tur = (Spinner) findViewById(R.id.spn_izin_tur);
                Spinner spinner_sure_tur = (Spinner) findViewById(R.id.spnSureTur);
                Spinner spinner_personel = (Spinner) findViewById(R.id.spnPersonel);

                spn_izin_item =  spinner_izin_tur.getSelectedItem().toString();
                spn_sure_item  = spinner_sure_tur.getSelectedItem().toString();
                spn_personel_item = spinner_personel.getSelectedItemPosition();

                new IzinKaydet().execute();

               break;



        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calisan_izin_talep);

        izin_turu = (Spinner) findViewById(R.id.spn_izin_tur);
        sure_turu = (Spinner) findViewById(R.id.spnSureTur);

        btnSave = (Button) findViewById(R.id.btnIzinKaydet);

        gorev = (EditText) findViewById(R.id.txtGorev);
        aciklama = (EditText) findViewById(R.id.txtAciklama);
        baslamaTarih = (EditText) findViewById(R.id.txtBaslamaTarih);
        bitisTarih = (EditText) findViewById(R.id.txtBitisTarih);
        izinSure = (EditText) findViewById(R.id.txtIzin);
        adres = (EditText) findViewById(R.id.txtAdres);
        erisimAd = (EditText) findViewById(R.id.txtErisimAd);
        erisimSoyad = (EditText) findViewById(R.id.txtErisimSoyad);
        erisimAdres = (EditText) findViewById(R.id.txtErisimAdres);
        erisimTel = (EditText) findViewById(R.id.txtErisimTel);
        erisimYakinlik = (EditText) findViewById(R.id.txtErisimYakinlik);

        session =new SessionManager(getApplicationContext());
        user_id = session.getUserId();


        btnSave.setOnClickListener(this);

        List<String> list_izin = new ArrayList<String>();
        list_izin.add("Yıllık");
        list_izin.add("Mazeret");
        list_izin.add("Hastalık");
        list_izin.add("Ücretsiz");
        list_izin.add("Diğer");


        ArrayAdapter<String> izinAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list_izin);

        izinAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

        izin_turu.setAdapter(izinAdapter);


        List<String> list_sure = new ArrayList<String>();
        list_sure.add("Saat");
        list_sure.add("Gün");

        ArrayAdapter<String> sureAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list_sure);

        sureAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

        sure_turu.setAdapter(sureAdapter);

        new fillAdapter().execute();


    }

}
