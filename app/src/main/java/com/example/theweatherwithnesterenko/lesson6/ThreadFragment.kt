package com.example.theweatherwithnesterenko.lesson6


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.databinding.FragmentThreadsBinding
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
        theThread.start() // если не стартовать поток, то будет
        // UninitializedPropertyAccessException: lateinit property theHandler has not been initialized


        with(binding) {
            val time = editText1.text.toString().toLong()
            var counter = 0
            button1.setOnClickListener {
                Thread { // задачи в этом потоке буполняются параллельно (сразу все)
                    sleep(time * 1000L)
                    /*requireActivity().runOnUiThread { // современный способ
                         // textView1.text = "${R.string.it_was_working_for} $time ${R.string.sec}"
                         //FIXME что-то не так со строчкой выше (отображает цифры вместо текста)
                         textView1.text = "It was working for $time sec."
                    }*/
                    Handler(Looper.getMainLooper()).post { // для понимания процессов
                        textView1.text = "It was working for $time sec."
                        createTextView("${Thread.currentThread().name} ${++counter}")
                    }
                }.start()
            }
            //"ВЕЧНЫЙ ПОТОК"
            button2.setOnClickListener {
                theThread.theHandler.post { // в этом потоке задачи выполняться будут по очереди
                    sleep(time * 1000L)
                    Handler(Looper.getMainLooper()).post {
                        // textView2.text = "${R.string.it_was_working_for} $time ${R.string.sec}"
                        //FIXME что-то не так со строчкой выше (отображает цифры вместо текста)
                        textView2.text = "It was working for $time sec."
                        createTextView("${Thread.currentThread().name} ${++counter}")
                    }

                }
            }
        }
    }

    private fun createTextView(name:String) {
        binding.mainContainer.addView(TextView(requireContext()).apply {
            text = name
            textSize = 18f
        })
    }

    class TheThread : Thread() { // "вечный поток"
        lateinit var theHandler: Handler //FIXME оставить lateinit или theThread.theHandler?????.post (как на уроке было). Как лучше?
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




