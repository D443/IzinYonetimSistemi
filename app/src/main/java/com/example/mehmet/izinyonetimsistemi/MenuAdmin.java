package com.example.mehmet.izinyonetimsistemi;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.GridView;

import adapter.CustomGrid;


public class MenuAdmin extends ActionBarActivity {


    GridView grid;
    String[] menu = {
            "Çalışan Yönet",
            "Proje Yönet",
            "Departman Yönet",
            "Çıkış"


    };
    int[] imageId = {
            R.drawable.izin_talep,
            R.drawable.projeyonet,
            R.drawable.departman,
            R.drawable.cikis


    };

    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

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
                    case 0:
                        alertDialogBuilder = new AlertDialog.Builder(MenuAdmin.this);
                        alertDialogBuilder.setMessage("Çalışan üzerinde yapmak istediğiniz işlemi seçiniz");

                        alertDialogBuilder.setPositiveButton("Çalışan Ekle",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent positveActivity = new Intent(getApplicationContext(), AdminCalisanEkle.class);
                                        startActivity(positveActivity);

                                    }
                                });
                        alertDialogBuilder.setNegativeButton("Çalışan Listele",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent negativeActivity = new Intent(getApplicationContext(), AdminCalisanListele.class);
                                        startActivity(negativeActivity);
                                    }
                                });


                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                        break;

                    case 1: {

                        alertDialogBuilder = new AlertDialog.Builder(MenuAdmin.this);
                        alertDialogBuilder.setMessage("Proje üzerinde yapmak istediğiniz işlemi seçiniz");

                        alertDialogBuilder.setPositiveButton("Proje Ekle",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent positveActivity = new Intent(getApplicationContext(), AdminProjeEkle.class);
                                        startActivity(positveActivity);

                                    }
                                });
                        alertDialogBuilder.setNegativeButton("Proje Listele",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent negativeActivity = new Intent(getApplicationContext(), AdminProjeListele.class);
                                        startActivity(negativeActivity);
                                    }
                                });


                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                        break;
                    }
                    case 2:{

                        alertDialogBuilder = new AlertDialog.Builder(MenuAdmin.this);
                        alertDialogBuilder.setMessage("Departman üzerinde yapmak istediğiniz işlemi seçiniz");

                        alertDialogBuilder.setPositiveButton("Departman Ekle",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent positveActivity = new Intent(getApplicationContext(), AdminDepartmanEkle.class);
                                        startActivity(positveActivity);

                                    }
                                });
                        alertDialogBuilder.setNegativeButton("Departman Listele",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent negativeActivity = new Intent(getApplicationContext(), AdminDepartmanListele.class);
                                        startActivity(negativeActivity);
                                    }
                                });


                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;
                }

                    case 3 : {

                        Intent i = new Intent(getApplicationContext(),Giris.class);
                        startActivity(i);
                        finish();

                        break;
                    }





                    default:
                        break;
                }


            }
        });

    }

}