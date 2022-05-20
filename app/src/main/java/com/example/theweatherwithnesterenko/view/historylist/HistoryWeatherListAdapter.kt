package com.example.theweatherwithnesterenko.view.historylist //FIXME как так получилось? Я пакеты пока не менял

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherwithnesterenko.databinding.FragmentHistoryWeatherListRecyclerItemBinding
import com.example.theweatherwithnesterenko.repository.weather.Weather


class HistoryWeatherListAdapter(

    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<HistoryWeatherListAdapter.CityHolder>() {

    override fun getItemCount() = data.size

    fun setData(dataNew: List<Weather>) {
        this.data = dataNew
        notifyItemRangeChanged(0, data.size) //DiffUtil бы почитать!
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



    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            FragmentHistoryWeatherListRecyclerItemBinding.bind(itemView).apply {
                tvCityName.text = weather.city.name
                tvTemperature.text = weather.temperature.toString()
                tvFeelsLike.text = weather.feelsLike.toString()
                ivIcon
                //todo HW вызвать отображение weather.icon
            }
        }
    }
}