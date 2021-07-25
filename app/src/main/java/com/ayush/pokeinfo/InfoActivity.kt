package com.ayush.pokeinfo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.ayush.pokeinfo.databinding.ActivityInfoBinding
import com.google.android.material.snackbar.Snackbar

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding
    lateinit var nameList: List<String>

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
                val  spritesObject = response.getJSONObject("sprites")
                val urlImage = spritesObject.getString("front_default")
                val typeArray = response.getJSONArray("types")

                Log.i("debug here","${typeArray.length()}")


                var typeNames: String = "Type: "

                for(ele in 0 until typeArray.length()){
                    val temp = typeArray.getJSONObject(ele)
                    val typepart = temp.getJSONObject("type").getString("name")
                    typeNames += typepart
                    typeNames += " "
                }


                val id = response.getString("id")
                var wt = response.getString("weight").toDouble()
                var ht = response.getString("height").toDouble()
                ht /= 10
                wt /= 10

                binding.indexnum.text="#$id $name"
                binding.types.text="$typeNames"
                binding.height.text="Height: ${ht.toString()} m"
                binding.weight.text="Weight: ${wt.toString()} Kg"

                Glide.with(this).load(urlImage).into(binding.photopkmn)
            },
            {
                showSnackBar("Invalid Pokemon name", this)
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