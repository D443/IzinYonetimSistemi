package com.example.mehmet.izinyonetimsistemi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapter.DepartmanListviewAdapter;
import adapter.Kisiler;


public class AdminDepartmanListele extends ActionBarActivity {


    ListView list;
    DepartmanListviewAdapter adapter;
    EditText editsearch;
    public String[] isimler;
    Button guncelle;
    ArrayList<Kisiler> arraylist = new ArrayList<Kisiler>();
    public ProgressDialog pDialog;
    Json jsonParser = new Json();
    public JSONObject json;

    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    private static final String TAG_DEPARTMAN = "departmanlar";
    private static final String TAG_DEPARTMAN_ADI = "departman_adi";
    private static final String TAG_DEPARTMAN_ID = "departman_id";
    public ArrayList<String> departman_ids, departman_ads;
    public JSONArray departman;

    private static final String url_departman_listele = "http://www.sihader.org/mehmet/departman_listele.php";

    class fillAdapter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminDepartmanListele.this);
            pDialog.setMessage("Departmanlar Yükleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            json = jsonParser.makeHttpRequest(url_departman_listele, "POST", params1);

            try {
                departman = json.getJSONArray(TAG_DEPARTMAN);

                departman_ids = new ArrayList<String>();
                departman_ads = new ArrayList<String>();

                for (int i = 0; i < departman.length(); i++) {
                    JSONObject item = departman.getJSONObject(i);
                    String departmanadi = item.getString(TAG_DEPARTMAN_ADI);
                    String departmanid =item.getString(TAG_DEPARTMAN_ID);

                    departman_ids.add(departmanid);
                    departman_ads.add(departmanadi);
                }



                for (int i = 0; i < departman_ads.size() ; i++)
                {
                    Kisiler kisi = new Kisiler(departman_ads.get(i), departman_ids.get(i));
                    arraylist.add(kisi);
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
            adapter = new DepartmanListviewAdapter(AdminDepartmanListele.this, arraylist);

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

    }



}
