package to.boosty.cmit.weatherapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import to.boosty.cmit.weatherapp.databinding.ActivityMainBinding
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var et_city: EditText
    private lateinit var tv_city: TextView
    private lateinit var tv_temp: TextView
    private lateinit var tv_pressure: TextView
    private lateinit var tv_sunrise: TextView
    private lateinit var tv_sunset: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        button = binding.btSearch
        et_city = binding.etCity
        tv_city = binding.tvCity
        tv_temp = binding.tvTemp
        tv_pressure = binding.tvPressure
        tv_sunrise = binding.tvSurise
        tv_sunset = binding.tvSunset

        button?.setOnClickListener {
            Log.d("TAG", "1")
            if (et_city?.text?.toString()?.trim()?.equals("")!!) {
                Log.d("TAG", "2")
                Toast.makeText(this, "Введите город", Toast.LENGTH_SHORT).show()
            } else {
                val city: String = et_city!!.text.toString()
                val api = "11d717397570d1d0b40264031a316a90"
                Log.d("TAG", "3")
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$api"

                CoroutineScope(Dispatchers.IO).async { processFinish(url) }
            }

        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            val intent = Intent (this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
            //NavUtils.navigateUpFromSameTask(this)
        return super.onOptionsItemSelected(item)
    }

    suspend fun processFinish(output: String) {
        try {
            Log.d("TAG", "$output")
            val apiResponse = URL(output).readText()
            val resJSON = JSONObject(apiResponse)
            Log.d("TAG", "3 + $resJSON")
            val weather = resJSON.getJSONObject("main")
            val sys = resJSON.getJSONObject("sys")
            val temp = binding.tvTemp
            val pressure = binding.tvPressure
            val sunset = binding.tvSunset
            val sunrise = binding.tvSurise
            temp.text = weather.getString("temp")
            pressure.text = weather.getString("pressure")
            val myLocale = Locale("ru", "RU")
            val formatter = SimpleDateFormat("HH:mm:ss", myLocale)
            fun Date_SR(s: String) = Date(s.toLong() * 1000 + (60 * 60 * 1000) * 3)
            val time_Sunrise = formatter.format(Date_SR(sys.getString("sunrise")))
            val time_Sunset = formatter.format(Date_SR(sys.getString("sunset")))
            sunrise.text = time_Sunrise
            sunset.text = time_Sunset
        } catch (ex: JSONException) {
            Log.d("TAG", "5")
        }




    }
}



