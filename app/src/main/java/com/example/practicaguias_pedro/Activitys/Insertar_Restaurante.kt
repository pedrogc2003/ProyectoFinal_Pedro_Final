package com.example.practicaguias_pedro.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityInsertarRestauranteBinding
import com.google.firebase.firestore.FirebaseFirestore

class Insertar_Restaurante : AppCompatActivity() {
    // Variables
    private val db = FirebaseFirestore.getInstance()
    // Variable para poder crear el AlertDialog
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarRestauranteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar el AlertDialog.Builder
        builder = AlertDialog.Builder(this)

        binding.BInsertarRestaurante.setOnClickListener {
            // Comprobar si algún campo está vacío
            if (binding.ETNombreRestaurante.text.isNotEmpty() && binding.ETDireccionRestaurante.text.isNotEmpty() &&
                binding.ETGoogleMapsRestaurante.text.isNotEmpty() && binding.ETHorarioRestaurante.text.isNotEmpty()) {

                if (binding.ETGoogleMapsRestaurante.text.toString().startsWith("https://www.google.es/maps/place/") ||
                    binding.ETGoogleMapsRestaurante.text.toString().startsWith("https://maps.app.goo.gl/")) {

                    builder.setTitle("GUARDAR")
                        .setMessage("¿Estás seguro de que quieres guardar el restaurante?")
                        .setCancelable(true)
                        .setPositiveButton("Si") { dialogInterface, it ->
                                db.collection("restaurantes").document(binding.ETNombreRestaurante.text.toString()).set(
                                hashMapOf(
                                    "Nombre" to binding.ETNombreRestaurante.text.toString(),
                                    "horario" to binding.ETHorarioRestaurante.text.toString(),
                                    "direccion" to binding.ETDireccionRestaurante.text.toString(),
                                    "enlace" to binding.ETGoogleMapsRestaurante.text.toString()
                                )
                            )
                            Toast.makeText(this, "Restaurante insertado con éxito", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK) // Envía el resultado a la actividad anterior
                            finish() // Finaliza la actividad actual
                        }
                        .setNegativeButton("No") { dialogInterface, it ->
                            dialogInterface.cancel()
                            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                } else {
                    Toast.makeText(this, "El enlace no es correcto", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Si hay algún campo vacío, mostrar mensaje de error
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
