package to.boosty.cmit.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.json.JSONException
import org.json.JSONObject
import to.boosty.cmit.weatherapp.databinding.ActivityMainBinding
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var et_city: EditText? = null
    private var tv_city: TextView? = null
    private var tv_temp: TextView? = null
    private var tv_pressure: TextView? = null
    private var tv_sunrise: TextView? = null
    private var tv_sunset: TextView? = null
    private var button: Button? = null

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
        //val city = "Санкт-Петербург"


        button?.setOnClickListener {
            if (et_city?.text?.toString()?.trim()?.equals("")!!) {
                Toast.makeText(this, "Введите город", Toast.LENGTH_SHORT).show()
            } else {
                val city: String = et_city!!.text.toString()
                val api = "11d717397570d1d0b40264031a316a90"
                Log.d("TAG", "${tv_sunset?.text}")
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$api"

                doAsync {
                    val apiResponse = URL(url).readText()
                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temper = main.getString("temp")
                    val press = main.getString("pressure")
                    val sys = JSONObject(apiResponse).getJSONObject("sys")
                    val myLocale = Locale("ru", "RU")
                    val formatter = SimpleDateFormat("HH:mm:ss", myLocale)
                    fun Date_SR(s: String) = Date(s.toLong() * 1000 + (60 * 60 * 1000) * 3)
                    val time_Sunrise = formatter.format(Date_SR(sys.getString("sunrise")))
                    val time_Sunset = formatter.format(Date_SR(sys.getString("sunset")))

                    tv_city?.text = et_city?.text?.toString()
                    tv_temp?.text = temper
                    tv_pressure?.text = press
                    tv_sunset?.text = time_Sunset
                    tv_sunrise?.text = time_Sunrise
                    Log.d("TAG", "${tv_sunset?.text}")
                }
            }

        }

        /*fun processFinish(output: String) {
            try {
                val resJSON = JSONObject(output)
                Log.d("TAG", resJSON.toString())
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

            }
        }*/

    }
}


