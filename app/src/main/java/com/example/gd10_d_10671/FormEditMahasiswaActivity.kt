package com.example.gd10_d_10671

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gd10_d_10671.databinding.ActivityFormEditMahasiswaBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.util.*
import kotlin.collections.ArrayList

class FormEditMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormEditMahasiswaBinding
    private var b:Bundle? = null
    private val listMahasiswa = ArrayList<MahasiswaData>()
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityFormEditMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Form Edit Mahasiswa"

        b = intent.extras
        val nim = b?.getString("nim")

        binding.tvEditTgl.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayofMonth ->
                binding.tglEditView.text =
                    dateToString(year,month,dayofMonth)
            }
            dateDialog(this,datePicker).show()
        }

        nim?.let { getDetailData(it) }

        binding.btnUpdate.setOnClickListener {
            with(binding) {
                val nama = txtEditNama.text.toString()
                val alamat = txtEditAlamat.text.toString()
                val prodi = txtEditProdi.text.toString()
                val tgllahir = tglEditView.text.toString()


                RClient.instance.updateData(nim,nama,alamat,prodi,tgllahir).enqueue(
                    object : retrofit2.Callback<ResponseCreate> {
                        override fun onResponse(
                            call: retrofit2.Call<ResponseCreate>,
                            response: retrofit2.Response<ResponseCreate>
                        ) {
                            if(response.isSuccessful) {

                                Toast.makeText(applicationContext,"${response.body()?.pesan}",
                                    Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }
                        override fun onFailure(call:
                                               retrofit2.Call<ResponseCreate>, t: Throwable) {
                        }
                    })
            }
        }
    }

    fun getDetailData(nim:String) {
        RClient.instance.getData(nim).enqueue(object :
            retrofit2.Callback<ResponseDataMahasiswa> {
            override fun onResponse(
                call: retrofit2.Call<ResponseDataMahasiswa>,
                response: retrofit2.Response<ResponseDataMahasiswa>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listMahasiswa.addAll(it.data) }
                    with(binding) {
                        txtNobp.setText(listMahasiswa[0].nim)
                        txtEditNama.setText(listMahasiswa[0].nama)
                        txtEditAlamat.setText(listMahasiswa[0].alamat)
                        txtEditProdi.setText(listMahasiswa[0].prodi)
                        tglEditView.setText(listMahasiswa[0].tgllhr)
                    }
                }
            }
            override fun onFailure(call:
                                   retrofit2.Call<ResponseDataMahasiswa>, t: Throwable) {
            }

        })
    }
    private fun dateToString(year: Int, month: Int, dayofMonth:
    Int): String {
        return year.toString()+"-"+(month+1)+""+dayofMonth.toString()
    }
    private fun dateDialog(context:Context, datePicker:DatePickerDialog.OnDateSetListener):DatePickerDialog
    {
        val calender = Calendar.getInstance()

        return DatePickerDialog(
            context, datePicker,
            calender[Calendar.YEAR],
            calender[Calendar.MONTH],
            calender[Calendar.DAY_OF_MONTH],
        )
    }
}
