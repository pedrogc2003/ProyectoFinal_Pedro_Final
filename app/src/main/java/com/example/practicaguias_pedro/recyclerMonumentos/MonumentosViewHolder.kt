package com.example.practicaguias_pedro.recyclerMonumentos

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewMonumentosSingleBinding

class MonumentosViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding = ViewMonumentosSingleBinding.bind(itemView)
    val miBoton: Button = itemView.findViewById(R.id.BEliminar_Monumento)

    fun render(MonumentoModel: MonumentosR){
        binding.TVNombreMonumento.text = MonumentoModel.nombre
        binding.TVDirec.text= MonumentoModel.direccion
        binding.TVEnlace.text = MonumentoModel.enlace
        binding.TVHorario.text = MonumentoModel.horario
    }
}