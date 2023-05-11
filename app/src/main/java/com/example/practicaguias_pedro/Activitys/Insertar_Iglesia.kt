package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityInsertarIglesiaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Insertar_Iglesia : AppCompatActivity() {

    //variebles
    private val db = FirebaseFirestore.getInstance()
    //Variable para poder crear el Alert Dialog
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarIglesiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Aqui se le da el valor para el Alert Dialog
        builder = AlertDialog.Builder(this)



        binding.BInsertarIglesias.setOnClickListener {
            //Primero compruebo si algún campo está vacio


            if(binding.ETNombreIglesia.text.isNotEmpty() && binding.ETDireccion.text.isNotEmpty()&&
                    binding.ETGoogleMaps.text.isNotEmpty() && binding.ETHorario.text.isNotEmpty()){
                if(binding.ETGoogleMaps.text.toString().startsWith("https://www.google.es/maps/place/") ||
                    binding.ETGoogleMaps.text.toString().startsWith("https://maps.app.goo.gl/") ){
                    builder.setTitle("GUARDAR")
                        //Mensaje
                        .setMessage("¿Estás seguro de que quieres guardar la Iglesia?")
                        .setCancelable(true)
                        //Accion por si el botón es positivo
                        .setPositiveButton("Si"){dialogInterface,it ->
                            db.collection("iglesias").document(binding.ETNombreIglesia.text.toString()).set(
                                hashMapOf("Nombre" to binding.ETNombreIglesia.text.toString(),
                                    "horario" to binding.ETHorario.text.toString(),
                                    "direccion" to binding.ETDireccion.text.toString(),
                                    "enlace" to binding.ETGoogleMaps.text.toString())
                            )
                            Toast.makeText(this,"Iglesia insertada con exito",Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this,"Algún campo esta vacio",Toast.LENGTH_SHORT).show()
            }
        }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m_iglesia->{
                val intent = Intent(this, IglesiasA::class.java)
                startActivity(intent)
            }
            R.id.m_parques->{
                val intent = Intent(this,Parques::class.java)
                startActivity(intent)
            }
            R.id.m_monumentos->{
                val intent = Intent(this,Monumentos::class.java)
                startActivity(intent)
            }
            R.id.m_restaurantes->{
                val intent = Intent(this,RestaurantesA::class.java)
                startActivity(intent)
            }


        }
        return super.onOptionsItemSelected(item)
    }
}