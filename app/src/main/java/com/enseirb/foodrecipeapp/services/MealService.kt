package com.enseirb.foodrecipeapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enseirb.foodrecipeapp.models.Categorie
import com.enseirb.foodrecipeapp.models.Meal
import com.enseirb.foodrecipeapp.services.urls.URLs
import com.enseirb.foodrecipeapp.models.modelsresponse.MealResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.net.URL

class MealService {

    private val meals = MutableLiveData<ArrayList<Meal>>()
    private var callbackBuilder = CallbackBuilder()

    fun setMeals(context: Context,categorie: Categorie){
        val url = URL(categorie.strCategory?.let { URLs.MEALS.replace("{strCategory}", it) })
        callbackBuilder.GET(url,object: Callback {
            override fun onFailure(call: Call, e : IOException){
                Log.e("OKHttp",e.localizedMessage)
                run {
                    Toast.makeText( context,"Problem on loading meals", Toast.LENGTH_LONG)
                        .show()

                }
            }
            override fun onResponse(call: Call, response : Response){
                val listMeals = ArrayList<Meal>()
                response.body?.string()?.let {
                    var gson = Gson();
                    var categoriesResponse = gson.fromJson(it, MealResponse ::class.java)
                    categoriesResponse.meals?.let { item ->
                        run {
                            for (m in item){
                                listMeals.add(Meal(m.idMeal,m.strMeal,m.strMealThumb))
                            }
                            meals.postValue(listMeals)
                        }
                    }
                }
            }
        })
    }

    fun getMeals(): LiveData<ArrayList<Meal>> {
        return meals
    }
}