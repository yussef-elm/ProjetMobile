package com.enseirb.foodrecipeapp.models


import java.io.Serializable

class Categorie : Serializable {

    var idCategory             : String? = null
    var strCategory            : String? = null
    var strCategoryThumb       : String? = null
    var strCategoryDescription : String? = null

    constructor(){}

    constructor(
        idCategory: String?,
        strCategory: String?,
        strCategoryThumb: String?,
        strCategoryDescription: String?
    ) {
        this.idCategory = idCategory
        this.strCategory = strCategory
        this.strCategoryThumb = strCategoryThumb
        this.strCategoryDescription = strCategoryDescription
    }
}