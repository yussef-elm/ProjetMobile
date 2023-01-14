package com.enseirb.foodrecipeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.adapters.ItemType
import com.enseirb.foodrecipeapp.adapters.MealAdapter
import com.enseirb.foodrecipeapp.services.SearchService
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchActivity : AppCompatActivity() {

    private lateinit var mealAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private var searchService = SearchService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hiddeAppBar()
        setContentView(R.layout.activity_search)
        recyclerView = findViewById(R.id.search_result)
        setBottomNavbar()
        setSearchView()
    }

    fun getsearchedMeal(strMeal : String){
        searchService.searchMeal(this,strMeal)
        searchService.getResult().observe(this, Observer { listMeals ->
            if(listMeals.isNotEmpty()){
                listMeals.let { item ->
                    runOnUiThread {
                        Log.i("Meal",item.toString())
                        var progressBar = findViewById<ProgressBar>(R.id.progress_circular)
                        progressBar.visibility = View.GONE
                        mealAdapter = MealAdapter(this@SearchActivity ,ItemType.Linear,item)
                        recyclerView.adapter = mealAdapter
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
                    }
                }
            }
        })
    }


    fun setSearchView(){
        var searchView : SearchView = findViewById(R.id.search_recipe)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var progressBar = findViewById<ProgressBar>(R.id.progress_circular)
                progressBar.visibility = View.VISIBLE
                if (p0 != null) {
                    getsearchedMeal(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

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
    }
    fun hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}