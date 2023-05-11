package com.example.practicaguias_pedro.Sesiones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practicaguias_pedro.Activitys.MainActivity
import com.example.practicaguias_pedro.Activitys.ProviderType
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityIniciarSesionBinding
import com.google.firebase.auth.FirebaseAuth

class Iniciar_Sesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Llamada al método para poder almacenar datos de inicio de sesión y obtenerlos
        val auth = FirebaseAuth.getInstance()


        //Este binding te lleva a Registro, lo he puesto por si el usuario quiere registrarse y le da al iniciar sesión sin quererlo
        binding.BRegistrarseIniciar.setOnClickListener {
            val intent = Intent(this, Registrarme::class.java)
            startActivity(intent)
        }

        binding.BIniciarIniciar.setOnClickListener {
            //Comprobamos si algún campo está vacio
            if (binding.ETCorreoIniciar.text.isNotEmpty() && binding.ETContrasenaIniciar.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                        //Si todo está correcto, que se busque el usuario asignado mediante correo y contraseña
                    .signInWithEmailAndPassword(
                        binding.ETCorreoIniciar.text.toString(),
                        binding.ETContrasenaIniciar.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            //Si esta bien, que inicie sesión
                            showMain(it.result?.user?.email ?: " ", ProviderType.BASIC)
                        } else {
                            //Si hay algo mal que salga el error
                            alerta()
                        }
                    }

            } else {
                //Si hay algún campo vacio que lance el error para que se arregle
                Toast.makeText(this, "Algún campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Métodos
    private fun alerta(){
        //Si la contraseña o el correo esta mal, que lance la excepción
        Toast.makeText(this, "La contraseña o el correo son invalidos",Toast.LENGTH_SHORT).show()
    }

    private fun showMain(email: String, provider: ProviderType){
        //Si está todo correcto, que lleve al activitu main
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(mainIntent)
    }

}