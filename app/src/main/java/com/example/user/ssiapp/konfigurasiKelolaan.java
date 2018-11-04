package com.example.user.ssiapp;

public class konfigurasiKelolaan {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP 103.81.194.34 KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD = "http://103.107.100.194/Android/createAktivitas.php";
    public static final String URL_GET_ALL_KELOLAAN_JTW = "http://103.107.100.194/Android/selectKelolaanJtw.php";
    public static final String URL_GET_EMP = "http://103.107.100.194/Android/selectIdKelolaan.php?id=";
    public static final String URL_UPDATE_EMP = "http://103.107.100.194/Android/updateKelolaan.php";
    public static final String URL_UPDATE_EMP_LATLNG_USER = "http://103.107.100.194/Android/updateAktivitasLatLngUser.php";
    public static final String URL_UPDATE_EMP_PROGRES_LOKASI = "http://103.107.100.194/Android/updateAktivitasProgresLokasi.php";
    public static final String URL_UPDATE_EMP_REPORT_LOKASI = "http://103.107.100.194/Android/updateAktivitasReportLokasi.php";
    public static final String URL_DELETE_EMP = "http://103.107.100.194/Android/delete.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_TID = "tidu";
    public static final String KEY_EMP_LOKASI = "lokasiu";
    public static final String KEY_EMP_CPC = "cpcu";
    public static final String KEY_EMP_WILAYAH = "wilayahu";
    public static final String KEY_EMP_KODE_UKER = "kodeukeru";
    public static final String KEY_EMP_MERK_MESIN = "merkmesinu";
    public static final String KEY_EMP_DENOM = "denomu";
    public static final String KEY_EMP_PAGU = "paguu";
    public static final String KEY_EMP_SN_MESIN = "snmesinu";
    public static final String KEY_EMP_JARKOM = "jarkomu";
    public static final String KEY_EMP_DB = "dbu";
    public static final String KEY_EMP_SERV_HOUR = "servhouru";
    public static final String KEY_EMP_GARANSI = "garansiu";
    public static final String KEY_EMP_LAT = "latu";
    public static final String KEY_EMP_LON = "lonu";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_TID = "tidu";
    public static final String TAG_LOKASI = "lokasiu";
    public static final String TAG_CPC = "cpcu";
    public static final String TAG_WILAYAH = "wilayahu";
    public static final String TAG_KODE_UKER = "kodeukeru";
    public static final String TAG_MERK_MESIN = "merkmesinu";
    public static final String TAG_DENOM = "denomu";
    public static final String TAG_PAGU = "paguu";
    public static final String TAG_SN_MESIN = "snmesinu";
    public static final String TAG_JARKOM = "jarkomu";
    public static final String TAG_DB = "dbu";
    public static final String TAG_SERV_HOUR = "servhouru";
    public static final String TAG_GARANSI = "garansiu";
    public static final String TAG_LAT = "latu";
    public static final String TAG_LON = "lonu";

    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
