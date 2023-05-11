package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.practicaguias_pedro.Intro
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Llamas a los intent para que te lleven a un lugar u otro dependiendo de la imagen que se seleccione
        binding.IIglesias.setOnClickListener {
            val intent = Intent(this, IglesiasA::class.java)
            startActivity(intent)
        }

        binding.IMonumentos.setOnClickListener {
            val intent = Intent(this,Monumentos::class.java)
            startActivity(intent)
        }

        binding.IPlazas.setOnClickListener {
            val intent = Intent(this,Parques::class.java)
            startActivity(intent)
        }

        binding.IRestaurantes.setOnClickListener {
            val intent = Intent(this,RestaurantesA::class.java)
            startActivity(intent)
        }

        binding.IOtros.setOnClickListener {
            val intent = Intent(this,Otros::class.java)
            startActivity(intent)
        }

        binding.ICerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,Intro::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m_iglesia->{
                val intent = Intent(this, IglesiasA::class.java)
                startActivity(intent)
            }
            R.id.m_parques->{
                val intent = Intent(this,Parques::class.java)
                startActivity(intent)
            }
            R.id.m_monumentos->{
                val intent = Intent(this,Monumentos::class.java)
                startActivity(intent)
            }
            R.id.m_restaurantes->{
                val intent = Intent(this,RestaurantesA::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }


}