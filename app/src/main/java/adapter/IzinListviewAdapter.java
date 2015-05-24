package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmet.izinyonetimsistemi.CalisanIzinDetay;
import com.example.mehmet.izinyonetimsistemi.CalisanIzinKontrol;
import com.example.mehmet.izinyonetimsistemi.Json;
import com.example.mehmet.izinyonetimsistemi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class IzinListviewAdapter extends BaseAdapter implements View.OnClickListener {

    public Button izinList;
    Context mContext;
    LayoutInflater inflater;
    private List<Basvurular> basvurularlist = null;
    private ArrayList<Basvurular> arraylist;
    public int pozisyon;
    public String id;


    public IzinListviewAdapter(Context context, List<Basvurular> basvurularlist) {
        mContext = context;
        this.basvurularlist = basvurularlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Basvurular>();
        this.arraylist.addAll(basvurularlist);
    }



    public class ViewHolder {
        TextView basvuruTarih;
        TextView izinSure;
        TextView formOnay;
        TextView formId;


    }

    @Override
    public int getCount() {
        return basvurularlist.size();
    }

    @Override
    public Basvurular getItem(int position) {
        return basvurularlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.calisan_tek_satir, null);
            holder.basvuruTarih = (TextView) view.findViewById(R.id.txtBasvuruTarihi);
            holder.izinSure = (TextView) view.findViewById(R.id.txtIzinSuresi);
            holder.formOnay = (TextView) view.findViewById(R.id.txtOnaylanma);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.basvuruTarih.setText(basvurularlist.get(position).getBasvuruTarih());
        holder.izinSure.setText(basvurularlist.get(position).getIzinSure());
        holder.formOnay.setText(basvurularlist.get(position).getformOnay());


        final View finalView = view;



        pozisyon = position;
        id =  basvurularlist.get(pozisyon).getFormId();

        Log.d("id"+ id ," ");

        izinList= (Button) finalView.findViewById(R.id.btnbasvur);
        izinList.setOnClickListener(this);
       /*     @Override
            public void onClick(View view) {
                Intent i = new Intent (mContext,CalisanIzinDetay.class);
                i.putExtra("formId",id);
                mContext.startActivity(i);
            }
        });
        */

        return view;
    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent (mContext,CalisanIzinDetay.class);
        i.putExtra("formId",id);
        mContext.startActivity(i);
    }



    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        basvurularlist.clear();
        if (charText.length() == 0) {
            basvurularlist.addAll(arraylist);
        } else {
            for (Basvurular wp : arraylist) {
                if (wp.getBasvuruTarih().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    basvurularlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}