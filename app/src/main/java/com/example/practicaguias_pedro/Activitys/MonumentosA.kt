package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityMonumentosBinding
import com.example.practicaguias_pedro.recyclerMonumentos.MonumentosAdapter
import com.example.practicaguias_pedro.recyclerMonumentos.MonumentosR
import com.google.firebase.firestore.FirebaseFirestore

class MonumentosA : AppCompatActivity() {
    private lateinit var binding: ActivityMonumentosBinding
    private lateinit var monumentosAdapter: MonumentosAdapter
    private lateinit var listaMonumentos: MutableList<MonumentosR>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonumentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaMonumentos = mutableListOf()
        monumentosAdapter = MonumentosAdapter(listaMonumentos)

        binding.RVMonumentos.layoutManager = LinearLayoutManager(this)
        binding.RVMonumentos.adapter = monumentosAdapter

        binding.BInsertarMonumento.setOnClickListener {
            val intent = Intent(this, Insertar_Monumento::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        datos()
    }

    private fun datos() {
        val db = FirebaseFirestore.getInstance()
        listaMonumentos.clear()

        db.collection("monumentos").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val nombre = document.getString("Nombre") ?: ""
                val direccion = document.getString("direccion") ?: ""
                val enlace = document.getString("enlace") ?: ""
                val horario = document.getString("horario") ?: ""

                val monumento = MonumentosR(nombre, direccion, enlace, horario)
                listaMonumentos.add(monumento)
            }

            monumentosAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
}
