package com.example.gunawan_pj_buku;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;


//class Load_Item extends AppCompatActivity {
//
//    public void loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
//    }
//}

public class Pj_Buku_Adapter extends RecyclerView.Adapter<Pj_Buku_Adapter.GridviewHolder>{

    private List<Pj_Buku> pj_buku;
    private Context context;

    public Pj_Buku_Adapter(Context context, List<Pj_Buku> customers) {
        this.pj_buku = customers;
        this.context = context;
    }

    @NonNull
    @Override
    public Pj_Buku_Adapter.GridviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pj_buku_layout, parent, false);
        GridviewHolder viewHolder = new GridviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Pj_Buku_Adapter.GridviewHolder holder, int position) {
        final int pos = position;
        final String ID_Peminjaman = pj_buku.get(position).getID_Peminjaman();
        final String Nama_Buku     = pj_buku.get(position).getNama_Buku();
        final String Nama_Peminjam = pj_buku.get(position).getNama_Peminjam();
        final String Alamat        = pj_buku.get(position).getAlamat();
        final String Status        = pj_buku.get(position).getStatus();

        holder.tvID_Peminjaman.setText(ID_Peminjaman);
        holder.tvNama_Buku.setText(Nama_Buku);
        holder.tvNama_Peminjam.setText(Nama_Peminjam);
        holder.tvAlamat.setText(Alamat);
        //holder.tvStatus.setText(Status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Informasi Peminjaman");
                alertDialog.setMessage(ID_Peminjaman + " - Status Buku : " + Status);
                alertDialog.setNegativeButton("Lihat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle b = new Bundle();
                        b.putString("b_ID_Peminjaman",ID_Peminjaman);
                        b.putString("b_Nama_Buku",Nama_Buku);
                        b.putString("b_Nama_Peminjam",Nama_Peminjam);
                        b.putString("b_Alamat",Alamat);
                        b.putString("b_Status",Status);

                        Intent intent = new Intent(context, Pj_Buku_Detail.class);
                        intent.putExtras(b);

                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN){
                            ((Activity)context).startActivityForResult(intent,1,b);
                        }
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
        });

    }

    @Override
    public int getItemCount() {
        return pj_buku.size();
    }

    public class GridviewHolder extends RecyclerView.ViewHolder {
        TextView tvID_Peminjaman;
        TextView tvNama_Buku;
        TextView tvNama_Peminjam;
        TextView tvAlamat;
        // TextView tvStatus;


        public GridviewHolder(@NonNull View itemView) {
            super(itemView);

            tvID_Peminjaman    = (TextView) itemView.findViewById(R.id.tv_ID_Peminjaman);
            tvNama_Buku        = (TextView) itemView.findViewById(R.id.tv_Nama_Buku);
            tvNama_Peminjam    = (TextView) itemView.findViewById(R.id.tv_Nama_Peminjam);
            tvAlamat           = (TextView) itemView.findViewById(R.id.tv_Alamat);
            // tvStatus           = (TextView) itemView.findViewById(R.id.tv_Status);

        }
    }
}
