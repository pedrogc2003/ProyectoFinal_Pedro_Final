package com.example.practicaguias_pedro.recyclerViewRestaurantes

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewRestaurantesSingleBinding
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesAdapter(private val listaRestaurantes: MutableList<RestaurantesR>) : RecyclerView.Adapter<RestaurantesAdapter.RestaurantesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantesViewHolder {
        val binding = ViewRestaurantesSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaRestaurantes.size
    }

    override fun onBindViewHolder(holder: RestaurantesViewHolder, position: Int) {
        val restaurante = listaRestaurantes[position]
        holder.bind(restaurante)
    }

    inner class RestaurantesViewHolder(private val binding: ViewRestaurantesSingleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.BEliminarResrtaurante.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val restaurante = listaRestaurantes[position]
                    showDeleteDialog(restaurante)
                }
            }

            binding.TVEnlace.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val restaurante = listaRestaurantes[position]
                    openLink(restaurante.enlace)
                }
            }
        }

        fun bind(restaurante: RestaurantesR) {
            binding.TVNombreRestaurante.text = restaurante.nombre
            binding.TVDirec.text = restaurante.direccion
            binding.TVEnlace.text = restaurante.enlace
            binding.TVHorario.text = restaurante.horario
        }

        private fun showDeleteDialog(restaurante: RestaurantesR) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Eliminar")
                .setMessage("¿Estás seguro de que quieres eliminar este restaurante?")
                .setPositiveButton("Sí") { _, _ ->
                    deleteRestaurante(restaurante)
                }
                .setNegativeButton("No", null)
                .show()
        }

        private fun deleteRestaurante(restaurante: RestaurantesR) {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("restaurantes").document(restaurante.nombre)

            docRef.delete()
                .addOnSuccessListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listaRestaurantes.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listaRestaurantes.size)
                        Toast.makeText(binding.root.context, "Restaurante eliminado correctamente", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error al eliminar el restaurante", e)
                    Toast.makeText(binding.root.context, "Error al eliminar el restaurante", Toast.LENGTH_SHORT).show()
                }
        }

        private fun openLink(enlace: String) {
            val webpage: Uri = Uri.parse(enlace)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(binding.root.context.packageManager) != null) {
                binding.root.context.startActivity(intent)
            } else {
                Toast.makeText(binding.root.context, "No se encontró una aplicación de navegación", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
