package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityIglesiasBinding
import com.example.practicaguias_pedro.databinding.ActivityParquesBinding
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiaAdapter
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiasR
import com.example.practicaguias_pedro.recyclerViewParques.ParquesAdapter
import com.example.practicaguias_pedro.recyclerViewParques.ParquesR
import com.google.firebase.firestore.FirebaseFirestore

class ParquesA : AppCompatActivity() {

    lateinit var binding: ActivityParquesBinding
    lateinit var ListaParques: MutableList<ParquesR>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParquesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RVParques.layoutManager = LinearLayoutManager(this)

        datos()

        binding.BInsertarParque.setOnClickListener {
            val intent = Intent(this, Insertar_Parque::class.java)
            startActivityForResult(intent, 1) // Inicia la actividad con un resultado esperado
        }
    }

    public fun datos() {
        val db = FirebaseFirestore.getInstance()
        ListaParques = ArrayList<ParquesR>()

        db.collection("parques").get().addOnSuccessListener {
            for (parques in it) {
                var Info = ParquesR(
                    nombre = parques["Nombre"].toString(),
                    direccion = parques["direccion"].toString(),
                    enlace = parques["enlace"].toString(),
                    horario = parques["horario"].toString()
                )
                ListaParques.add(Info)
            }
            binding.RVParques.adapter = ParquesAdapter(ListaParques)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.m_iglesia -> {
                val intent = Intent(this, IglesiasA::class.java)
                startActivity(intent)
            }
            R.id.m_parques -> {
                val intent = Intent(this, ParquesA::class.java)
                startActivity(intent)
            }
            R.id.m_monumentos -> {
                val intent = Intent(this, MonumentosA::class.java)
                startActivity(intent)
            }
            R.id.m_restaurantes -> {
                val intent = Intent(this, RestaurantesA::class.java)
                startActivity(intent)
            }
            R.id.m_otros -> {
                val intent = Intent(this, Otros::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Este método se ejecutará cuando se complete la actividad de inserción y se vuelva a esta actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) { // Si el código de solicitud y el resultado son correctos
            datos() // Actualiza la lista de parques
        }
    }
}
