package iti.example.foodhub.presentation.main.fav


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import iti.example.foodhub.R
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.presentation.main.home.ItemsAdapter
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.Details.MealDetailsViewModel
import iti.example.foodhub.viewModel.Details.MealDetailsViewModelFactory
import iti.example.foodhub.viewModel.fav.FavouriteViewModel

import iti.example.foodhub.viewModel.sharedViewModel.SharedViewModel

class FavouriteFragment : Fragment() {

    private val favoriteItems = listOf(
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

    private lateinit var roomRepository: RoomRepository
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var sharedPreferences: SharedPrefHelper
    private lateinit var adapter :ItemsAdapter
    private  val viewModel:FavouriteViewModel by viewModels {
        FavouriteViewModel.FavouriteViewModelFactory(
            remoteRepository,
            roomRepository,
            sharedPrefHelper
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        roomRepository =
            RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
        remoteRepository = RemoteRepository(RemoteDataSourceImpl(RetrofitService.mealsService))
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
      //  sharedPreferences = context.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
      //  sharedPrefHelper = SharedPrefHelper(sharedPreferences)
    }
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)


    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

    }



    private fun observeViewModel(view: View) {
        val adapter = ItemsAdapter(onFavoriteClick = { mealUiModel ->
        }, onClick = {

        })
        view.findViewById<RecyclerView>(R.id.favrecyclerView).adapter = adapter

        viewModel.favoriteMeals.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }


    }
}





































































/*
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