package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mehmet.izinyonetimsistemi.CalisanIzinDetay;
import com.example.mehmet.izinyonetimsistemi.R;
import com.example.mehmet.izinyonetimsistemi.YonetimIzinDetay;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class YonetimListviewAdapter extends BaseAdapter implements View.OnClickListener  {

    public Button izinList;
    Context mContext;
    LayoutInflater inflater;
    private List<BasvurularYonetim> basvurularlist = null;
    private ArrayList<BasvurularYonetim> arraylist;
    public int pozisyon;
    public String id;


    public YonetimListviewAdapter(Context context, List<BasvurularYonetim> basvurularlist) {
        mContext = context;
        this.basvurularlist = basvurularlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<BasvurularYonetim>();
        this.arraylist.addAll(basvurularlist);
    }


    public class ViewHolder {
        TextView basvuruTarih;
        TextView izinSure;
        TextView formOnay;
        TextView formAd;


    }

    @Override
    public int getCount() {
        return basvurularlist.size();
    }

    @Override
    public BasvurularYonetim getItem(int position) {
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
            view = inflater.inflate(R.layout.yonetim_tek_satir, null);
            holder.basvuruTarih = (TextView) view.findViewById(R.id.yonetim_basvuru_tarih);
            holder.izinSure = (TextView) view.findViewById(R.id.yonetim_izin_suresi);
            holder.formAd = (TextView) view.findViewById(R.id.yonetim_calisan_ad);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.basvuruTarih.setText(basvurularlist.get(position).getBasvuruTarih());
        holder.izinSure.setText(basvurularlist.get(position).getIzinSure());
        holder.formAd.setText(basvurularlist.get(position).getformAd());


        final View finalView = view;


        pozisyon = position;
        id = basvurularlist.get(pozisyon).getFormId();

        Log.d("id " + id, " ");

        izinList = (Button) finalView.findViewById(R.id.btn_detay);
        izinList.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent(mContext, YonetimIzinDetay.class);
        i.putExtra("formId", id);
        mContext.startActivity(i);
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        basvurularlist.clear();
        if (charText.length() == 0) {
            basvurularlist.addAll(arraylist);
        } else {
            for (BasvurularYonetim wp : arraylist) {
                if (wp.getBasvuruTarih().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    basvurularlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
