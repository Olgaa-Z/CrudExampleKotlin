package com.lauwba.crudexamplekotlin

class konfigurasi {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    val URL_ADD = "http://192.168.18.38/Android/pegawai/tambahPgw.php"
    val URL_GET_ALL = "http://192.168.18.38/Android/pegawai/tampilSemuaPgw.php"
    val URL_GET_EMP = "http://192.168.18.38/Android/pegawai/tampilPgw.php?id="
    val URL_UPDATE_EMP = "http://192.168.18.38/Android/pegawai/updatePgw.php"
    val URL_DELETE_EMP = "http://192.168.18.38/Android/pegawai/hapusPgw.php?id="

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    val KEY_EMP_ID = "id"
    val KEY_EMP_NAMA = "name"
    val KEY_EMP_POSISI = "desg" //desg itu variabel untuk posisi
    val KEY_EMP_GAJIH = "salary" //salary itu variabel untuk gajih

    //JSON Tags
    val TAG_JSON_ARRAY = "result"
    val TAG_ID = "id"
    val TAG_NAMA = "name"
    val TAG_POSISI = "desg"
    val TAG_GAJIH = "salary"

    //ID karyawan
    //emp itu singkatan dari Employee
    val EMP_ID = "emp_id"
}