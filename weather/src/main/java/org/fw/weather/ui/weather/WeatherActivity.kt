package org.fw.weather.ui.weather

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import org.fw.weather.R
import org.fw.weather.logic.model.Weather
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //与状态栏融合
        val decorView = window.decorView
        /**
         * 1 activity布局会显示在状态栏上面
         * 2 android:fitsSystemWindows="true" 为系统状态栏留出空间
         */
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_weather)

        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng = intent.getStringExtra("location_lng") ?:""
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat = intent.getStringExtra("location_lat") ?:""
        }
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName = intent.getStringExtra("place_name") ?:""
        }
        //数据变化 回调监听
        viewModel.weatherLiveData.observe(this, Observer {
            swipeRefresh.isRefreshing = false
            val weather = it.getOrNull()
            if(weather != null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLat, viewModel.locationLng)

        //手动刷新
        swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        //========侧滑菜单==========布局的第二个控件是滑动菜单
        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) { }
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) { }
            override fun onDrawerClosed(drawerView: View) {
                //隐藏输入框
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            override fun onDrawerOpened(drawerView: View) {    }
        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE

    }
}