package com.enseirb.foodrecipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.adapters.FavoriteMealsAdapter
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.repositories.DataBaseHelper

class FavoriteMealsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var  favoritemealAdapter: FavoriteMealsAdapter
    private lateinit var MyFavouritMeals : ArrayList<Meal>
    private lateinit var dataBaseHelper: DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_meals)
        hiddeAppBar()
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

    fun  hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}