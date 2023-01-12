package com.enseirb.foodrecipeapp.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
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
    fun hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }

}