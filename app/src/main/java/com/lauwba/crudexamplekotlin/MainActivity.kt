package com.lauwba.crudexamplekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity(),View.OnClickListener {

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private var editTextName: EditText? = null
    private var editTextDesg: EditText? = null
    private var editTextSal: EditText? = null

    private var buttonAdd: Button? = null
    private var buttonView: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inisialisasi dari View
        editTextName = findViewById(R.id.editTextName) as EditText
        editTextDesg = findViewById(R.id.editTextDesg) as EditText
        editTextSal = findViewById(R.id.editTextSalary) as EditText

        buttonAdd = findViewById(R.id.buttonAdd) as Button
        buttonView = findViewById(R.id.buttonView) as Button

        //Setting listeners to button
        buttonAdd!!.setOnClickListener(this)
        buttonView!!.setOnClickListener(this)

    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private fun addEmployee() {

        val name = editTextName?.getText().toString().trim { it <= ' ' }
        val desg = editTextDesg?.getText().toString().trim { it <= ' ' }
        val sal = editTextSal?.getText().toString().trim { it <= ' ' }

        class AddEmployee : AsyncTask<Void, Void, String>() {

            lateinit var loading: ProgressDialog

            override fun onPreExecute() {
                super.onPreExecute()
                loading = ProgressDialog.show(
                    this@MainActivity,
                    "Menambahkan...",
                    "Tunggu...",
                    false,
                    false
                )
            }

            override fun onPostExecute(s: String) {
                super.onPostExecute(s)
                loading.dismiss()
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_LONG).show()
            }

            @SuppressLint("WrongThread")
            override fun doInBackground(vararg v: Void): String {
                val params = HashMap<String, String?>()
                params[Config.KEY_EMP_NAMA] = name
                params[Config.KEY_EMP_POSISI] = desg
                params[Config.KEY_EMP_GAJIH] = sal

                val rh = RequestHandler()
                return rh.sendPostRequest(Config.URL_ADD, params)

            }
        }

        val ae = AddEmployee()
        ae.execute()
    }


    override fun onClick(v: View) {
        if (v === buttonAdd) {
            addEmployee()
        }

        if (v === buttonView) {
            startActivity(Intent(this, TampilSemuaPgw::class.java))
        }
    }
}
