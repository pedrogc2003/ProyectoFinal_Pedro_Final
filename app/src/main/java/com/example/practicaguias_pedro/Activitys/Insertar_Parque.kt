package com.example.practicaguias_pedro.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityInsertarMonumentoBinding
import com.example.practicaguias_pedro.databinding.ActivityInsertarParqueBinding
import com.google.firebase.firestore.FirebaseFirestore

class Insertar_Parque : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    //Variable para poder crear el Alert Dialog
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarParqueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        binding.BInsertarParque.setOnClickListener {
            //Primero compruebo si algún campo está vacio


            if(binding.ETNombreParque.text.isNotEmpty() && binding.ETDireccionParque.text.isNotEmpty()&&
                binding.ETGoogleMapsParque.text.isNotEmpty() && binding.ETHorarioParque.text.isNotEmpty()){
                if(binding.ETGoogleMapsParque.text.toString().startsWith("https://www.google.es/maps/place/") ||
                        binding.ETGoogleMapsParque.text.toString().startsWith("https://maps.app.goo.gl/") ){
                    builder.setTitle("GUARDAR")
                        //Mensaje
                        .setMessage("¿Estás seguro de que quieres guardar el monumento?")
                        .setCancelable(true)
                        //Accion por si el botón es positivo
                        .setPositiveButton("Si"){dialogInterface,it ->
                            db.collection("parques").document(binding.ETNombreParque.text.toString()).set(
                                hashMapOf("Nombre" to binding.ETNombreParque.text.toString(),
                                    "horario" to binding.ETHorarioParque.text.toString(),
                                    "direccion" to binding.ETDireccionParque.text.toString(),
                                    "enlace" to binding.ETGoogleMapsParque.text.toString())
                            )
                            Toast.makeText(this,"Parque insertado con exito", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK) // Envía el resultado a la actividad anterior
                            finish() // Finaliza la actividad actual
                        }
                        //Si es negavitivo que no se inserte nada
                        .setNegativeButton("No"){dialogInterface,it ->
                            dialogInterface.cancel()
                            Toast.makeText(this,"Acción cancelada", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                }
                else{
                    Toast.makeText(this, "El enlace no es correcto", Toast.LENGTH_SHORT).show()
                }

            }else{
                //Si hay algún campo vacio que salga la excepción
                Toast.makeText(this,"Algún campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}