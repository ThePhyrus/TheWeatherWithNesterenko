package com.example.theweatherwithnesterenko.viewmodel

import com.example.theweatherwithnesterenko.repository.Weather

sealed class DetailsState{
  object Loading:DetailsState()
  data class Success(val weather:Weather):DetailsState()
  data class Error(val error:Throwable):DetailsState()
}
