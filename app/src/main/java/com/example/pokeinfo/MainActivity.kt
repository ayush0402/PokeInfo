package com.example.pokeinfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokeinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.btnA.setOnClickListener {
            var namepkmn = binding.nameinput.text.toString()

            sendName(namepkmn)
        }
        binding.btnB.setOnClickListener {
            var namepkmn = binding.nameinput.text.toString()

            sendName(namepkmn)
        }

    }

    private fun sendName(value: String){
        val intent = Intent(this@MainActivity, InfoActivity::class.java)
        intent.putExtra("name_pokemon", value)
        startActivity(intent)
    }
}