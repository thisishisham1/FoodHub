package iti.example.foodhub.presentation.main.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.viewModel.fav.FavouriteViewModel

class FavoriteFragment : Fragment() {

   // private lateinit var favoriteItemsAdapter: FavoriteItemsAdapter
    private lateinit var viewModel:FavouriteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setupRecyclerView(view)
    }
}


 /*   private fun setupRecyclerView(view: View) {
        favoriteItemsAdapter = FavoriteItemsAdapter(favoriteItems) { favoriteItem ->
            // Handle item click here, for example, show a Toast
            Toast.makeText(requireContext(), "Clicked on ${favoriteItem.name}", Toast.LENGTH_SHORT).show()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = favoriteItemsAdapter
    }
}
*/






















/*  private val favoriteItems = listOf(
        FavoriteItem(
            id = 1,
            name = "Spaghetti Bolognese",
            imageUrl = "https://www.example.com/images/spaghetti.jpg"
        ),
        FavoriteItem(
            id = 2,
            name = "Chicken Alfredo",
            imageUrl = "https://www.example.com/images/chicken_alfredo.jpg"
        ),
        FavoriteItem(
            id = 3,
            name = "Beef Tacos",
            imageUrl = "https://www.example.com/images/beef_tacos.jpg"
        ),
        FavoriteItem(
            id = 4,
            name = "Caesar Salad",
            imageUrl = "https://www.example.com/images/caesar_salad.jpg"
        ),
        FavoriteItem(
            id = 5,
            name = "Margarita Pizza",
            imageUrl = "https://www.example.com/images/margarita_pizza.jpg"
        )
    )
*/