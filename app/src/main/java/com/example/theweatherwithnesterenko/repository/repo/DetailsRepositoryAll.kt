package com.example.theweatherwithnesterenko.repository.repo


import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel


interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll)
}