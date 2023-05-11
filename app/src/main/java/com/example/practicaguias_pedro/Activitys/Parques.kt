package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.practicaguias_pedro.R

class Parques : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parques)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected  (item: MenuItem): Boolean {
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

