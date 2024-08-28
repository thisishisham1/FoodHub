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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}
