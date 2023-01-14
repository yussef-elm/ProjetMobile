package com.enseirb.foodrecipeapp.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enseirb.foodrecipeapp.R
import com.enseirb.foodrecipeapp.activities.MealsActivity
import com.enseirb.foodrecipeapp.models.Categorie

class CategoriesAdapter(private val context: Context, private val categories: ArrayList<Categorie>) : RecyclerView.Adapter<CategorieViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categoriescard_item, parent, false)
        return CategorieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        Glide.with(context)
            .load(categories[position].strCategoryThumb)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.strCategoryThumb);
        holder.strCategory.text = categories[position].strCategory
        holder.categorie= categories[position]
    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}

class CategorieViewHolder(itemView: View,var categorie: Categorie?=null) : RecyclerView.ViewHolder(itemView) {
    var strCategory: TextView = itemView.findViewById(R.id.strCategory)
    var strCategoryThumb: ImageView = itemView.findViewById(R.id.strCategoryThumb)
    init {
        itemView.setOnClickListener{
            val intent = Intent(itemView.context, MealsActivity::class.java)
            intent.putExtra("strCategory", categorie?.strCategory)
            intent.putExtra("strCategoryThumb",categorie?.strCategoryThumb)
            intent.putExtra("strCategoryDescription",categorie?.strCategoryDescription)
            itemView.context.startActivity(intent)
        }
    }


}