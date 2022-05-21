package com.example.theweatherwithnesterenko.utils

//Lesson 8

/*
Урок 8. Хранение данных в приложении (timeCode).

0:00 - начало видео

0:11 - начало урока освежили знания о Shared Preferences (хранение настроек).
0:14 - дз-8 сохранить состояние приложения (boolean внутри shared preference)

0:24 - коммит

0:32 - про SQLite

0: - Дальше про аномалии... страшно смотреть

0:

0:

0:

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

3:27 - живой пример плюса от правильной архитектуры

3:32 - смотрим хистори адаптер (какая погода приходит)

3:35 - Дз-8 нарисовать иконку в истории!)

3:37 - меняем DetailsViewModel

3:43 - про дз-8

3:51 - подитог работы с базой данных этого приложения

4:01 - миграции баз данных.

4:21 Урок окончен.
*/

/*
0:10 - Build variants (Build types + Product Flavors)

0:

0:

0:

0:42 - создаём фаил ключей

1:00 - коротко про настройки прогвард

1:05 - multiDexEnabled

1:07 - подитог  Build variants (Build types + Product Flavors)

1:10 - переменка

1:20 - коммит. Познакомились с Build variants (Build types + Product Flavors)

1:32 - кому интересно заморочится с несколькими версиями сборки приложения?

1:41 - Фаил ключей
Как проверить релизную версию на эмуляторе.

1:44 - Прописываем signing config

1:45 - создаём фаил password.properties  !!

1:48 - > Keystore file 'D:\Kotlin\TheWeatherWithNesterenko\app\key_app.jks' not found for signing
config 'freeAdultRelease'.

1:55 - Build variants (Build types + Product Flavors) пройдены.

2:00 - Принципы здорового разработчика:

2:14 - Начинаем редактировать код

2:

2:

2:

2:

2:
*/