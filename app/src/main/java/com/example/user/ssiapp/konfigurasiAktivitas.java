package com.example.user.ssiapp;

public class konfigurasiAktivitas {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP  KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD = "http://localhost/Android/createAktivitas.php";
    public static final String URL_GET_ALL_USER_JTW = "http://localhost/Android/selectUserJtw.php";
    public static final String URL_GET_ALL_USER_JTW_DIRECTION = "http://localhost/Android/selectUserDirection.php";
    public static final String URL_GET_ALL_USER_JTW_TID = "http://localhost/Android/selectUserJtwTid.php";
    public static final String URL_GET_ALL_USER_JTW_TID_PROGRES = "http://localhost/Android/selectUserJtwTidProgres.php";
    public static final String URL_GET_EMP = "http://localhost/Android/selectIdAktivitas.php?id=";
    public static final String URL_GET_EMP_ALL = "http://localhost/Android/selectAllAktivitas.php";
    public static final String URL_UPDATE_EMP = "http://localhost/Android/updateAktivitas.php";
    public static final String URL_UPDATE_EMP_LATLNG_USER = "http://localhost/Android/updateAktivitasLatLngUser.php";
    public static final String URL_UPDATE_EMP_PROGRES_LOKASI = "http://localhost/Android/updateAktivitasProgresLokasi.php";
    public static final String URL_UPDATE_EMP_REPORT_LOKASI = "http://localhost/Android/updateAktivitasReportLokasi.php";
    public static final String URL_DELETE_EMP = "http://localhost/Android/delete.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "namau";
    public static final String KEY_EMP_NPP = "nppu";
    public static final String KEY_EMP_SENTRA = "sentrau";
    public static final String KEY_EMP_LAT = "latu";
    public static final String KEY_EMP_LON = "lonu";
    public static final String KEY_EMP_LOGIN = "loginu";
    public static final String KEY_EMP_LOGOUT = "logoutu";
    public static final String KEY_EMP_STATUS = "statusu";
    public static final String KEY_EMP_TID = "tidu";
    public static final String KEY_EMP_LAT_TID_PROB = "latTidProbu";
    public static final String KEY_EMP_LON_TID_PROB = "lonTidProbu";
    public static final String KEY_EMP_RESPON_PROB = "responProbu";
    public static final String KEY_EMP_PROGRES_LOKASI = "progresLokasiu";
    public static final String KEY_EMP_REPORT_LOKASI = "reportLokasiu";
    public static final String KEY_EMP_KETERANGAN = "keteranganu";
    public static final String KEY_EMP_DESKRIPSI = "deskripsiu";
    public static final String KEY_EMP_VOLTASE = "voltaseu";
    public static final String KEY_EMP_GROUNDING = "groundingu";
    public static final String KEY_EMP_CCTV = "cctvu";
    public static final String KEY_EMP_AC = "acu";
    public static final String KEY_EMP_ANTI_SKIMING = "antiSkimingu";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "namau";
    public static final String TAG_NPP = "nppu";
    public static final String TAG_SENTRA = "sentrau";
    public static final String TAG_LAT = "latu";
    public static final String TAG_LON = "lonu";
    public static final String TAG_LOGIN = "loginu";
    public static final String TAG_LOGOUT = "logoutu";
    public static final String TAG_STATUS = "statusu";
    public static final String TAG_TID = "tidu";
    public static final String TAG_LAT_TID_PROB = "latTidProbu";
    public static final String TAG_LON_TID_PROB = "lonTidProbu";
    public static final String TAG_RESPON_PROB = "responProbu";
    public static final String TAG_PROGRES_LOKASI = "progresLokasiu";
    public static final String TAG_REPORT_LOKASI = "reportLokasiu";
    public static final String TAG_KETERANGAN = "keteranganu";
    public static final String TAG_DESKRIPSI = "deskripsiu";
    public static final String TAG_VOLTASE = "voltaseu";
    public static final String TAG_GROUNDING = "groundingu";
    public static final String TAG_CCTV = "cctvu";
    public static final String TAG_AC = "acu";
    public static final String TAG_ANTI_SKIMING = "antiSkimingu";

    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
