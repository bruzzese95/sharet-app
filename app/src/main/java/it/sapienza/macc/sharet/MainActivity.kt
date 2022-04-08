package it.sapienza.macc.sharet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.sapienza.macc.sharet.databinding.ActivityMainBinding
import it.sapienza.macc.sharet.sharedresource.SharedResourceFragment

class MainActivity : AppCompatActivity() {

    internal lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}