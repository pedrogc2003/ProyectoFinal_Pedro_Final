package com.example.practicaguias_pedro.Sesiones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practicaguias_pedro.Activitys.MainActivity
import com.example.practicaguias_pedro.Activitys.ProviderType
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityRegistrarmeBinding
import com.google.firebase.auth.FirebaseAuth

class Registrarme : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegistrarmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()


        binding.BiniciarRegistro.setOnClickListener {
            val intent = Intent(this, Iniciar_Sesion::class.java)
            startActivity(intent)
        }

        binding.BRegistroRegistro.setOnClickListener {
            if (binding.ETCorreoRegistro.text.isNotEmpty() && binding.ETContrasenaRegistro.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.ETCorreoRegistro.text.toString(),
                        binding.ETContrasenaRegistro.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showMain(it.result?.user?.email ?: " ", ProviderType.BASIC)
                        } else {
                            alerta()
                        }
                    }

            } else {
                //Si hay algún campo vacio que lance el error para que se arregle
                Toast.makeText(this, "Algún campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun alerta(){
        Toast.makeText(this, "La contraseña o el correo son invalidos",Toast.LENGTH_SHORT).show()
    }

    private fun showMain(email: String, provider: ProviderType){
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(mainIntent)
    }

}