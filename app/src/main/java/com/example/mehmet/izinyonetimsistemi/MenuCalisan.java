package com.example.mehmet.izinyonetimsistemi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import adapter.CustomGrid;


public class MenuCalisan extends ActionBarActivity {


    GridView grid;
    String[] menu = {
            "İzin Talep",
            "İzin Kontrol",
            "Çıkış"


    };
    int[] imageId = {
            R.drawable.izin_talep,
            R.drawable.izin_kontrol,
            R.drawable.cikis


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genel_menu);


        CustomGrid adapter = new CustomGrid(this, menu, imageId);
        grid = (GridView) findViewById(R.id.gridView1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0: {
                        Intent i = new Intent(getApplicationContext(), IzinTalep.class);
                        startActivity(i);

                        break;
                    }
                    case 1: {

                        Intent t = new Intent(getApplicationContext(),CalisanIzinKontrol.class);
                        startActivity(t);

                        break;
                    }
                    case 2:{

                        Intent i = new Intent(getApplicationContext(),Giris.class);
                        startActivity(i);
                        finish();


                    }




                    break;


                    default:
                        break;
                }


            }
        });

    }

}
















