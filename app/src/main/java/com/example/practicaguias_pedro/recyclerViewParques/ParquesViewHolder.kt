package com.example.practicaguias_pedro.recyclerViewParques

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewParquesSingleBinding

class ParquesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding = ViewParquesSingleBinding.bind(itemView)
    val miBoton: Button = itemView.findViewById(R.id.BEliminar_Parque)

    fun render(ParqueModel: ParquesR){
        binding.TVNombreParque.text = ParqueModel.nombre
        binding.TVDirec.text= ParqueModel.direccion
        binding.TVEnlace.text = ParqueModel.enlace
        binding.TVHorario.text = ParqueModel.horario
    }


}