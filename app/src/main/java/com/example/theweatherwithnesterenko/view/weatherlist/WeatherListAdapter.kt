package com.example.theweatherwithnesterenko.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListRecyclerItemBinding
import com.example.theweatherwithnesterenko.repository.weather.Weather


class WeatherListAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

    override fun getItemCount() = data.size

    fun setData(dataNew: List<Weather>) {
        this.data = dataNew
        notifyItemRangeChanged(0, data.size) // todo DiffUtil изучить!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentWeatherListRecyclerItemBinding.inflate(
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
            FragmentWeatherListRecyclerItemBinding.bind(itemView).apply {
                tvCityName.text = weather.city.name
                root.setOnClickListener {
                    onItemListClickListener.onItemClick(weather)
                }
            }
        }
    }
}