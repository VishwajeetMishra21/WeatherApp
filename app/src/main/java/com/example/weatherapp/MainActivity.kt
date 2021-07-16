package com.example.weatherapp


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    lateinit var searchBar : EditText
    lateinit var search : ImageButton
    lateinit var degree : TextView
    lateinit var name : TextView
    lateinit var names : TextView
    lateinit var temp : TextView
    lateinit var humidity : TextView
    lateinit var speed : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.searchBar)
        search = findViewById(R.id.search)
        degree = findViewById(R.id.degree)
        name = findViewById(R.id.name)
        names = findViewById(R.id.names)
        temp = findViewById(R.id.temp)
        humidity = findViewById(R.id.humidity)
        speed = findViewById(R.id.speed)

        //for the default weather of country/state/...
        search()


        //For searching when user search for the state district country
        search.setOnClickListener {
            search()
        }
    }

    fun search() {

        val country = searchBar.text.toString()

        val url = "https://api.openweathermap.org/data/2.5/weather?q=silvassa&appid=7ad94f7fe6f95bdbfa8a8a97a1475db2"

        val link = "https://api.openweathermap.org/data/2.5/weather?q=$country&appid=7ad94f7fe6f95bdbfa8a8a97a1475db2"

        if(country.isEmpty()) {
            if(ConnectionManager().checkConnectivity(this@MainActivity)) {

                val queue = Volley.newRequestQueue(this@MainActivity)
                val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener { response->

                        try {

                            val jsonObject = response.getJSONObject("main")

                            temp.text = jsonObject.getString("temp")
                            humidity.text = jsonObject.getString("humidity")
                            names.text = response.getString("name")

                            val json = response.getJSONObject("wind")
                            speed.text = json.getString("speed")
                            degree.text = json.getString("deg")
                            val data = response.getJSONArray("weather")
                            for(i in 0 until data.length()) {
                                val jsonRe = data.getJSONObject(i)
                                name.text = jsonRe.getString("description")
                            }

                        }catch (e:JSONException) {
                            Toast.makeText(this@MainActivity,"JSON exception",Toast.LENGTH_SHORT).show()
                        }

                    },

                    Response.ErrorListener {
                        println("Error on $it")

                    })
                {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String,String>()
                        headers["User-Agent"] = "Mozilla/5.0"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)

            }
            else {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Error")
                dialog.setMessage("Internet not Found")
                dialog.setPositiveButton("Open Settings") {text,listerner ->

                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    this?.finish()
                }
                dialog.setNegativeButton("Exit") {text,listerner ->

                    ActivityCompat.finishAffinity(this as Activity)
                }
                dialog.create()
                dialog.show()
            }
        }
        else {
            if(ConnectionManager().checkConnectivity(this@MainActivity)) {

                val queue = Volley.newRequestQueue(this@MainActivity)
                val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,link,null,
                    Response.Listener {response->

                        try {

                            val jsonObject = response.getJSONObject("main")

                            temp.text = jsonObject.getString("temp")
                            humidity.text = jsonObject.getString("humidity")
                            names.text = response.getString("name")

                            val json = response.getJSONObject("wind")
                            speed.text = json.getString("speed")
                            degree.text = json.getString("deg")
                            val data = response.getJSONArray("weather")
                            for(i in 0 until data.length()) {
                                val jsonRe = data.getJSONObject(i)
                                name.text = jsonRe.getString("description")
                            }

                        }catch (e:JSONException) {
                            Toast.makeText(this@MainActivity,"JSON exception $response",Toast.LENGTH_SHORT).show()
                        }

                    },

                    Response.ErrorListener {
                        println("Error on $it")

                    })
                {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String,String>()
                        headers["User-Agent"] = "Mozilla/5.0"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)

            }
            else {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Error")
                dialog.setMessage("Internet not Found")
                dialog.setPositiveButton("Open Settings") {text,listerner ->

                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    this?.finish()
                }
                dialog.setNegativeButton("Exit") {text,listerner ->

                    ActivityCompat.finishAffinity(this as Activity)
                }
                dialog.create()
                dialog.show()
            }
        }

    }
}


