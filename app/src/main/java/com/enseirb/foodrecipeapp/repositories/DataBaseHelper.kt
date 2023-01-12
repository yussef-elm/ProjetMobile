package com.enseirb.foodrecipeapp.repositories

import android.content.Context
import android.widget.CheckBox
import com.enseirb.foodrecipeapp.models.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataBaseHelper (private val context: Context ) {

    var sharedPreferences= context.getSharedPreferences("My Favorite Meals", Context.MODE_PRIVATE)
    var editor =sharedPreferences.edit()
    var gson = Gson()
    var myMeals : ArrayList<Meal> = arrayListOf()

    fun checkMeal(meal: Meal,checkBox: CheckBox){
        editor.putBoolean(meal.strMeal,checkBox.isChecked)
        editor.apply()
    }

    fun unCheckMeal(meal: Meal){
        editor.remove(meal.strMeal)
        editor.apply()
    }

    fun getCheckBoxState(meal :Meal) : Boolean{
        return sharedPreferences.getBoolean(meal.strMeal,false)
    }

    fun saveMeal(meal:Meal){
        myMeals= getFavoriteMeals()
        myMeals.add(meal)
        editor.putString("wishing_List_meals",gson.toJson(myMeals).toString())
        editor.apply()
    }

    fun removeMeal(meal:Meal){
        myMeals= getFavoriteMeals()
        run lit@ {myMeals.forEach {if (it.strMeal.equals(meal.strMeal)) {
            myMeals.remove(it)
            return@lit
        } }}
        editor.putString("wishing_List_meals",gson.toJson(myMeals).toString())
        editor.apply()
    }

    fun getFavoriteMeals(): ArrayList<Meal> {
        var data = sharedPreferences.getString("wishing_List_meals", "[]")
        var type = object : TypeToken<ArrayList<Meal>>() {}.type
        myMeals= gson.fromJson(data, type)
        return myMeals
    }

}