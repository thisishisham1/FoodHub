package iti.example.foodhub.presentation.main.fav

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.main.details.DetailsActivity
import iti.example.foodhub.presentation.main.home.ItemsAdapter
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.fav.FavouriteViewModel
import iti.example.foodhub.viewModel.fav.FavouriteViewModelFactory

class FavoriteFragment : Fragment() {
    private lateinit var roomRepository: RoomRepository
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: FavouriteViewModel by viewModels {
        FavouriteViewModelFactory(remoteRepository, roomRepository, sharedPrefHelper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        roomRepository =
            RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
        remoteRepository = RemoteRepository(RemoteDataSourceImpl(RetrofitService.mealsService))
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        sharedPrefHelper = SharedPrefHelper(sharedPreferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        observeViewModel(view)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.favrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ItemsAdapter(onFavoriteClick = { mealUiModel ->
            viewModel.favoriteClickedHandle(mealUiModel)
        }, onClick = {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("mealId", it.idMeal)
            startActivity(intent)
            Toast.makeText(requireContext(), "Clicked on ${it.strMeal}", Toast.LENGTH_SHORT).show()
        })
        recyclerView.adapter = adapter
    }

    private fun observeViewModel(view: View) {
        val adapter = view.findViewById<RecyclerView>(R.id.favrecyclerView).adapter as ItemsAdapter
        viewModel.favoriteMeals.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }
}