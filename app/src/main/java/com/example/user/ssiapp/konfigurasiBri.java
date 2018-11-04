package com.example.user.ssiapp;

public class konfigurasiBri {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP  KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_GET_PROB_JTW = "http://localhost/Android/selectProb.php";
    public static final String URL_GET_PROB_JTW_NT = "http://localhost/Android/selectProbNT.php";
    public static final String URL_GET_ALL_PROB_JTW = "http://localhost/Android/selectProbJtw.php";
    public static final String URL_GET_ALL_PROB_JTW_IN = "http://localhost/Android/selectProbJtwIn.php";
    public static final String URL_GET_ALL_PROB_JTW_NT = "http://localhost/Android/selectProbNt1dJtw.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_TID = "tidu";
    public static final String KEY_EMP_KANWIL = "kanwilu";
    public static final String KEY_EMP_LOKASI = "lokasiu";
    public static final String KEY_EMP_KC_SPV = "kcspvu";
    public static final String KEY_EMP_KC_SPV_KODE = "kcspvkodeu";
    public static final String KEY_EMP_MERK_ATM = "merkatmu";
    public static final String KEY_EMP_PENGELOLA = "pengelolau";
    public static final String KEY_EMP_PENGELOLA_KODE = "pengelolakodeu";
    public static final String KEY_EMP_PENGELOLA_VENDOR = "pengelolavendoru";
    public static final String KEY_EMP_PENGELOLA_CPC = "pengelolacpcu";
    public static final String KEY_EMP_PENGELOLA_JENIS = "pengelolajenisu";
    public static final String KEY_EMP_KETERANGAN = "keteranganu";
    public static final String KEY_EMP_DOWN_TIME_HARI = "downtimehariu";
    public static final String KEY_EMP_DOWN_TIME_JAM = "downtimejamu";
    public static final String KEY_EMP_LAST_SUKSES = "lastsuksesu";
    public static final String KEY_EMP_CASH_GL = "cashglu";
    public static final String KEY_EMP_PROBLEM = "problemu";
    public static final String KEY_EMP_STATUS = "statusu";
    public static final String KEY_EMP_WAKTU_INSERT = "waktuinsertu";
    public static final String KEY_EMP_TGL_PROBLEM = "tglproblemu";
    public static final String KEY_EMP_LEMBAR = "lembaru";
    public static final String KEY_EMP_DISP1 = "disp1u";
    public static final String KEY_EMP_DISP2 = "disp2u";
    public static final String KEY_EMP_DISP3 = "disp3u";
    public static final String KEY_EMP_DISP4 = "disp4u";
    public static final String KEY_EMP_DENOM = "denomu";
    public static final String KEY_EMP_LEMBAR1 = "lembar1u";
    public static final String KEY_EMP_LEMBAR2 = "lembar2u";
    public static final String KEY_EMP_LEMBAR3 = "lembar3u";
    public static final String KEY_EMP_LEMBAR4 = "lembar4u";
    public static final String KEY_EMP_RTL_ID_TICKET = "rtlidticketu";
    public static final String KEY_EMP_RTL_PROBLEM = "rtlproblemu";
    public static final String KEY_EMP_RTL_PART = "rtlpartu";
    public static final String KEY_EMP_RTL_SLA = "rtlslau";
    public static final String KEY_EMP_RTL_KETERANGAN = "rtlketeranganu";
    public static final String KEY_EMP_PAGU = "paguu";
    public static final String KEY_EMP_SISA_SALDO = "sisasaldou";
    public static final String KEY_EMP_UPDATE_RTL_TICKET = "updatertlticketu";
    public static final String KEY_EMP_PAGU_DINAMIS= "pagudinamisu";
    public static final String KEY_EMP_PAGU_LEMBAR = "pagulembaru";
    public static final String KEY_EMP_PAGU_DAYS = "pagudaysu";
    public static final String KEY_EMP_LATITUDE = "latitudeu";
    public static final String KEY_EMP_LONGITUDE = "longitudeu";


    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_TID = "tidu";
    public static final String TAG_KANWIL = "kanwilu";
    public static final String TAG_LOKASI = "lokasiu";
    public static final String TAG_KC_SPV = "kcspvu";
    public static final String TAG_KC_SPV_KODE = "kcspvkodeu";
    public static final String TAG_MERK_ATM = "merkatmu";
    public static final String TAG_PENGELOLA = "pengelolau";
    public static final String TAG_PENGELOLA_KODE = "pengelolakodeu";
    public static final String TAG_PENGELOLA_VENDOR = "pengelolavendoru";
    public static final String TAG_PENGELOLA_CPC = "pengelolacpcu";
    public static final String TAG_PENGELOLA_JENIS = "pengelolajenisu";
    public static final String TAG_KETERANGAN = "keteranganu";
    public static final String TAG_DOWN_TIME_HARI = "downtimehariu";
    public static final String TAG_DOWN_TIME_JAM = "downtimejamu";
    public static final String TAG_LAST_SUKSES = "lastsuksesu";
    public static final String TAG_CASH_GL = "cashglu";
    public static final String TAG_PROBLEM = "problemu";
    public static final String TAG_STATUS = "statusu";
    public static final String TAG_WAKTU_INSERT = "waktuinsertu";
    public static final String TAG_TGL_PROBLEM = "tglproblemu";
    public static final String TAG_LEMBAR = "lembaru";
    public static final String TAG_DISP1 = "disp1u";
    public static final String TAG_DISP2 = "disp2u";
    public static final String TAG_DISP3 = "disp3u";
    public static final String TAG_DISP4 = "disp4u";
    public static final String TAG_DENOM = "denomu";
    public static final String TAG_LEMBAR1 = "lembar1u";
    public static final String TAG_LEMBAR2 = "lembar2u";
    public static final String TAG_LEMBAR3 = "lembar3u";
    public static final String TAG_LEMBAR4 = "lembar4u";
    public static final String TAG_RTL_ID_TICKET = "rtlidticketu";
    public static final String TAG_RTL_PROBLEM = "rtlproblemu";
    public static final String TAG_RTL_PART = "rtlpartu";
    public static final String TAG_RTL_SLA = "rtlslau";
    public static final String TAG_RTL_KETERANGAN = "rtlketeranganu";
    public static final String TAG_PAGU = "paguu";
    public static final String TAG_SISA_SALDO = "sisasaldou";
    public static final String TAG_UPDATE_RTL_TICKET = "updatertlticketu";
    public static final String TAG_PAGU_DINAMIS= "pagudinamisu";
    public static final String TAG_PAGU_LEMBAR = "pagulembaru";
    public static final String TAG_PAGU_DAYS = "pagudaysu";
    public static final String TAG_LATITUDE = "latitudeu";
    public static final String TAG_LONGITUDE = "longitudeu";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
