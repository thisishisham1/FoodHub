package iti.example.foodhub.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.responseModel.Meal

data class Item(
    val name: String,
    val imageResId: Int = R.drawable.profile
)


class ItemsAdapter(private val items: List<Meal>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTextView.text = item.strMeal
        Glide.with(holder.itemView.context)
            .load(item.strMealThumb)
            .into(holder.itemImageView)
    }

    override fun getItemCount(): Int = items.size
}