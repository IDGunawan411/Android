package com.example.gunawan_pj_buku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etID_Peminjaman, etNama_Buku, etAlamat, etNama_Peminjam, etStatus;

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.d("Gunawan ", "onResume: " + Active_Fragment.ACTIVE_FRAGMENT);

            //cek fragment mana yang sebelumnya aktif
            if (Active_Fragment.ACTIVE_FRAGMENT.equals("FirstFragment")) {
                loadFragment(new FirstFragment());
            }
        } catch (Exception e) {
            Log.d("Gunawan ", "onResume: " + e.getMessage());
        }
    }

    // Insert Data
    private void simpanPj_Buku(String ID_Peminjaman, String Nama_Buku, String Alamat, String Nama_Peminjam, String Status) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://web02gunawan.000webhostapp.com/customer/pj_buku.php?action=simpan&ID_Peminjaman=" + ID_Peminjaman +
        "&Nama_Buku=" + Nama_Buku + "&Nama_Peminjam=" + Nama_Peminjam + "&Alamat=" + Alamat + "&Status=" + Status + "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String ID_Peminjaman, Nama_Buku, Nama_Peminjam, Alamat, Status;

                if (response.optString("result").equals("true")) {
                    Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan !", Toast.LENGTH_SHORT).show();

                    // panggil fungsi load pada fragment
                    loadFragment(new FirstFragment());
                } else {
                    Toast.makeText(getApplicationContext(), "Data Gagal Disimpan !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Events: ", error.toString());

                Toast.makeText(getApplicationContext(), "Internet/Data Eror !", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("Gunawan ", "onCreate: load main");
        //load fragment pertama kali
        Active_Fragment.ACTIVE_FRAGMENT = "FirstFragment";
        loadFragment(new FirstFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //cek fragment mana yang aktif?

                // tarik layout
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.input_data_pj_buku, null);
                dialog.setView(view);
                dialog.setCancelable(true);

                // definisi objek
                etID_Peminjaman = (EditText) view.findViewById(R.id.et_ID_Peminjaman);
                etNama_Buku = (EditText) view.findViewById(R.id.et_Nama_Buku);
                etNama_Peminjam = (EditText) view.findViewById(R.id.et_Nama_Peminjam);
                etAlamat = (EditText) view.findViewById(R.id.et_Alamat);
                etStatus = (EditText) view.findViewById(R.id.et_Status);

                dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String ID_Peminjaman, Nama_Buku, Alamat, Nama_Peminjam, Status;

                        ID_Peminjaman = etID_Peminjaman.getText().toString();
                        Nama_Buku = etNama_Buku.getText().toString();
                        Nama_Peminjam = etNama_Peminjam.getText().toString();
                        Alamat = etAlamat.getText().toString();
                        Status = etStatus.getText().toString();


                        // simpan customer
                        simpanPj_Buku(ID_Peminjaman, Nama_Buku, Alamat, Nama_Peminjam, Status);
                    }
                });

                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}