package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.database.Lista
import com.example.practicaguias_pedro.database.ListaAdapter
import com.example.practicaguias_pedro.database.MiLista
import com.example.practicaguias_pedro.databinding.ActivityOtrosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Otros : AppCompatActivity() {
    lateinit var binding: ActivityOtrosBinding
    lateinit var elementos: MutableList<Lista>
    lateinit var adapter: ListaAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOtrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        elementos = ArrayList()

        getElementos()

        binding.BInsertar.setOnClickListener {
            addElemento(Lista(nombre = binding.TVOtro.text.toString()))
        }


    }

    fun getElementos(){
        CoroutineScope(Dispatchers.IO).launch {
                elementos = MiLista.database.listaDao().getAllElements()
            runOnUiThread{
                adapter = ListaAdapter(elementos,{updateLista(it)},{deleteLista(it)})
                recyclerView = binding.recycler
                recyclerView.layoutManager = LinearLayoutManager(this@Otros)
                recyclerView.adapter = adapter
            }
        }
    }

    fun addElemento(elemento:Lista){
        CoroutineScope(Dispatchers.IO).launch {
            val id = MiLista.database.listaDao().addElemento(elemento)
            val recoveryElemento = MiLista.database.listaDao().getElementoId(id)
            runOnUiThread{
                elementos.add(recoveryElemento)
                adapter.notifyItemInserted(elementos.size)

            }
        }
    }

    fun updateLista(elemento: Lista){
        CoroutineScope(Dispatchers.IO).launch {
            elemento.activo = !elemento.activo
            MiLista.database.listaDao().updateLista(elemento)
        }
    }

    fun deleteLista(elemento: Lista){
        CoroutineScope(Dispatchers.IO).launch{
            val position = elementos.indexOf(elemento)
            MiLista.database.listaDao().deleteLista(elemento)
            elementos.remove(elemento)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
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