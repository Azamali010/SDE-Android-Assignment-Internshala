package com.example.notetask.views


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.notetask.R
import com.example.notetask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        goToFragment(SignInFragment())

    }

    private fun goToFragment(fragment: Fragment){
//      fragmentManager = supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(R.id.FragmentContainerView, fragment)
            .commit()
    }
}