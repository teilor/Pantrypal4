package com.example.pantrypal4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pantrypal4.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Inicio())

        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){

                R.id.home -> replaceFragment(Inicio())
                R.id.local -> replaceFragment(Local())
                R.id.refeicao -> replaceFragment(Refeicao())
                R.id.lista -> replaceFragment(Lista())

                else ->{

                }
            }
            true
        }
        val button: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        button.setOnClickListener {
            val intent = Intent(this, Novo::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

}