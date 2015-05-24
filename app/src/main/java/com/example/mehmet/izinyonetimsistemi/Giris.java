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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;


public class Giris extends ActionBarActivity implements OnClickListener {


    SessionManager session;
    public static String userId;
    Button giris;
    EditText user,pass;
    public static int Yetki;
    private ProgressDialog pDialog;

    Json jsonParser = new Json();

    private static final String Url_Login = "http://www.sihader.org/mehmet/login.php";

    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;
    private static final String TAG_YETKI = "yetki";
    private static final String TAG_USERID = "userid";


    class LoginExecute extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Giris.this);
            pDialog.setMessage("Kullanıcı Kontrol Ediliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String username = user.getText().toString();
            String password = pass.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("eposta", username));
            params.add(new BasicNameValuePair("sifre", password));


            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Url_Login, "POST",
                    params);

            try {

                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated

                    Yetki = json.getInt(TAG_YETKI);
                    userId = json.getString(TAG_USERID);

                    session.createLoginSession(userId);

                    if (Yetki == 5){
                        Intent calisanMenu = new Intent(Giris.this,MenuCalisan.class);
                        startActivity(calisanMenu);
                        finish();
                    }
                    if (Yetki == 2 || Yetki == 3 || Yetki == 4 ){
                        Intent yoneticiMenu = new Intent(Giris.this,MenuYonetim.class);
                        startActivity(yoneticiMenu);
                        finish();
                    }
                    if (Yetki == 1){
                        Intent adminMenu = new Intent(Giris.this,MenuAdmin.class);
                        startActivity(adminMenu);
                        finish();

                    }

                }
                else {
                    Log.d("Giriş Başarısız", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

           if (file_url != null) {
               Toast.makeText(Giris.this, file_url, Toast.LENGTH_LONG)
                       .show();
           }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);

        giris = (Button) findViewById(R.id.btn_giris);
        user = (EditText) findViewById(R.id.txt_eposta);
        pass = (EditText) findViewById(R.id.txt_sifre);

        giris.setOnClickListener(this);

        session = new SessionManager(getApplicationContext());


    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_giris:
                if(isOnline()){
                    new LoginExecute().execute();
                }
                else {
                    Toast.makeText(Giris.this, "İnternetiniz Yok Yada Çok Yavaş", Toast.LENGTH_LONG) .show();} break; default: break;
                     }
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}









