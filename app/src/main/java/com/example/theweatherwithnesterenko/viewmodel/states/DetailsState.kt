package com.example.theweatherwithnesterenko.viewmodel.states

import com.example.theweatherwithnesterenko.repository.weather.Weather

sealed class DetailsState{
  object Loading: DetailsState()
  data class Success(val weather: Weather): DetailsState()
  data class Error(val error:Throwable): DetailsState()
}
