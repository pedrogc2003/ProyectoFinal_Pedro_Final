package com.example.practicaguias_pedro.recyclerViewIglesias

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewIglesiasSingleBinding
import com.google.firebase.firestore.FirebaseFirestore

class IglesiaAdapter(val listaIglesias: MutableList<IglesiasR>) : RecyclerView.Adapter<IglesiaAdapter.IglesiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IglesiaViewHolder {
        val binding = ViewIglesiasSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IglesiaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaIglesias.size
    }

    override fun onBindViewHolder(holder: IglesiaViewHolder, position: Int) {
        val iglesia = listaIglesias[position]
        holder.bind(iglesia)
    }

    inner class IglesiaViewHolder(private val binding: ViewIglesiasSingleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.BEliminarIglesia.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val iglesia = listaIglesias[position]
                    showDeleteDialog(iglesia)
                }
            }

            binding.TVEnlace.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val iglesia = listaIglesias[position]
                    openLink(iglesia.enlace)
                }
            }
        }

        fun bind(iglesia: IglesiasR) {
            binding.TVNombreIglesia.text = iglesia.nombre
            binding.TVDirec.text = iglesia.direccion
            binding.TVEnlace.text = iglesia.enlace
            binding.TVHorario.text = iglesia.horario
        }

        private fun showDeleteDialog(iglesia: IglesiasR) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Eliminar")
                .setMessage("¿Estás seguro de que quieres eliminar esta iglesia?")
                .setPositiveButton("Sí") { _, _ ->
                    deleteIglesia(iglesia)
                }
                .setNegativeButton("No", null)
                .show()
        }

        private fun deleteIglesia(iglesia: IglesiasR) {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("iglesias").document(iglesia.nombre)

            docRef.delete()
                .addOnSuccessListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listaIglesias.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, listaIglesias.size)
                        Toast.makeText(binding.root.context, "Iglesia eliminada correctamente", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error al eliminar la iglesia", e)
                    Toast.makeText(binding.root.context, "Error al eliminar la iglesia", Toast.LENGTH_SHORT).show()
                }
        }

        private fun openLink(enlace: String) {
            val webpage: Uri = Uri.parse(enlace)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(binding.root.context.packageManager) != null) {
                binding.root.context.startActivity(intent)
            } else {
                Toast.makeText(binding.root.context, "No se encontró una aplicación compatible para abrir el enlace", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
