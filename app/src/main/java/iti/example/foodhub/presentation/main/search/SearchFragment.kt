package iti.example.foodhub.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iti.example.foodhub.databinding.FragmentSearchBinding
import iti.example.foodhub.presentation.model.MealUiModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val mealList = listOf(
            MealUiModel("Chicken Hawaiian", "https://example.com/image1.jpg", "1"),
            MealUiModel("Red N Hot Pizza", "https://example.com/image2.jpg", "2")
        )

        val adapter = SearchAdapter(requireContext(), mealList) { meal ->
        }
        binding.idRecyclerViewMeals.apply {
            this.adapter = adapter
        }
    }
}
