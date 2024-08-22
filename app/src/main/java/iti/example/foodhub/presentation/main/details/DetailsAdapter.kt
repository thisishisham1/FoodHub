package iti.example.foodhub.presentation.main.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseDetailsModel
import iti.example.foodhub.databinding.ItemCardBinding

class DetailsAdapter (private var mealList: List<Meals>) :
    RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemCardBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detaDetails = mealList[position]
        holder.binding.itemNameTextView.text=detaDetails.strMeal

        val imageUrl = mealList[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.binding.itemImageView)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun update(newList: List<Meals>) {
        mealList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}