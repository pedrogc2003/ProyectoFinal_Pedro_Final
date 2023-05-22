import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ViewRestaurantesSingleBinding
import com.example.practicaguias_pedro.recyclerViewRestaurantes.RestaurantesR

class RestaurantesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ViewRestaurantesSingleBinding.bind(itemView)
    private val tvEnlace: TextView = itemView.findViewById(R.id.TVEnlace)

    fun render(RestauranteModel: RestaurantesR) {
        binding.TVNombreRestaurante.text = RestauranteModel.nombre
        binding.TVDirec.text = RestauranteModel.direccion
        binding.TVHorario.text = RestauranteModel.horario

        // Establecer el enlace de Google Maps como texto en el TextView
        tvEnlace.text = RestauranteModel.enlace
        tvEnlace.setOnClickListener {
            val url = "http://maps.google.com/maps?q=${RestauranteModel.enlace}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            itemView.context.startActivity(intent)
        }
    }
}
