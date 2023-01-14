package com.enseirb.foodrecipeapp.activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.services.RecipeService


class RecipeActivity : AppCompatActivity() {

    private lateinit var mealImageBackground : ImageView
    private lateinit var toolbarTitle : TextView
    private lateinit var strMeal: TextView
    private lateinit var strCategory : TextView
    private lateinit var strArea : TextView
    private lateinit var ingredient : TextView
    private lateinit var measure : TextView
    private lateinit var instructions : TextView
    private lateinit var youtube : LinearLayout
    private lateinit var meal: Meal
    private var recipeService = RecipeService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        hiddeAppBar()
        var recipeType = intent.getStringExtra("recipeType")
        toolbarTitle = findViewById(R.id.Title)
        mealImageBackground = findViewById(R.id.mealImageBackground)
        strCategory =findViewById(R.id.strCategory)
        strMeal = findViewById(R.id.strMeal)
        strArea =findViewById(R.id.areaMeal)
        ingredient = findViewById(R.id.ingredient)
        measure = findViewById(R.id.measure)
        instructions = findViewById(R.id.instructions)
        youtube = findViewById(R.id.link_youtube)
        if(recipeType.equals("Random")){
            getRandomRecipe()
        }else{
            setMealCard()
            getRecipe(meal.idMeal)
        }

    }

    fun setMealCard(){
        meal = Meal(intent.getStringExtra("idMeal"),
            intent.getStringExtra("strMeal"),
            intent.getStringExtra("strMealThumb"))
        strMeal.text = meal.strMeal
        Glide.with(this@RecipeActivity)
            .load(meal.strMealThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mealImageBackground);
    }

    fun getRecipe(idMeal: String?){
        recipeService.setRecipe(this,idMeal)
        recipeService.getRecipe().observe(this, Observer { searchedRecipe ->
            strCategory.text=searchedRecipe.strCategory
            strArea.text=searchedRecipe.strArea
            ingredient.text= searchedRecipe.strIngredient
            measure.text=searchedRecipe.strMeasure
            instructions.text = searchedRecipe.strInstructions
            youtube.setOnClickListener(){
                val intentYoutube = Intent(Intent.ACTION_VIEW)
                intentYoutube.data = Uri.parse(searchedRecipe.strYoutube)
                startActivity(intentYoutube)
            }
            instructions.justificationMode = JUSTIFICATION_MODE_INTER_WORD

        })

    }


    fun getRandomRecipe(){
        toolbarTitle.text = "Random Recipe"
        recipeService.setRandomRecipe(this)
        recipeService.getRandomRecipe().observe(this, Observer { randomRecipe ->
            strCategory.text=randomRecipe.strCategory
            strArea.text=randomRecipe.strArea
            ingredient.text= randomRecipe.strIngredient
            measure.text=randomRecipe.strMeasure
            instructions.text = randomRecipe.strInstructions
            strMeal.text = randomRecipe.strMeal
            Glide.with(this@RecipeActivity)
                .load(randomRecipe.strMealThumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mealImageBackground);
            instructions.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            youtube.setOnClickListener(){
                val intentYoutube = Intent(Intent.ACTION_VIEW)
                intentYoutube.data = Uri.parse(randomRecipe.strYoutube)
                startActivity(intentYoutube)
            }
        })

    }



    fun  hiddeAppBar(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}


