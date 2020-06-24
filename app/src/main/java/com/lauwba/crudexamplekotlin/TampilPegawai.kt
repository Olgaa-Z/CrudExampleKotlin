package com.lauwba.crudexamplekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

class TampilPegawai : AppCompatActivity(), View.OnClickListener {

    private var editTextId: EditText? = null
    private var editTextName: EditText? = null
    private var editTextDesg: EditText? = null
    private var editTextSalary: EditText? = null

    private var buttonUpdate: Button? = null
    private var buttonDelete: Button? = null

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_pegawai)

        val intent = intent

        id = intent.getStringExtra(Config.EMP_ID)

        editTextId = findViewById(R.id.editTextId) as EditText
        editTextName = findViewById(R.id.editTextName) as EditText
        editTextDesg = findViewById(R.id.editTextDesg) as EditText
        editTextSalary = findViewById(R.id.editTextSalary) as EditText

        buttonUpdate = findViewById(R.id.buttonUpdate) as Button
        buttonDelete = findViewById(R.id.buttonDelete) as Button

        buttonUpdate!!.setOnClickListener(this)
        buttonDelete!!.setOnClickListener(this)

        editTextId!!.setText(id)

        getEmployee()
    }

    private fun getEmployee() {
        @Suppress("DEPRECATION")
        class GetEmployee : AsyncTask<Void, Void, String>() {
            lateinit var loading: ProgressDialog
            override fun onPreExecute() {
                super.onPreExecute()
                loading =
                    ProgressDialog.show(this@TampilPegawai, "Fetching...", "Wait...", false, false)
            }

            override fun onPostExecute(s: String) {
                super.onPostExecute(s)
                loading.dismiss()
                showEmployee(s)
            }

            override fun doInBackground(vararg params: Void): String {
                val rh = RequestHandler()
                return rh.sendGetRequestParam(Config.URL_GET_EMP , id)
            }
        }

        val ge = GetEmployee()
        ge.execute()
    }

    private fun showEmployee(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY)
            val c = result.getJSONObject(0)
            val name = c.getString(Config.TAG_NAMA)
            val desg = c.getString(Config.TAG_POSISI)
            val sal = c.getString(Config.TAG_GAJIH)

            editTextName?.setText(name)
            editTextDesg?.setText(desg)
            editTextSalary?.setText(sal)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun updateEmployee() {
        val name = editTextName?.getText().toString().trim { it <= ' ' }
        val desg = editTextDesg?.getText().toString().trim { it <= ' ' }
        val salary = editTextSalary?.getText().toString().trim { it <= ' ' }

        class UpdateEmployee : AsyncTask<Void, Void, String>() {
            lateinit var loading: ProgressDialog
            override fun onPreExecute() {
                super.onPreExecute()
                loading =
                    ProgressDialog.show(this@TampilPegawai, "Updating...", "Wait...", false, false)
            }

            override fun onPostExecute(s: String) {
                super.onPostExecute(s)
                loading.dismiss()
                Toast.makeText(this@TampilPegawai, s, Toast.LENGTH_LONG).show()
            }

//            override fun doInBackground(vararg params: Void): String {
//                val hashMap = HashMap<String, String>()
//                hashMap[konfigurasi.KEY_EMP_ID] = id
//                hashMap[konfigurasi.KEY_EMP_NAMA] = name
//                hashMap[konfigurasi.KEY_EMP_POSISI] = desg
//                hashMap[konfigurasi.KEY_EMP_GAJIH] = salary
//
//                val rh = RequestHandler()
//
//                return rh.sendPostRequest(Config.URL_UPDATE_EMP, hashMap)
//            }

            @SuppressLint("WrongThread")
            override fun doInBackground(vararg v: Void): String {
//                val params = HashMap<String, String>()
                val params = HashMap<String, String?>()
                params[Config.KEY_EMP_ID] = id
                params[Config.KEY_EMP_NAMA] = name
                params[Config.KEY_EMP_POSISI] = desg
                params[Config.KEY_EMP_GAJIH] = salary

                val rh = RequestHandler()
                return rh.sendPostRequest(Config.URL_UPDATE_EMP, params)

            }
        }

        val ue = UpdateEmployee()
        ue.execute()
    }

    private fun deleteEmployee() {
        class DeleteEmployee : AsyncTask<Void, Void, String>() {
            lateinit var loading: ProgressDialog

            override fun onPreExecute() {
                super.onPreExecute()
                loading = ProgressDialog.show(
                    this@TampilPegawai,
                    "Updating...",
                    "Tunggu...",
                    false,
                    false
                )
            }

            override fun onPostExecute(s: String) {
                super.onPostExecute(s)
                loading.dismiss()
                Toast.makeText(this@TampilPegawai, s, Toast.LENGTH_LONG).show()
            }

            override fun doInBackground(vararg params: Void): String {
                val rh = RequestHandler()
                return rh.sendGetRequestParam(Config.URL_DELETE_EMP, id)
            }
        }

        val de = DeleteEmployee()
        de.execute()
    }

    private fun confirmDeleteEmployee() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Pegawai ini?")

        alertDialogBuilder.setPositiveButton("Ya") { arg0, arg1 ->
            deleteEmployee()
            startActivity(Intent(this@TampilPegawai, TampilSemuaPgw::class.java))
        }

        alertDialogBuilder.setNegativeButton("Tidak",
        object: DialogInterface.OnClickListener {

            override fun onClick(arg0: DialogInterface, arg1:Int) {

            }
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onClick(v: View) {
        if (v === buttonUpdate) {
            updateEmployee()
        }

        if (v === buttonDelete) {
            confirmDeleteEmployee()
        }
    }


}
