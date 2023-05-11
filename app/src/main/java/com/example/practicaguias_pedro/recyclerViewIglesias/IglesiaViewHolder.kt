package com.example.practicaguias_pedro.recyclerViewIglesias

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewIglesiasSingleBinding

//Valores que se cogen dentro del Holder y dar valores dentro del Layout
class IglesiaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val binding = ViewIglesiasSingleBinding.bind(itemView)
    val miBoton: Button = itemView.findViewById(R.id.BEliminar_Restaurante)

    fun render(IglesiaModel: IglesiasR){
        binding.TVNombreIglesia.text = IglesiaModel.nombre
        binding.TVDirec.text= IglesiaModel.direccion
        binding.TVEnlace.text = IglesiaModel.enlace
        binding.TVHorario.text = IglesiaModel.horario
    }
}