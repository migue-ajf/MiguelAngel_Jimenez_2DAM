package com.example.miguelangel_jimenez_dam;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.miguelangel_jimenez_dam.databinding.ActivitySplashBinding
 class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,LogInActivity::class.java))
        finish()
        },3000)

    }
}