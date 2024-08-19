package iti.example.foodhub.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R

data class Item(
    val name: String,
    val recipe: String,
    val imageResId: Int = R.drawable.profile
)


class ItemsAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTextView.text = item.name
        holder.itemPriceTextView.text = item.recipe
        holder.itemImageView.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = items.size
}