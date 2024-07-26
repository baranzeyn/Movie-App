package com.example.movieapp.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.movieapp.Adapters.SliderAdapter
import com.example.movieapp.Domain.SliderItems
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentPage = 0

    private val requestAudioPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this, "İzin reddedildi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ses kaydı izni iste
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestAudioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        // Konuşma tanıyıcıyı başlat
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                Toast.makeText(this@MainActivity, "Hata: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches[0]
                    // Konuşulan metinle bir şeyler yap
                    binding.etSearch.setText(spokenText)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        binding.ivMic.setOnClickListener {
            startSpeechRecognition()
        }

        // Slider adaptörünü ayarla
        val sliderItems = listOf(
            SliderItems(R.drawable.wide),
            SliderItems(R.drawable.wide1),
            SliderItems(R.drawable.wide3)
        )

        sliderAdapter = SliderAdapter(sliderItems.toMutableList())
        binding.vpTop.adapter = sliderAdapter

        // Otomatik kaydırma için Handler ve Runnable ayarla
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == sliderItems.size) {
                    currentPage = 0
                }
                binding.vpTop.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // 3 saniye aralıklarla kaydır
            }
        }
        handler.post(runnable)
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Şimdi konuşun")
        }
        speechRecognizer.startListening(intent)
    }

    override fun onDestroy() {
        if (::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}
