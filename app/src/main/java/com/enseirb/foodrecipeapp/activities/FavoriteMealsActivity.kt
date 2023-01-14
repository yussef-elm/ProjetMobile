package com.enseirb.foodrecipeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.adapters.FavoriteMealsAdapter
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.repositories.DataBaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteMealsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var  favoritemealAdapter: FavoriteMealsAdapter
    private lateinit var MyFavouritMeals : ArrayList<Meal>
    private lateinit var dataBaseHelper: DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_meals)
        hiddeAppBar()
        setBottomNavbar()
        var progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        dataBaseHelper = DataBaseHelper(this)
        recyclerView = findViewById(R.id.Meals)
        MyFavouritMeals  = dataBaseHelper.getFavoriteMeals()
        favoritemealAdapter = FavoriteMealsAdapter(this , MyFavouritMeals)
        recyclerView.adapter = favoritemealAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,false)
        progressBar.visibility = View.GONE


    }

    fun setBottomNavbar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent);
                    Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_favorite -> {
                    val intent = Intent(this, FavoriteMealsActivity::class.java)
                    startActivity(intent);
                    Toast.makeText(this, "Favorite clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent);
                    Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_random -> {
                    val intent = Intent(this, RecipeActivity::class.java)
                    intent.putExtra("recipeType", "Random")
                    startActivity(intent);
                    Toast.makeText(this, "Random Recipe clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        val mStartActBtn = findViewById<Toolbar>(R.id.toolbarMeals)
        mStartActBtn.setOnClickListener {
            startActivity(Intent(this@FavoriteMealsActivity, MainActivity::class.java))
        }
    }


    fun  hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}