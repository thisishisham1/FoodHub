package iti.example.foodhub.presentation.main.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R
import iti.example.foodhub.presentation.model.MealUiModel

class SearchAdapter(
    private val context: Context,
    private val mealList: List<MealUiModel>,
    private val onFavoriteClick: (MealUiModel) -> Unit
) : RecyclerView.Adapter<SearchAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class MealViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceType")
        private val mealThumbImageView: ImageView = itemView.findViewById(R.drawable.meal_image)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        @SuppressLint("ResourceType")
        private val favoriteButton: ImageButton = itemView.findViewById(R.drawable.baseline_favorite_24)

        fun bind(meal: MealUiModel) {
            mealNameTextView.text = meal.strMeal
            favoriteButton.setOnClickListener {
                meal.isFavorite = !meal.isFavorite
                onFavoriteClick(meal)
                notifyItemChanged(adapterPosition)
            }
        }
    }
}