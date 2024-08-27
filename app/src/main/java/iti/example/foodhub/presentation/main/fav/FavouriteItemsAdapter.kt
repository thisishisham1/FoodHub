package iti.example.foodhub.presentation.main.fav

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.presentation.main.fav.FavouriteItemsAdapter.*
import iti.example.foodhub.presentation.main.home.ItemsAdapter
import iti.example.foodhub.presentation.main.home.MealDiffCallback
import iti.example.foodhub.presentation.model.MealUiModel

class FavouriteItemsAdapter (
    private val favouriteItems: List<MealUiModel>,
    private val onClick: (MealUiModel) -> Unit
) : RecyclerView.Adapter<FavouriteItemsAdapter.FavouriteItemViewHolder>() {

    inner class FavouriteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealName = itemView.findViewById<TextView>(R.id.itemNameTextView)
        private val mealImage = itemView.findViewById<ImageView>(R.id.itemImageView)

        fun bind(item: MealUiModel) {
            mealName.text = item.strMeal
            Glide.with(itemView.context).load(item.strMealThumb).into(mealImage)

            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return FavouriteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        holder.bind(favouriteItems[position])
    }

    override fun getItemCount(): Int = favouriteItems.size
}






























