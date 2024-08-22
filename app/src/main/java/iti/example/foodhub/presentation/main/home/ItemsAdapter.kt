package iti.example.foodhub.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.presentation.model.MealUiModel

class ItemsAdapter(private val onClick: (MealUiModel) -> Unit) :
    ListAdapter<MealUiModel, ItemsAdapter.ViewHolder>(MealDiffCallback()) {

    class ViewHolder(itemView: View, val onClick: (MealUiModel) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val itemCardView: CardView = itemView.findViewById(R.id.item_card)
        private var currentItem: MealUiModel? = null

        init {
            itemCardView.setOnClickListener {
                currentItem?.let { onClick(it) }
            }
        }

        fun bind(item: MealUiModel) {
            currentItem = item
            itemNameTextView.text = item.strMeal
            Glide.with(itemView.context)
                .load(item.strMealThumb)
                .into(itemImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class MealDiffCallback : DiffUtil.ItemCallback<MealUiModel>() {
    override fun areItemsTheSame(oldItem: MealUiModel, newItem: MealUiModel): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealUiModel, newItem: MealUiModel): Boolean {
        return oldItem == newItem
    }
}