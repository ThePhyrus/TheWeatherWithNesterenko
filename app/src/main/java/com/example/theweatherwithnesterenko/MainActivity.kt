package com.example.theweatherwithnesterenko

import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.view.MapsFragment
import com.example.theweatherwithnesterenko.view.WorkWithContentProviderFragment
import com.example.theweatherwithnesterenko.view.historylist.HistoryWeatherListFragment
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment


//FIXME: это нужно как-то починить
// - подправить ui и выводимую информацию,
// - сделать дополнительные версии,
// - ресурсы (как там порядок навести),
// - обработка ошибок, обработка ответов сервера,
// - порядок в snackbar почти наведён (надо всё перепроверить),
// - выводить иконку в истории запросов!!!???,
// - научиться сохранять настройки приложения,
// - кнопки меню не переключают некорректно, не правильно переключаются между собой, что-то с добавлением в бекстек,
// - каждый DetailsFragment добавляется в backstack
// - научиться хранить в базе данных lat, lon


/**
 * Андрей Нестеренко・Преподаватель
Здравствуйте, Роман!
• У вас сейчас апри каждом вызове
getWeatherDetails
пересоздается экземпляр ретрофит клиента, хотелось бы этого избежать, например, путем
выноса его за пределы getWeatherDetails в какую-то внутреннюю переменную репозитория.
Или вообще на уровень приложения MyApp или как там у нас было на
уроке (класс наследующий Application)

• Для функции load (речь про coil) мы можем указывать, что делать в случае ошибки
загрузки error, и чем скрасить ожидание placeholder
binding.ivHeader.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png"){
placeholder(R.drawable.ic_russia)
error(R.drawable.ic_earth)
}
это позволяет вашему приложению выглядеть более живым для пользователя, что ли
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
        createReceiver()
//        setupSP()

        Thread {
            MainApp.getHistoryDao().getAll()
        }.start()
    }


    private fun setupSP() {//FIXME

        /*isRussian = requireActivity().getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
.getBoolean(Settings.SETTING_RUS, Settings.settingRus)

и запись (тут пару рутинных строк кода)
на каждый клик по FAB кнопке открываем эдитор
val sharedPreferences =
requireActivity().getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
val editor = sharedPreferences.edit()
записываем текущее значение
editor.putBoolean(Settings.SETTING_RUS, <true/false – зависит от текущего состояния>)
и сохраняем изменение
editor.apply()*/

      val sp = getSharedPreferences(KEY_SP_SETTINGS, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(KEY_SP_SETTINGS_KEY_RUSSIAN, true)
        editor.apply()
        val defaultValueIsRussian = true
        sp.getBoolean(KEY_SP_SETTINGS_KEY_RUSSIAN, defaultValueIsRussian)
    }

    private fun createReceiver() {//FIXME сколько угодно можно регистрировать ресиверов одной этой функцией?
        val receiver = BroadcastReceiver() // создаётся ресивер (приёмник)
        registerReceiver(
            receiver,
            IntentFilter(KEY_WAVE_THE_ACTION)
        )
        registerReceiver(
            receiver,
            IntentFilter(ACTION_AIRPLANE_MODE)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//FIXME не правильно переключаются между собой, что-то с добавлением в бекстек
        when (item.itemId) {
            R.id.action_history -> {
                val fragmentA = supportFragmentManager.findFragmentByTag("tagA")
                if (fragmentA == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                HistoryWeatherListFragment.newInstance(),
                                "tagA"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_work_with_content_provider -> {
                val fragmentB = supportFragmentManager.findFragmentByTag("tagB")
                if (fragmentB == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                WorkWithContentProviderFragment.newInstance(),
                                "tagB"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_menu_google_maps -> {
                val fragmentC = supportFragmentManager.findFragmentByTag("tagC")
                if (fragmentC == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                MapsFragment(),
                                "tagC"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}