package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.mehmet.izinyonetimsistemi.Json;
import com.example.mehmet.izinyonetimsistemi.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class DepartmanListviewAdapter extends BaseAdapter {
    DepartmanListviewAdapter adapter;
    Context mContext;
    LayoutInflater inflater;
    private List<Kisiler> kisilerlist = null;
    private ArrayList<Kisiler> arraylist;
    public int pozisyon,success;
    Json jsonParser = new Json();
    private static final String url_calisan_sil = "http://www.sihader.org/mehmet/departman_sil.php";
    private static final String TAG_SUCCESS = "success" ;
    private static final String TAG_MESSAGE = "message" ;

    public DepartmanListviewAdapter(Context context, List<Kisiler> kisilerlist) {
        mContext = context;
        this.kisilerlist = kisilerlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Kisiler>();
        this.arraylist.addAll(kisilerlist);
    }

    public class ViewHolder {
        TextView isim;
    }

    @Override
    public int getCount() {
        return kisilerlist.size();
    }

    @Override
    public Kisiler getItem(int position) {
        return kisilerlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.admin_tek_satir, null);
            holder.isim = (TextView) view.findViewById(R.id.isimsoyisim);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.isim.setText(kisilerlist.get(position).getIsım());

        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                pozisyon= position;


                AlertDialog show = new AlertDialog.Builder(mContext)
                        .setTitle("Departman Sil")
                        .setMessage("Departmanı Silmek İstediğinize Emin misiniz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Calisan_Delete().execute();
                                Toast.makeText(mContext,"Başarıyla Silindi Kardo", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        kisilerlist.clear();
        if (charText.length() == 0) {
            kisilerlist.addAll(arraylist);
        } else {
            for (Kisiler wp : arraylist) {
                if (wp.getIsım().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    kisilerlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    class Calisan_Delete extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {

        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            String calisan_id = kisilerlist.get(pozisyon).getId();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", calisan_id));

            JSONObject json = jsonParser.makeHttpRequest(url_calisan_sil, "POST", params);

            try {

                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated

                    Log.d("Silme Başarılı", json.getString(TAG_MESSAGE));

                }
                else {
                    Log.d("Silme Başarısız", json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            kisilerlist.remove(kisilerlist.get(pozisyon));
            notifyDataSetChanged();


        }
    }



}