package com.example.practicaguias_pedro.reciclerViewRestaurantes

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewIglesiasSingleBinding
import com.example.practicaguias_pedro.databinding.ViewRestaurantesSingleBinding

class RestaurantesViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding = ViewRestaurantesSingleBinding.bind(itemView)
    val miBoton: Button = itemView.findViewById(R.id.BEliminar_Restaurante)

    fun render(RestauranteModel: RestaurantesR){
        binding.TVNombreRestaurante.text = RestauranteModel.nombre
        binding.TVDirec.text= RestauranteModel.direccion
        binding.TVEnlace.text = RestauranteModel.enlace
        binding.TVHorario.text = RestauranteModel.horario
    }
}