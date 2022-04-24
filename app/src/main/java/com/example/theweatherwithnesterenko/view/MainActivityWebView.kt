package com.example.theweatherwithnesterenko.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.databinding.ActivityMainWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class MainActivityWebView : AppCompatActivity() {
    private lateinit var binding: ActivityMainWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener {
            val urlText = binding.edTextUrl.text.toString()
            createAndOpenUrlConnection(urlText)
        }
    }

    private fun createAndOpenUrlConnection(urlText: String) { // подробная роспись
        val uri = URL(urlText)
        val urlConnection: HttpsURLConnection =
            (uri.openConnection() as HttpsURLConnection).apply {
                connectTimeout = 1000
                readTimeout = 1000
            }
        Thread {
            val headers = urlConnection.headerFields
            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = getLinesAsOneBigString(buffer)
            Handler(Looper.getMainLooper()).post {
                binding.webView.loadDataWithBaseURL(
                    null,
                    result,
                    "text/html; utf-8",
                    "utf-8",
                    null
                )
            }
        }.start()
    }

    private fun getLinesAsOneBigString(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}
