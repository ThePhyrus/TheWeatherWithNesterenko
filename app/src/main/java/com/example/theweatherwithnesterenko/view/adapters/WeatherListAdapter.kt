package com.example.theweatherwithnesterenko.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherwithnesterenko.databinding.FragmentAllCitiesListRecyclerItemBinding
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.view.listeners.OnItemListClickListener

class WeatherListAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var dataForWeatherListAdapter: List<Weather> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

    fun doSetData(updatedWeatherData: List<Weather>) {
        this.dataForWeatherListAdapter = updatedWeatherData
        notifyDataSetChanged() //todo DiffUtil
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentAllCitiesListRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(dataForWeatherListAdapter.get(position))
    }

    override fun getItemCount() = dataForWeatherListAdapter.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            val binding = FragmentAllCitiesListRecyclerItemBinding.bind(itemView)
            binding.tvCityName.text = weather.city.name
            binding.root.setOnClickListener {
                onItemListClickListener.onItemClick(weather)
            }
        }
    }
}