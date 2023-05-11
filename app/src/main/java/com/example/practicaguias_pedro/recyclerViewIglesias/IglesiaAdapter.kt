package com.example.practicaguias_pedro.recyclerViewIglesias

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.google.firebase.firestore.FirebaseFirestore

//Clase para dar valores a la Iglesia
class IglesiaAdapter(val listaIglesias: List<IglesiasR>) : RecyclerView.Adapter<IglesiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IglesiaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IglesiaViewHolder(layoutInflater.inflate(R.layout.view_iglesias_single, parent, false))
    }

    override fun getItemCount(): Int {
        return listaIglesias.size
    }

    override fun onBindViewHolder(holder: IglesiaViewHolder, position: Int) {
        val item = listaIglesias[position]
        val builder = AlertDialog.Builder(holder.itemView.context)

        holder.miBoton.setOnClickListener {
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro de que quieres eliminar esta iglesia?")

            builder.setPositiveButton("Si") { dialog, which ->
                val db = FirebaseFirestore.getInstance()

                val docRef = db.collection("iglesias").document(item.nombre)

                docRef.delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "Documento eliminado correctamente")
                        Toast.makeText(holder.itemView.context, "Iglesia eliminada correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error al eliminar el documento", e)
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
