package com.example.pokeinfo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.pokeinfo.databinding.ActivityInfoBinding
import com.google.android.material.snackbar.Snackbar

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_info)

        val bundle = intent.extras
        val namepokemon = bundle!!.getString("name_pokemon")

        getInfo(namepokemon)

    }

    private fun getInfo(name: String?){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://pokeapi.co/api/v2/pokemon/$name"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val  spritesArray = response.getJSONObject("sprites")
                val urlImage = spritesArray.getString("front_default")
                Glide.with(this).load(urlImage).into(binding.photopkmn)


            },
            {
                showSnackBar("Connection Error", this)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun showSnackBar(message: String?, activity: Activity?) {
        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}