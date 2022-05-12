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
import com.example.theweatherwithnesterenko.R
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
        theThread.start() // если не стартовать поток, то будет UninitializedPropertyAccessException: lateinit property theHandler has not been initialized
        with(binding) {
            val time = editText1.text.toString().toLong() //todo fix it 1
            var counter = 0
            button1.setOnClickListener {
                Thread { //todo задачи в этом потоке буполняются параллельно (сразу все) или создасться несколько потоков?
                    sleep(time * 1000L) //todo fix it 1
                    requireActivity().runOnUiThread { // современный способ без лишних вопросов
                          textView1.text =
                            resources.getString(R.string.it_was_working_for) +
                                    " $time " +
                                    resources.getString(R.string.sec)
                        createTextView("${Thread.currentThread().name} ${++counter}")
                    }
                        //более подробная запись
                    /*Handler(Looper.getMainLooper()).post { // для понимания процессов
                        textView1.text =
                            resources.getString(R.string.it_was_working_for) +
                                    "$time " +
                                    resources.getString(R.string.sec)
                        createTextView("${Thread.currentThread().name} ${++counter}")
                    }*/
                }.start()
            }
            //"ВЕЧНЫЙ ПОТОК"
            button2.setOnClickListener {
                theThread.theHandler.post { //todo закрыть поток?
                    sleep(time * 1000L) //todo fix it 1
                    requireActivity().runOnUiThread { // современный способ без лишних вопросов
                        textView1.text =
                            resources.getString(R.string.it_was_working_for) +
                                    "$time " +
                                    resources.getString(R.string.sec)
                        createTextView("${Thread.currentThread().name} ${++counter}")
                    }
                }
            }
        }
    }

    private fun createTextView(name: String) {
        binding.mainContainer.addView(TextView(requireContext()).apply {
            text = name
            textSize = 18f
        })
    }

    class TheThread : Thread() { // "вечный поток"
        lateinit var theHandler: Handler
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





