package com.example.practicaguias_pedro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicaguias_pedro.Sesiones.Iniciar_Sesion
import com.example.practicaguias_pedro.Sesiones.Registrarme
import com.example.practicaguias_pedro.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //El binding, dependiendo de la seleccion te llevará a alguna activity u otra
        binding.BIniciarIntro.setOnClickListener {
            val intent = Intent(this,Iniciar_Sesion::class.java)
            startActivity(intent)
            finish()
        }

        binding.BRegistroIntro.setOnClickListener {
            val intent = Intent(this,Registrarme::class.java)
            startActivity(intent)
            finish()
        }
    }
}