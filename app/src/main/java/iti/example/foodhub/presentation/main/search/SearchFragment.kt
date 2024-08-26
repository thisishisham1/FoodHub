package iti.example.foodhub.presentation.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.databinding.FragmentSearchBinding
import iti.example.foodhub.presentation.main.home.ItemsAdapter
import iti.example.foodhub.presentation.model.MealUiModel
import iti.example.foodhub.viewModel.search.SearchViewModel
import iti.example.foodhub.viewModel.search.SearchViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SearchFragment : Fragment() {
    private lateinit var remoteRepository: RemoteRepository
    val viewModel: SearchViewModel by viewModels(
        factoryProducer = { SearchViewModelFactory(remoteRepository) }
    )
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteRepository = RemoteRepository(RemoteDataSourceImpl(RetrofitService.mealsService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ItemsAdapter(
            onClick = { meal ->
                // Handle click
            },
            onFavoriteClick = { meal ->
                // Handle favorite click
            }
        )
        binding.idRecyclerViewMeals.adapter = adapter
    }

    private fun setupSearchBar() {
        binding.idSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    viewModel.getMealBySearch(query)
                } else {
                    adapter.submitList(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.submitList(meals)
        }
    }
}