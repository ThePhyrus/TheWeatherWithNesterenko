package com.example.theweatherwithnesterenko.utils

//Lesson 8

/*
1:45 - Начинаем работу доменной частью приложения (room database)
Создаём HistoryEntity. Каждый наш запрос будет обновлять базу данных.

1:51 - часть дз к 8-му уроку (timestamp + city)

1:53 - Создаём интерфейс HistoryDao по работе с базой данных. Поже на ретрофит.

2:08 - Осталось приготовить Room. ОДИН РАЗ СОЗДАЁМ Room на уровне приложения(контекст будет доступен везде?).
Создаём MyApp.

2:27 - Проверяем и коммит (добавили room в проект)

2:40 - подитог Room

2:43 - Куда эту базу данных добавить?

2:47 - Применяем принцип SOLID и разделяем интерфейсы
Создаём
DetailsRepositoryOne
DetailsRepositoryAll
DetailsRepositoryAdd
class DetailsRepositoryRoomImpl

2:54 - Первый маппер!

2:57 - ДЗ для энтузиастов // Научиться хранить в базе данных lat, lon

3:03 -  Создаём DetailsRepositoryAdd

3:04 - подитог разделения интерфейса

3:05 - Какие изменения произойдут на уровне viewmodel? Придётся создавать новую.
Создаём...
HistoryWeatherListAdapter
HistoryWeatherListFragment
fragment_history_weather_list
fragment_history_weather_list_recycler_item
HistoryViewModel

3:27 - живой пример плюса от правильной архитектуры //todo спать




*/
