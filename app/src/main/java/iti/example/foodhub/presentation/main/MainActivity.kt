package iti.example.foodhub.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import iti.example.foodhub.R
import iti.example.foodhub.databinding.ActivityMainBinding
import iti.example.foodhub.presentation.main.about.AboutFragment
import iti.example.foodhub.presentation.main.fav.FavoriteFragment
import iti.example.foodhub.presentation.main.home.HomeFragment
import iti.example.foodhub.presentation.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setupWindowInsets()
        }
        supportFragmentManager.beginTransaction().replace(R.id.main_nav_host_fragment, HomeFragment()).commit()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> navigateFragment(HomeFragment())
                R.id.search -> navigateFragment(SearchFragment())
                R.id.profile -> navigateFragment(AboutFragment())
                R.id.favorite -> navigateFragment(FavoriteFragment())
            }
            true
        }
    }
    private fun navigateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_nav_host_fragment, fragment).commit()
    }
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}