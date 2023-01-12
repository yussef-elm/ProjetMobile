package com.enseirb.foodrecipeapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.activities.RecipeActivity
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.repositories.DataBaseHelper

class MealAdapter(private val context: Context,private val itemTye : ItemType  ,private val meals: List<Meal>) : RecyclerView.Adapter<MealViewHolder>() {

    private var dataBaseHelper: DataBaseHelper = DataBaseHelper(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        if (itemTye == ItemType.Linear){
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mealcard_l_item, parent, false)
            return MealViewHolder(itemView)
        }else{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mealcard_g_item, parent, false)
            return MealViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(context)
            .load(meals[position].strMealThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.strMealThumb);
        holder.meal = meals[position]
        holder.strMeal.text = meals[position].strMeal
        //set checkBox state
        holder.checkBox.isChecked = dataBaseHelper.getCheckBoxState(meals[position])
        //save checkBox State
        holder.checkBox.setOnClickListener(){
            if (holder.checkBox.isChecked) {
                dataBaseHelper.checkMeal(meals[position],holder.checkBox)
                dataBaseHelper.saveMeal(meals[position])
            }else{
                dataBaseHelper.unCheckMeal(meals[position])
                dataBaseHelper.removeMeal(meals[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return meals.count()
    }


}

class MealViewHolder(itemView: View, var meal: Meal?=null) : RecyclerView.ViewHolder(itemView) {
    var strMeal: TextView = itemView.findViewById(R.id.strMeal)
    var strMealThumb: ImageView = itemView.findViewById(R.id.mealImage)
    var checkBox: CheckBox = itemView.findViewById(R.id.favorite)
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

enum class ItemType{
    Linear , Grid
}