package com.enseirb.foodrecipeapp.models;
import java.io.Serializable

class Meal : Serializable{
    var idMeal         : String? = null
    var strMeal        : String? = null
    var strMealThumb   : String? = null

    constructor(){}

    constructor(idMeal: String?, strMeal: String?, strMealThumb: String?) {
        this.idMeal = idMeal
        this.strMeal = strMeal
        this.strMealThumb = strMealThumb
    }


}