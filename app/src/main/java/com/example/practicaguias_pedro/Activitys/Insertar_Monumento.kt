package com.example.practicaguias_pedro.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityInsertarIglesiaBinding
import com.example.practicaguias_pedro.databinding.ActivityInsertarMonumentoBinding
import com.google.firebase.firestore.FirebaseFirestore

class Insertar_Monumento : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    //Variable para poder crear el Alert Dialog
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarMonumentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)



        binding.BInsertarMonumento.setOnClickListener {
            //Primero compruebo si algún campo está vacio


            if(binding.ETNombreMonumento.text.isNotEmpty() && binding.ETDireccionMonumento.text.isNotEmpty()&&
                binding.ETGoogleMapsMonumento.text.isNotEmpty() && binding.ETHorarioMonumento.text.isNotEmpty()){
                if(binding.ETGoogleMapsMonumento.text.toString().startsWith("https://www.google.es/maps/place/") ||
                    binding.ETGoogleMapsMonumento.text.toString().startsWith("https://maps.app.goo.gl/") ){
                    builder.setTitle("GUARDAR")
                        //Mensaje
                        .setMessage("¿Estás seguro de que quieres guardar el monumento?")
                        .setCancelable(true)
                        //Accion por si el botón es positivo
                        .setPositiveButton("Si"){dialogInterface,it ->
                            db.collection("monumentos").document(binding.ETNombreMonumento.text.toString()).set(
                                hashMapOf("Nombre" to binding.ETNombreMonumento.text.toString(),
                                    "horario" to binding.ETHorarioMonumento.text.toString(),
                                    "direccion" to binding.ETDireccionMonumento.text.toString(),
                                    "enlace" to binding.ETGoogleMapsMonumento.text.toString())
                            )
                            Toast.makeText(this,"Monumento insertado con exito", Toast.LENGTH_SHORT).show()
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