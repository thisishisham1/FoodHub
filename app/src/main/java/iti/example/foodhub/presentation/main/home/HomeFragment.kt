package iti.example.foodhub.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.viewModel.home.HomeViewModel
import iti.example.foodhub.viewModel.home.HomeViewModelFactory


class HomeFragment : Fragment() {
    private val homeRepository: HomeRepository =
        HomeRepository(RemoteDataSourceImpl(RetrofitService.mealsService))

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(homeRepository)
    }

    private val categories =
        listOf(
            "Starter",
            "Breakfast",
            "Beef",
            "Chicken",
            "Dessert",
            "Lamb",
            "Miscellaneous",
            "Pasta",
            "Pork",
            "Seafood",
            "Side",
            "Vegan",
            "Vegetarian",
            "Goat",
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDrawer()
        setupRecyclerView(view)
        setupTabs(view)
        observeViewModel(view)
    }

    private fun setupDrawer() {
        val drawerLayout = view?.findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerButton = view?.findViewById<ImageButton>(R.id.drawer_button)

        drawerButton?.setOnClickListener {
            drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel(view: View) {
        val adapter = ItemsAdapter { item ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.idMeal.toInt())
            view.findNavController().navigate(action)
        }
        view.findViewById<RecyclerView>(R.id.orderRecyclerView).adapter = adapter

        viewModel.meals.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun setupTabs(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        categories.forEach { category ->
            tabLayout.addTab(tabLayout.newTab().setText(category))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.text?.let { viewModel.getMealsByCategory(it.toString()) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.text?.let { viewModel.getMealsByCategory(it.toString()) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
        tabLayout.getTabAt(0)?.select()
    }
}