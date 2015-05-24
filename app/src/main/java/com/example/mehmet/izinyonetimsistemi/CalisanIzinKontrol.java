package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapter.Basvurular;
import adapter.DepartmanListviewAdapter;
import adapter.IzinListviewAdapter;
import adapter.Kisiler;

/**
 * Created by MYPC on 26.4.2015.
 */
public class CalisanIzinKontrol extends ActionBarActivity {

    ListView list;
    IzinListviewAdapter adapter;
    EditText editsearch;
    public String[] isimler;
    ArrayList<Basvurular> arraylist = new ArrayList<Basvurular>();
    public ProgressDialog pDialog;
    Json jsonParser = new Json();
    public JSONObject json;
    SessionManager session;
    private static final String TAG_FORM = "formlar";
    private static final String TAG_FORM_TARIH = "formBasvuruTarihi";
    private static final String TAG_FORM_IZIN = "formIzinSuresi";
    private static final String TAG_FORM_ONAY = "formOnay";
    private static final String TAG_FORM_ID = "formId";
public String user_id;
    public ArrayList<String> basvuruTarihs, izinSures,formOnays,FormIds;
    public JSONArray izinKontrol;

    private static final String url_izin_listele = "http://www.sihader.org/mehmet/izinForm_liste.php";





    class fillAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CalisanIzinKontrol.this);
            pDialog.setMessage("Başvurular Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("calisanId", user_id));
            json = jsonParser.makeHttpRequest(url_izin_listele, "POST", params1);

            try {
                izinKontrol = json.getJSONArray(TAG_FORM);

                basvuruTarihs = new ArrayList<String>();
                izinSures = new ArrayList<String>();
                formOnays = new ArrayList<String>();
                FormIds = new ArrayList<String>();


                for (int i = 0; i < izinKontrol.length(); i++) {
                    JSONObject item = izinKontrol.getJSONObject(i);
                    String basvuru_tarih = item.getString(TAG_FORM_TARIH);
                    String izin_sure =item.getString(TAG_FORM_IZIN);
                    String form_onay =item.getString(TAG_FORM_ONAY);
                    String izin_id =item.getString(TAG_FORM_ID);


                    basvuruTarihs.add(basvuru_tarih);
                    izinSures.add(izin_sure);
                    formOnays.add(form_onay);
                    FormIds.add(izin_id);

                }



                for (int i = 0; i < basvuruTarihs.size() ; i++)
                {
                    Basvurular basvuru = new Basvurular(basvuruTarihs.get(i), izinSures.get(i), formOnays.get(i), FormIds.get(i));
                    arraylist.add(basvuru);
                }

            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }

            return  null;
        }

        protected void onPostExecute(Void args) {

            pDialog.dismiss();
            list = (ListView) findViewById(R.id.liste);

            // Pass results to ListViewAdapter Class
            adapter = new IzinListviewAdapter(CalisanIzinKontrol.this, arraylist);

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
       session = new SessionManager(getApplicationContext());
       user_id  = session.getUserId();
       Log.d("session user id = " + user_id,"");
        new fillAdapter().execute();




    }


}
