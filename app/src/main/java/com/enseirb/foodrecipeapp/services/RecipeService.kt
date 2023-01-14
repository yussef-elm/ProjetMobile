package com.enseirb.foodrecipeapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enseirb.foodrecipeapp.services.urls.URLs
import com.enseirb.foodrecipeapp.models.Recipe
import com.enseirb.foodrecipeapp.models.modelsresponse.RecipeResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class RecipeService {

    private val searchedRecipe = MutableLiveData<Recipe>()
    private val randomRecipe = MutableLiveData<Recipe>()
    private var callbackBuilder = CallbackBuilder()

    fun setRecipe(context : Context, idMeal: String?) {
        val url = URL(idMeal?.let { URLs.RECIPE.replace("{idMeal}", it) })
        callbackBuilder.GET(url,object: Callback {
            override fun onFailure(call: Call, e : IOException){
                Log.e("OKHttp",e.localizedMessage)
                    Toast.makeText(context,"Problem on loading recipe", Toast.LENGTH_LONG)
                        .show()
            }
            override fun onResponse(call: Call, response : Response){
                var recipe = Recipe()
                val responseBody = response.body?.string()
                val returnedRecipe = responseBody?.let { JSONObject(it) }?.getJSONArray("meals")?.getJSONObject(0)

                responseBody?.let {
                    var gson = Gson();
                    var recipeResponse = gson.fromJson(it, RecipeResponse ::class.java)
                    recipeResponse.meals?.let { item ->
                        run {
                            recipe.strMeasure = item[0].strMeal
                            recipe.strCategory=item[0].strCategory
                            recipe.strArea=item[0].strArea
                            recipe.strMeal=item[0].strMeal
                            recipe.strMealThumb=item[0].strMealThumb
                            recipe.strInstructions = item[0].strInstructions
                            recipe.strYoutube = item[0].strYoutube
                        }
                    }
                }
                for (j in 1 until 21) {
                    if (returnedRecipe != null) {
                        if (returnedRecipe.getString("strIngredient$j").trim().isNotEmpty() && returnedRecipe.getString("strIngredient$j").trim() != "null"
                            && returnedRecipe.getString("strMeasure$j").trim().isNotEmpty() && returnedRecipe.getString("strMeasure$j").trim() != "null" ){
                            recipe.strIngredient += "\n \u2022 " + returnedRecipe.getString("strIngredient$j")
                            recipe.strMeasure += "\n : " + returnedRecipe.getString("strMeasure$j")

                        }
                    }

                }
                searchedRecipe.postValue(recipe)
            }
        })

    }

    fun setRandomRecipe(context : Context) {
        val url = URL(URLs.RANDOMRECIPE)
        callbackBuilder.GET(url,object: Callback {
            override fun onFailure(call: Call, e : IOException){
                Log.e("OKHttp",e.localizedMessage)
                Toast.makeText(context,"Problem on loading the random recipe", Toast.LENGTH_LONG)
                    .show()
            }
            override fun onResponse(call: Call, response : Response){
                var recipe = Recipe()
                val responseBody = response.body?.string()
                val returnedRecipe = responseBody?.let { JSONObject(it) }?.getJSONArray("meals")?.getJSONObject(0)

                responseBody?.let {
                    var gson = Gson();
                    var recipeResponse = gson.fromJson(it, RecipeResponse ::class.java)
                    recipeResponse.meals?.let { item ->
                        run {
                            recipe.strMeasure = item[0].strMeal
                            recipe.strCategory=item[0].strCategory
                            recipe.strArea=item[0].strArea
                            recipe.strMeal=item[0].strMeal
                            recipe.strMealThumb=item[0].strMealThumb
                            recipe.strInstructions = item[0].strInstructions
                            recipe.strYoutube = item[0].strYoutube
                        }
                    }
                }
                for (j in 1 until 21) {
                    if (returnedRecipe != null) {
                        if (returnedRecipe.getString("strIngredient$j").trim().isNotEmpty() && returnedRecipe.getString("strIngredient$j").trim() != "null"
                            && returnedRecipe.getString("strMeasure$j").trim().isNotEmpty() && returnedRecipe.getString("strMeasure$j").trim() != "null" ){
                            recipe.strIngredient += "\n \u2022 " + returnedRecipe.getString("strIngredient$j")
                            recipe.strMeasure += "\n : " + returnedRecipe.getString("strMeasure$j")

                        }
                    }

                }
                randomRecipe.postValue(recipe)
            }
        })

    }

    fun getRandomRecipe(): LiveData<Recipe> {
        return randomRecipe
    }
    fun getRecipe(): LiveData<Recipe> {
        return searchedRecipe
    }

}