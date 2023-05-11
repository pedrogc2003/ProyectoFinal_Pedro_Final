package com.example.practicaguias_pedro.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R

class ListaAdapter(val elementos: List<Lista>,
                   val updateLista:(Lista) -> Unit,
                   val deleteLista: (Lista) -> Unit) : RecyclerView.Adapter<ListaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListaViewHolder(layoutInflater.inflate(R.layout.elemento, parent, false))
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val item = elementos[position]

        holder.bind(item, updateLista, deleteLista)
    }

    override fun getItemCount(): Int {
        return elementos.size
    }
}