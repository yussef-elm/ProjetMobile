package com.enseirb.foodrecipeapp.services.urls

object URLs {

    var CATEGORIES      = "https://www.themealdb.com/api/json/v1/1/categories.php"
    var MEALS           = "https://www.themealdb.com/api/json/v1/1/filter.php?c={strCategory}"
    var SEARCH_MEAL     = "https://www.themealdb.com/api/json/v1/1/search.php?s={strMeal}"
    var RECIPE          = "https://www.themealdb.com/api/json/v1/1/lookup.php?i={idMeal}"
    var RANDOMRECIPE    = "https://www.themealdb.com/api/json/v1/1/random.php"
}