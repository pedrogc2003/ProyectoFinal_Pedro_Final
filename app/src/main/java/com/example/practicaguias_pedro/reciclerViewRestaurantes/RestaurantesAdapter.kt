package com.example.practicaguias_pedro.reciclerViewRestaurantes

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiaViewHolder
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesAdapter(val listaRestaurantes: List<RestaurantesR>) : RecyclerView.Adapter<RestaurantesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RestaurantesViewHolder(layoutInflater.inflate(R.layout.view_restaurantes_single, parent, false))
    }

    override fun getItemCount(): Int {
        return listaRestaurantes.size
    }

    override fun onBindViewHolder(holder: RestaurantesViewHolder, position: Int) {
        val item = listaRestaurantes[position]
        val builder = AlertDialog.Builder(holder.itemView.context)

        holder.miBoton.setOnClickListener {
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro de que quieres eliminar este restaurante?")

            builder.setPositiveButton("Si") { dialog, which ->
                val db = FirebaseFirestore.getInstance()

                val docRef = db.collection("restaurantes").document(item.nombre)

                docRef.delete()
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "Documento eliminado correctamente")
                        Toast.makeText(holder.itemView.context, "Restaurante eliminado correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error al eliminar el documento", e)
                        Toast.makeText(holder.itemView.context, "Error al eliminar la iglesia", Toast.LENGTH_SHORT).show()
                    }
            }

            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(holder.itemView.context, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            }

            // Crear un objeto AlertDialog y mostrar el cuadro de diálogo
            val dialog = builder.create()
            dialog.show()
        }

        holder.render(item)
    }
}