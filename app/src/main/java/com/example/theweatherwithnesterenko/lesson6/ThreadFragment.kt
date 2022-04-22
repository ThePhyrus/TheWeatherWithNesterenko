package com.example.theweatherwithnesterenko.lesson6


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentThreadsBinding
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListBinding
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.example.theweatherwithnesterenko.view.details.DetailsFragment
import com.example.theweatherwithnesterenko.viewmodel.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Thread.sleep

class ThreadFragment : Fragment() {

    private var _binding: FragmentThreadsBinding? = null
    private val binding: FragmentThreadsBinding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theThread = TheThread() // здесть создаётся "вечный поток"
        with(binding) {
            button.setOnClickListener {
                Thread {
                    val time = editText.text.toString().toLong()
                    sleep(time * 1000L)
                    requireActivity().runOnUiThread {
                        // textView.text = "${R.string.it_was_working_for} $time ${R.string.sec}"
                        //FIXME что-то не так со строчкой выше (отображает цифры вместо текста)
                        textView.text = "It was working for $time sec."
                    }

                }.start()
            }
        }
    }

    class TheThread:Thread(){ // "вечный поток"
        lateinit var theHandler:Handler
        override fun run() {
            Looper.prepare()
            theHandler = Handler(Looper.myLooper()!!)
            Looper.loop()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThreadFragment()
    }


}
