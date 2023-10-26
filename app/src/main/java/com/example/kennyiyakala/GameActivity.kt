package com.example.kennyiyakala

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kennyiyakala.databinding.ActivityGameBinding
import com.example.kennyiyakala.databinding.ActivityMainBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var skor = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView10)
        imageArray.add(binding.imageView11)
        imageArray.add(binding.imageView12)
        resmiGizle()

        object : CountDownTimer(5500,1000){
            override fun onTick(p0: Long) {
                binding.sureText.text = "Kalan Süre: ${p0/1000}"
            }
            override fun onFinish() {
                binding.sureText.text = "Kalan Süre: 0"
                handler.removeCallbacks(runnable)
                for(resim in imageArray){
                    resim.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@GameActivity)
                alert.setTitle("Skorunuz: ${skor}")
                alert.setMessage("Tekrar oynamak ister misiniz?")
                alert.setPositiveButton("Evet") {dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }

                alert.setNegativeButton("Hayır") {dialog, which ->
                    val intent = Intent(this@GameActivity,MainActivity::class.java)
                    startActivity(intent)
                }
                alert.show()
            }
        }.start()
    }

    fun resmiGizle(){

        val intent = intent
        var zorlukSeviyesi = intent.getIntExtra("zorluk",0)

        if (zorlukSeviyesi == 0){
            zorlukSeviyesi = 500

        }else if (zorlukSeviyesi == 1){
            zorlukSeviyesi = 300
        }else{
            zorlukSeviyesi = 150
        }

        runnable = object  : Runnable {
            override fun run() {
                for(resim in imageArray){
                    resim.visibility = View.INVISIBLE
                }
                val random = Random.nextInt(9)
                imageArray[random].visibility = View.VISIBLE

                handler.postDelayed(runnable, zorlukSeviyesi.toLong())
            }
        }
        handler.post(runnable)
    }

    fun skoruArttir(view: View){
        skor = skor + 1
        binding.skorText.text = "Skor: ${skor}"
    }
}