package com.hiteshsahu.stt_tts.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.hiteshsahu.stt_tts.databinding.ActivityHomeBinding
import com.hiteshsahu.stt_tts.translation_engine.ConversionCallback
import com.hiteshsahu.stt_tts.translation_engine.TranslatorFactory
import java.util.*

class HomeActivity : BasePermissionActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val viewRoot = binding.root
        setContentView(viewRoot)
    }

    @SuppressLint("SetTextI18n")
    override fun setUpView() {

        setSupportActionBar(binding.toolBar)

        binding.speechToText.setOnClickListener { view ->

            Snackbar.make(view, "Speak now, App is listening", Snackbar.LENGTH_LONG).setAction("Action", null).show()

            TranslatorFactory.instance.with(TranslatorFactory.TRANSLATORS.SPEECH_TO_TEXT, object : ConversionCallback {
                override fun onSuccess(result: String) {
                    binding.sttOutput.text = result
                }

                override fun onCompletion() {
                }

                override fun onErrorOccurred(errorMessage: String) {
                    binding.erroConsole.text = "Speech2Text Error: $errorMessage"
                }

            }).initialize("Speak Now !!", this@HomeActivity)
        }

        binding.textToSpeech.setOnClickListener { view ->

            val stringToSpeak: String = binding.ttsInput.text.toString()

            if (stringToSpeak.isNotEmpty()) {

                TranslatorFactory.instance.with(TranslatorFactory.TRANSLATORS.TEXT_TO_SPEECH, object : ConversionCallback {
                    override fun onSuccess(result: String) {
                    }

                    override fun onCompletion() {
                    }

                    override fun onErrorOccurred(errorMessage: String) {
                        binding.erroConsole.text = "Text2Speech Error: $errorMessage"
                    }

                }).initialize(stringToSpeak, this)

            } else {
                binding.ttsInput.setText("Invalid input")
                Snackbar.make(view, "Please enter some text to speak", Snackbar.LENGTH_LONG).show()
            }

        }

    }

    fun share(messageToShare: String, activity: Activity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare)
        activity.startActivity(Intent.createChooser(shareIntent, "Share using"))
    }
}
