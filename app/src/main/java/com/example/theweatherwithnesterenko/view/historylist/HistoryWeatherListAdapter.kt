package com.example.theweatherwithnesterenko.view.weatherlist //FIXME как так получилось? Я пакеты пока не менял

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.theweatherwithnesterenko.databinding.FragmentHistoryWeatherListBinding
import com.example.theweatherwithnesterenko.databinding.FragmentHistoryWeatherListRecyclerItemBinding
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListRecyclerItemBinding
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.utils.DOT_SVG
import com.example.theweatherwithnesterenko.utils.YANDEX_WEATHER_ICON_ENDPOINT
import com.example.theweatherwithnesterenko.utils.YASTATIC_DOMAIN


class HistoryWeatherListAdapter(

    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<HistoryWeatherListAdapter.CityHolder>() {

    fun setData(dataNew: List<Weather>) {
        this.data = dataNew
        notifyItemRangeChanged(0, data.size) // todo DiffUtil блин, хотя бы почитать!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentHistoryWeatherListRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount() = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            FragmentHistoryWeatherListRecyclerItemBinding.bind(itemView).apply {
                tvCityName.text = weather.city.name
                tvTemperature.text = weather.temperature.toString()
                tvFeelsLike.text = weather.feelsLike.toString()
                //todo HW вызвать отображение weather.icon
            }
        }
    }
}