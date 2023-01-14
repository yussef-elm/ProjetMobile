package com.enseirb.foodrecipeapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.adapters.ItemType
import com.enseirb.foodrecipeapp.adapters.MealAdapter
import com.enseirb.foodrecipeapp.models.Categorie
import com.enseirb.foodrecipeapp.services.MealService
import com.google.android.material.bottomnavigation.BottomNavigationView


class MealsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var  mealAdapter: MealAdapter
    private lateinit var categorieImage : ImageView
    private lateinit var categorieImageBackground : ImageView
    private lateinit var categorieDescription: TextView
    private lateinit var strCategorie: TextView


    private var categorie = Categorie()
    private var mealService = MealService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        hiddeAppBar()
        setBottomNavbar()
        recyclerView = findViewById(R.id.Meals)
        setCategorieCard()
        getAllMeals()
    }


    fun setCategorieCard(){
        categorie.strCategory= intent.getStringExtra("strCategory")
        categorie.strCategoryThumb= intent.getStringExtra("strCategoryThumb")
        categorie.strCategoryDescription=intent.getStringExtra("strCategoryDescription")
        categorieImage = findViewById(R.id.imgCategorie1)
        categorieImageBackground = findViewById(R.id.categorieImageBackground)
        categorieDescription = findViewById(R.id.categorieDescription)
        strCategorie = findViewById(R.id.strCategory)
        categorieDescription.text = categorie.strCategoryDescription
        strCategorie.text = categorie.strCategory
        Glide.with(this@MealsActivity)
            .load(categorie.strCategoryThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(categorieImageBackground);

        Glide.with(this@MealsActivity)
            .load(categorie.strCategoryThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(categorieImage);
    }


    fun getAllMeals(){
        mealService.setMeals(this,categorie)
        mealService.getMeals().observe(this, Observer { listMeals ->
            if(listMeals.isNotEmpty()){
                listMeals.let { item ->
                    runOnUiThread {
                        var progressBar = findViewById<ProgressBar>(R.id.progress_circular)
                        mealAdapter = MealAdapter(this@MealsActivity ,ItemType.Grid, item)
                        recyclerView.adapter = mealAdapter
                        recyclerView.layoutManager = GridLayoutManager(applicationContext,2,
                            LinearLayoutManager.VERTICAL,false)
                        progressBar.visibility = View.GONE
                    }
                }
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
        val mStartActBtn = findViewById<Toolbar>(R.id.toolbarMeals)
        mStartActBtn.setOnClickListener {
            startActivity(Intent(this@MealsActivity, MainActivity::class.java))
        }
    }

    fun hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }

}