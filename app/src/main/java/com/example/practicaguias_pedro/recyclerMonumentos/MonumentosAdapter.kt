package com.example.practicaguias_pedro.recyclerMonumentos

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.databinding.ViewMonumentosSingleBinding
import com.google.firebase.firestore.FirebaseFirestore


class MonumentosAdapter (private val listaMonumentos: MutableList<MonumentosR>) : RecyclerView.Adapter<MonumentosAdapter.MonumentosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentosViewHolder {
        val binding = ViewMonumentosSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MonumentosViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaMonumentos.size
    }

    override fun onBindViewHolder(holder: MonumentosViewHolder, position: Int) {
        val monumento = listaMonumentos[position]
        holder.bind(monumento)
    }

    inner class MonumentosViewHolder(private val binding: ViewMonumentosSingleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.BEliminarMonumento.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val monumento = listaMonumentos[position]
                    eliminarMonumento(monumento, position)
                }
            }

            binding.TVEnlace.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val monumento = listaMonumentos[position]
                    abrirEnlace(monumento.enlace)
                }
            }
        }

        fun bind(monumento: MonumentosR) {
            binding.TVNombreMonumento.text = monumento.nombre
            binding.TVDirec.text = monumento.direccion
            binding.TVEnlace.text = monumento.enlace
            binding.TVHorario.text = monumento.horario
        }

        private fun eliminarMonumento(monumento: MonumentosR, position: Int) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro de que quieres eliminar este monumento?")

            builder.setPositiveButton("Sí") { dialog, _ ->
                val db = FirebaseFirestore.getInstance()
                val docRef = db.collection("monumentos").document(monumento.nombre)

                docRef.delete()
                    .addOnSuccessListener {
                        listaMonumentos.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listaMonumentos.size)
                        Log.d(ContentValues.TAG, "Documento eliminado correctamente")
                        Toast.makeText(binding.root.context, "Monumento eliminado correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error al eliminar el documento", e)
                        Toast.makeText(binding.root.context, "Error al eliminar el monumento", Toast.LENGTH_SHORT).show()
                    }
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        private fun abrirEnlace(enlace: String) {
            val webpage = Uri.parse(enlace)
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            try {
                binding.root.context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(binding.root.context, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Error al abrir el enlace: $enlace", e)
            }
        }
    }
}