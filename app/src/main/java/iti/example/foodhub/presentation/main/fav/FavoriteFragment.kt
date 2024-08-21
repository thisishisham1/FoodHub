package iti.example.foodhub.presentation.main.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R
import iti.example.foodhub.presentation.main.home.Item
import iti.example.foodhub.presentation.main.home.ItemsAdapter

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // Initialize RecyclerView
        val favRecyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerView)

        // Ensure favRecyclerView is not null
        favRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())

            // Sample data
            val favItems = listOf(
                Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
                Item("BBQ Chicken Pizza", "BBQ sauce, Chicken, and Cheese"),
                Item("Veggie Delight", "Bell peppers, Olives, and Onions"),
                Item("Pepperoni Pizza", "Pepperoni, Cheese, and Tomato sauce"),
                Item("Margherita Pizza", "Tomato, Basil, and Mozzarella"),
                Item("Meat Lover's Pizza", "Sausage, Bacon, and Ham")
            )

            // Set up the adapter with the data
            adapter = ItemsAdapter(favItems, isFavoriteRecyclerView = true)
        }

        return view
    }
}
