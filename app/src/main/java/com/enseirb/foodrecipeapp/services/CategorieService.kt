package com.enseirb.foodrecipeapp.services


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enseirb.foodrecipeapp.services.urls.URLs
import com.enseirb.foodrecipeapp.models.Categorie
import com.enseirb.foodrecipeapp.models.modelsresponse.CategoriesResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.net.URL

class CategorieService {
    private val categories = MutableLiveData<ArrayList<Categorie>>()
    private var callbackBuilder = CallbackBuilder()

    fun setCategories(context: Context){
        val url = URL(URLs.CATEGORIES)
        callbackBuilder.GET(url,object: Callback {
            override fun onFailure(call: Call, e : IOException){
                Log.e("OKHttp",e.localizedMessage)
                run {
                    Toast.makeText( context,"Problem on loading categories", Toast.LENGTH_LONG)
                        .show()

                }
            }
            override fun onResponse(call: Call, response : Response){
                val listCategories = ArrayList<Categorie>()
                response.body?.string()?.let {
                    var gson = Gson();
                    var categoriesResponse = gson.fromJson(it, CategoriesResponse ::class.java)
                    categoriesResponse.categories?.let { item ->
                        run {
                            for (c in item){
                                listCategories.add(Categorie(c.idCategory,c.strCategory,c.strCategoryThumb,c.strCategoryDescription))
                            }
                            categories.postValue(listCategories)
                        }
                    }
                }
            }
        })
    }

    fun getCategories(): LiveData<ArrayList<Categorie>> {
        return categories
    }

}