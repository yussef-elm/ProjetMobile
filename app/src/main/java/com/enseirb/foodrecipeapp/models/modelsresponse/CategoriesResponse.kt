package com.enseirb.foodrecipeapp.models.modelsresponse

import com.enseirb.foodrecipeapp.models.Categorie
import java.io.Serializable

class CategoriesResponse : Serializable {

    var categories : ArrayList<Categorie> = arrayListOf()
}