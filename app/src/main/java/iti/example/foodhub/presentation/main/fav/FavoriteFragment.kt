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

        // Initialize RecyclerView inside onCreateView where 'view' is accessible
        val recyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data
        val items = listOf(
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Item 1", "$10.00"),
            Item("Item 2", "$20.00"),
            Item("Item 3", "$30.00")
        )

        // Set up the adapter with the data
        val adapter = ItemsAdapter(items)
        recyclerView.adapter = adapter

        return view
    }
}
