package iti.example.foodhub.presentation.main.fav


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.responseModel.FavoriteItem


class FavoriteItemsAdapter(
    private val favoriteItems: List<FavoriteItem>,
    private val onItemClick: (FavoriteItem) -> Unit
) : RecyclerView.Adapter<FavoriteItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(favoriteItem: FavoriteItem, onItemClick: (FavoriteItem) -> Unit) {
            itemNameTextView.text = favoriteItem.name
            Glide.with(itemView.context)
                .load(favoriteItem.imageUrl)
                .into(itemImageView)

            itemView.setOnClickListener {
                onItemClick(favoriteItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteItems[position], onItemClick)
    }

    override fun getItemCount(): Int = favoriteItems.size
}
