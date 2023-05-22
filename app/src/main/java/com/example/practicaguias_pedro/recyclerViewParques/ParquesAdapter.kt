package com.example.practicaguias_pedro.recyclerViewParques

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
import com.example.practicaguias_pedro.databinding.ViewParquesSingleBinding
import com.google.firebase.firestore.FirebaseFirestore

class ParquesAdapter(private val listaParques: MutableList<ParquesR>) : RecyclerView.Adapter<ParquesAdapter.ParquesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParquesViewHolder {
        val binding = ViewParquesSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParquesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaParques.size
    }

    override fun onBindViewHolder(holder: ParquesViewHolder, position: Int) {
        val parque = listaParques[position]
        holder.bind(parque)
    }

    inner class ParquesViewHolder(private val binding: ViewParquesSingleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.BEliminarParque.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val parque = listaParques[position]
                    showDeleteDialog(parque)
                }
            }

            binding.TVEnlace.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val parque = listaParques[position]
                    openLink(parque.enlace)
                }
            }
        }

        fun bind(parque: ParquesR) {
            binding.TVNombreParque.text = parque.nombre
            binding.TVDirec.text = parque.direccion
            binding.TVEnlace.text = parque.enlace
            binding.TVHorario.text = parque.horario
        }

        private fun showDeleteDialog(parque: ParquesR) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Eliminar")
                .setMessage("¿Estás seguro de que quieres eliminar este parque?")
                .setPositiveButton("Sí") { _, _ ->
                    deleteParque(parque)
                }
                .setNegativeButton("No", null)
                .show()
        }

        private fun deleteParque(parque: ParquesR) {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("parques").document(parque.nombre)

            docRef.delete()
                .addOnSuccessListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listaParques.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listaParques.size)
                        Toast.makeText(binding.root.context, "Parque eliminado correctamente", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error al eliminar el parque", e)
                    Toast.makeText(binding.root.context, "Error al eliminar el parque", Toast.LENGTH_SHORT).show()
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
