package com.example.gunawan_pj_buku;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Pj_Buku_Detail extends AppCompatActivity {
    ProgressBar pb;
    EditText etID_Peminjaman,etNama_Buku,etNama_Peminjam,etAlamat,etStatus;
    Button btConfirm, btUbah,btHapus;
    String ID_Peminjaman,Nama_Buku,Nama_Peminjam,Alamat,Status;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pj_buku_detail);

        pb               = (ProgressBar) findViewById(R.id.pb);
        etID_Peminjaman  = (EditText) findViewById(R.id.et_ID_Peminjaman);
        etNama_Buku      = (EditText) findViewById(R.id.et_Nama_Buku);
        etNama_Peminjam  = (EditText) findViewById(R.id.et_Nama_Peminjam);
        etAlamat         = (EditText) findViewById(R.id.et_Alamat);
        etStatus         = (EditText) findViewById(R.id.et_Status);

        btUbah    = (Button) findViewById(R.id.bt_ubah);
        btConfirm = (Button) findViewById(R.id.bt_konfirmasi);
        btHapus   = (Button) findViewById(R.id.bt_hapus);

        //tangkap bundle
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        //letakan isi bundle
        ID_Peminjaman   = bundle.getString("b_ID_Peminjaman");
        Nama_Buku       = bundle.getString("b_Nama_Buku");
        Nama_Peminjam   = bundle.getString("b_Nama_Peminjam");
        Alamat          = bundle.getString("b_Alamat");
        Status          = bundle.getString("b_Status");

        //letakan pada textview
        etID_Peminjaman.setText(ID_Peminjaman);
        etNama_Buku.setText(Nama_Buku);
        etNama_Peminjam.setText(Nama_Peminjam);
        etAlamat.setText(Alamat);
        etStatus.setText(Status);

        //operasi ubah data
        btUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_Peminjaman = etID_Peminjaman.getText().toString();
                String Nama_Buku     = etNama_Buku.getText().toString();
                String Nama_Peminjam = etNama_Peminjam.getText().toString();
                String Alamat        = etAlamat.getText().toString();

                pb.setVisibility(ProgressBar.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://web02gunawan.000webhostapp.com/customer/pj_buku.php?action=ubah&ID_Peminjaman=" + ID_Peminjaman +
                "&Nama_Buku=" + Nama_Buku + "&Nama_Peminjam=" + Nama_Peminjam + "&Alamat="+ Alamat +"";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String id, nama,telp;
                                if (response.optString("result").equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Data " + ID_Peminjaman + " Berubah !", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Coba Lagi Boss !", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Events :", error.toString());
                        Toast.makeText(getApplicationContext(),"Masalah Internet Boss !",Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });

//      operasi ubah data
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(ProgressBar.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String str_status = "Done";
                String url = "https://web02gunawan.000webhostapp.com/customer/pj_buku.php?action=konfirmasi&ID_Peminjaman=" + ID_Peminjaman +
                "&Status="+ str_status +"";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String id, nama,telp;
                                if (response.optString("result").equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Data Buku "+ ID_Peminjaman +"Berhasil Dikonfirmasi", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Coba Lagi Boss !", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Events :", error.toString());
                        Toast.makeText(getApplicationContext(),"Masalah Internet Boss !",Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });

        btHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(ProgressBar.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.getCache().clear();
                String str_status = "Done";
                String url = "https://web02gunawan.000webhostapp.com/customer/pj_buku.php?action=hapus&ID_Peminjaman=" + ID_Peminjaman + "";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String id, nama,telp;
                                if (response.optString("result").equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Data Buku "+ ID_Peminjaman + "Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Coba Lagi Boss !", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Events :", error.toString());
                        Toast.makeText(getApplicationContext(),"Masalah Internet Boss !",Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });
    }
}