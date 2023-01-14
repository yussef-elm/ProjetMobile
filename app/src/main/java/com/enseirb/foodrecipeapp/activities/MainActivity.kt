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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.adapters.CategoriesAdapter
import com.enseirb.foodrecipeapp.models.Categorie
import com.enseirb.foodrecipeapp.services.CategorieService
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList



class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var  categoriesAdapter: CategoriesAdapter
    private lateinit var listOfCategories: ArrayList<Categorie>
    private var categoriesService = CategorieService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hiddeAppBar()
        setBottomNavbar()
        recyclerView = findViewById(R.id.recyclerView)
        getAllCategories()
        setSearchView()
    }

    fun getAllCategories(){
        categoriesService.setCategories(this)
        categoriesService.getCategories().observe(this, Observer { listCategories ->
            if (listCategories.isNotEmpty()) {
                listCategories.let {
                        item ->
                    runOnUiThread {
                        Log.i("Categorie",item.toString())
                        var progressBar = findViewById<ProgressBar>(R.id.progress_circular)
                        progressBar.visibility = View.GONE
                        categoriesAdapter = CategoriesAdapter(this@MainActivity , item)
                        recyclerView.adapter = categoriesAdapter
                        recyclerView.layoutManager = GridLayoutManager(applicationContext,3,LinearLayoutManager.VERTICAL,false)
                        listOfCategories = item
                    }
                }
            }
        })
    }


    fun setSearchView(){
        var searchView : SearchView = findViewById(R.id.search_recipe)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.let { filtredList(it,listOfCategories).isEmpty() } == false)
                { categoriesAdapter = CategoriesAdapter(this@MainActivity ,filtredList(p0,listOfCategories) )}
                else{
                    categoriesAdapter = CategoriesAdapter(this@MainActivity , arrayListOf())
                }
                recyclerView.adapter = categoriesAdapter
                recyclerView.layoutManager = GridLayoutManager(applicationContext,3,LinearLayoutManager.VERTICAL,false)
                return false
            }
        })

    }

    fun filtredList(query: String,list : ArrayList<Categorie>): ArrayList<Categorie>{
        var filteredlist : ArrayList<Categorie> = arrayListOf()
        for (categorie in list){
            if (categorie.strCategory?.lowercase(Locale.getDefault())?.startsWith(query.lowercase(Locale.getDefault())) == true)
            {  filteredlist.add(categorie) }
        }
        return filteredlist
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