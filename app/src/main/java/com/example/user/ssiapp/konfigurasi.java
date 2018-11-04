package com.example.user.ssiapp;

public class konfigurasi {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP localhost KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD = "";
    public static final String URL_GET_ALL = "";
    public static final String URL_GET_EMP = "";
    public static final String URL_UPDATE_EMP = "";
    public static final String URL_DELETE_EMP = "";
    public static final String URL_ADD_RPL = "";
    public static final String URL_GET_ALL_RPL = "";
    public static final String URL_GET_EMP_RPL = "";
    public static final String URL_UPDATE_EMP_RPL = "";
    public static final String URL_DELETE_EMP_RPL = "";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_TID = "tidq";
    public static final String KEY_EMP_SN = "snq";
    public static final String KEY_EMP_MESIN = "mesinq";
    public static final String KEY_EMP_TIPE_MESIN = "tipemesinq";
    public static final String KEY_EMP_NAMA_TIM = "namatimq";
    public static final String KEY_EMP_NOMOR_KONTAK = "nomorkontakq";
    public static final String KEY_EMP_PROBLEM = "problemq";
    public static final String KEY_EMP_STATUS = "statusq";
    public static final String KEY_EMP_SUKSES_TRX = "suksestrxq";
    public static final String KEY_EMP_WKT_INSERT = "wktinsertq";
    public static final String KEY_EMP_IMAGE = "image";

    public static final String KEY_EMP_ID_RPL = "id";
    public static final String KEY_EMP_TID_RPL = "tid";
    public static final String KEY_EMP_NAMA_TIM_RPL = "namatim";
    public static final String KEY_EMP_NOMOR_KONTAK_RPL = "nomorkontak";
    public static final String KEY_EMP_DENOM_RPL = "denom";
    public static final String KEY_EMP_PAGU_RPL = "pagu";
    public static final String KEY_EMP_STATUS_ADMIN_RPL = "statusadmin";
    public static final String KEY_EMP_STATUS_RPL = "status";
    public static final String KEY_EMP_WAKTU_INSERT_RPL = "wktinsert";
    public static final String KEY_EMP_IMAGE_RPL = "image";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_TID = "tidq";
    public static final String TAG_SN = "snq";
    public static final String TAG_MESIN = "mesinq";
    public static final String TAG_TIPE_MESIN = "tipemesinq";
    public static final String TAG_NAMA_TIM = "namatimq";
    public static final String TAG_NOMOR_KONTAK = "nomorkontakq";
    public static final String TAG_PROBLEM = "problemq";
    public static final String TAG_STATUS = "statusq";
    public static final String TAG_SUKSES_TRX = "suksestrxq";
    public static final String TAG_WKT_INSERT = "wktinsertq";
    public static final String TAG_IMAGE = "image";

    public static final String TAG_ID_RPL = "id";
    public static final String TAG_TID_RPL = "tid";
    public static final String TAG_NAMA_TIM_RPL = "namatim";
    public static final String TAG_NOMOR_KONTAK_RPL = "nomorkontak";
    public static final String TAG_DENOM_RPL = "denom";
    public static final String TAG_PAGU_RPL = "pagu";
    public static final String TAG_STATUS_ADMIN_RPL = "statusadmin";
    public static final String TAG_STATUS_RPL = "status";
    public static final String TAG_WAKTU_INSERT_RPL = "wktinsert";
    public static final String TAG_IMAGE_RPL = "image";



    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
    public static final String EMP_ID_RPL = "emp_id";

    //Usernamae
    public static final  String USER_ADMIN = "adminssi";
    public static final  String PASS_ADMIN = "ssi551";
}
