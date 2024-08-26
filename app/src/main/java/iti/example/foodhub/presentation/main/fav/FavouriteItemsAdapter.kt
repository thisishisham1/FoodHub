package iti.example.foodhub.presentation.main.fav

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.presentation.model.MealUiModel

class FavouriteItemsAdapter(private val favouriteItems:List<FavoriteItem>,
    private val onItemClickListener: (FavoriteItem) -> Unit):
RecyclerView.Adapter<FavouriteItemsAdapter.ViewHolder>()
{
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val itemCardView: CardView = itemView.findViewById(R.id.item_card)
        private val favoriteCardView: CardView = itemView.findViewById(R.id.favoriteCardView)

        fun bind(item:FavoriteItem,onItemClickListener:(FavoriteItem)->Unit)
        {
            itemNameTextView.text=item.name


            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(itemImageView)

            itemCardView.setOnClickListener { onItemClickListener(item) }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int =favouriteItems.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favouriteItems[position], onItemClickListener)

    }
}





















































/*
class FavoriteItemsAdapter(private val items: List<Item>,
                           private val onItemClickListener: (Item) -> Unit
) : RecyclerView.Adapter<FavoriteItemsAdapter.FavouriteItemViewHolder>() {

    class FavouriteItemViewHolder(itemView: View, val onClick: (Item) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(item: Item) {
            itemNameTextView.text = item.itemName

            // Assuming youhave the image URL in your Item class
            Glide.with(itemView.context)
                .load( "" ) // Replace with actual image URL
                .into(itemImageView)

            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_card, parent, false
        )
        return FavouriteItemViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}

























*/














   /* private val items:List<Item>,
    private val onClick: (MealUiModel) -> Unit,
    private val onFavoriteClick: (MealUiModel) -> Unit
) :
    androidx.recyclerview.widget.ListAdapter<MealUiModel, ItemsAdapter.ViewHolder>(MealDiffCallback()) {

    class ViewHolder(
        itemView: View,
        val onClick: (MealUiModel) -> Unit,
        val onFavoriteClick: (MealUiModel) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val itemCardView: CardView = itemView.findViewById(R.id.item_card)
        private val favoriteCardView: CardView = itemView.findViewById(R.id.favoriteCardView)
        private var currentItem: MealUiModel? = null

        init {
            itemCardView.setOnClickListener {
                currentItem?.let { onClick(it) }
            }
            favoriteCardView.setOnClickListener {
                currentItem?.let { onFavoriteClick(it) }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        TODO("Not yet implemented")
    }


    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


   */
