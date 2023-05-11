package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityIglesiasBinding
import com.example.practicaguias_pedro.databinding.ActivityRestaurantesBinding
import com.example.practicaguias_pedro.reciclerViewRestaurantes.RestaurantesAdapter
import com.example.practicaguias_pedro.reciclerViewRestaurantes.RestaurantesR
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiaAdapter
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiasR
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesA : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantesBinding
    lateinit var ListaRestaurantes: MutableList<RestaurantesR>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RVRestaurantes.layoutManager = LinearLayoutManager(this)

        datos()

        binding.BAnaditrRestaurante.setOnClickListener {
            val intent = Intent(this,Insertar_Restaurante::class.java)
            startActivity(intent)
        }
    }

    public fun datos(){
        //Llamada al método de FireStore para obtener todos los datos
        val db = FirebaseFirestore.getInstance()
        ListaRestaurantes = ArrayList<RestaurantesR>()

        //Llamas a la colección para mostrar
        db.collection("restaurantes").get().addOnSuccessListener {
            //Lo recorres mientras siga habiendo iglesias
            for(restaurantes in it){
                var Info = RestaurantesR(
                    nombre = restaurantes["Nombre"].toString(),
                    direccion = restaurantes["direccion"].toString(),
                    enlace = restaurantes["enlace"].toString(),
                    horario = restaurantes["horario"].toString()
                )
                ListaRestaurantes.add(Info)
            }
            //Llamas al adapter
            binding.RVRestaurantes.adapter = RestaurantesAdapter(ListaRestaurantes)
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