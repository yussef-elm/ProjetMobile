package com.enseirb.foodrecipeapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.activities.RecipeActivity
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.repositories.DataBaseHelper

class FavoriteMealsAdapter(private val context: Context, private val meals: ArrayList<Meal>) : RecyclerView.Adapter<FavoriteMealViewHolder>() {
    private var dataBaseHelper: DataBaseHelper = DataBaseHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mealcard_l_item, parent, false)
        return FavoriteMealViewHolder(itemView)    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        if(meals !=null) {
            Glide.with(context)
                .load(meals[position].strMealThumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.strMealThumb);
            holder.strMeal.text = meals[position].strMeal
            holder.meal = meals[position]
            holder.checkBox.isChecked = dataBaseHelper.getCheckBoxState(meals[position])
            holder.checkBox.setOnClickListener() {
                if (holder.checkBox.isChecked) {
                    dataBaseHelper.checkMeal(meals[position], holder.checkBox)
                    dataBaseHelper.saveMeal(meals[position])
                } else {
                    dataBaseHelper.unCheckMeal(meals[position])
                    dataBaseHelper.removeMeal(meals[position])
                }
            }
        }else{
            holder.card.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return meals.count()
    }

}

class FavoriteMealViewHolder(itemView: View,var meal: Meal?=null) : RecyclerView.ViewHolder(itemView) {
    var strMeal: TextView = itemView.findViewById(R.id.strMeal)
    var strMealThumb: ImageView = itemView.findViewById(R.id.mealImage)
    var checkBox: CheckBox = itemView.findViewById(R.id.favorite)
    var card : CardView = itemView.findViewById(R.id.mealCard)
    init {
        itemView.setOnClickListener{
            val intent = Intent(itemView.context, RecipeActivity::class.java)
            intent.putExtra("idMeal",meal?.idMeal)
            intent.putExtra("strMeal",meal?.strMeal)
            intent.putExtra("strMealThumb",meal?.strMealThumb)
            intent.putExtra("recipeType", "Regular")
            itemView.context.startActivity(intent)
        }
    }
}