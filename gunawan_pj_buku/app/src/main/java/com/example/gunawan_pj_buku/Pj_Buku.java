package com.example.gunawan_pj_buku;

public class Pj_Buku {

    String ID_Peminjaman,Nama_Buku,Nama_Peminjam,Alamat,Status;
    public Pj_Buku(String ID_Peminjaman,String Nama_Buku,String Nama_Peminjam,String Alamat,String Status){
        this.ID_Peminjaman  = ID_Peminjaman;
        this.Nama_Buku      = Nama_Buku;
        this.Nama_Peminjam  = Nama_Peminjam;
        this.Alamat         = Alamat;
        this.Status         = Status;
    }

    public String getID_Peminjaman(){
        return ID_Peminjaman;
    }
    public String getNama_Buku(){ return Nama_Buku; }
    public String getNama_Peminjam(){
        return Nama_Peminjam;
    }
    public String getAlamat(){
        return Alamat;
    }
    public String getStatus(){
        return Status;
    }

}
