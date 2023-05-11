package com.example.practicaguias_pedro.database

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.databinding.ElementoBinding

class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val binding = ElementoBinding.bind(view)

    fun bind(elemento: Lista, updateLista: (Lista) -> Unit, deleteLista: (Lista) -> Unit){
        binding.elementolista.text = elemento.nombre
        binding.pulsado.isChecked = elemento.activo

        binding.pulsado.setOnClickListener{updateLista(elemento)}
        itemView.setOnClickListener {deleteLista(elemento)}
    }
}