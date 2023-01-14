package com.enseirb.foodrecipeapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enseirb.foodrecipeapp.services.urls.URLs
import com.enseirb.foodrecipeapp.models.*
import com.enseirb.foodrecipeapp.models.modelsresponse.RecipeResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.net.URL

class SearchService {

    private val meal = MutableLiveData<ArrayList<Meal>>()
    private var callbackBuilder = CallbackBuilder()

    fun searchMeal(context: Context, strMeal: String){
        val url = URL(strMeal.let { URLs.SEARCH_MEAL.replace("{strMeal}", it) })
        callbackBuilder.GET(url,object: Callback {
            override fun onFailure(call: Call, e : IOException){
                Log.e("OKHttp",e.localizedMessage)
                run {
                    Toast.makeText( context,"Problem on searching meal", Toast.LENGTH_LONG)
                        .show()

                }
            }
            override fun onResponse(call: Call, response : Response){
                val listMeals = ArrayList<Meal>()
                response.body?.string()?.let {
                    var gson = Gson();
                    var recipeResponse = gson.fromJson(it, RecipeResponse ::class.java)
                    recipeResponse.meals?.let { item ->
                        run {
                            Log.i("Meal",item.toString())
                            for (recipe in item){
                                listMeals.add(Meal(recipe.idMeal,recipe.strMeal,recipe.strMealThumb))
                            }
                            meal.postValue(listMeals)
                        }
                    }
                }
            }
        })
    }

    fun getResult(): LiveData<ArrayList<Meal>> {
        return meal
    }
}