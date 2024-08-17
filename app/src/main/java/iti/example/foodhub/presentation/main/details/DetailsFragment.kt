package iti.example.foodhub.presentation.main.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R

class DetailsFragment : Fragment() {

    private lateinit var backArrow: ImageView
    private lateinit var favoriteButton: ImageView
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        // Initialize views using the inflated view
        backArrow = view.findViewById(R.id.back_arrow)
        favoriteButton = view.findViewById(R.id.favorite_button)

        // Handle back arrow click
        backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        // Handle favorite button click
        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            val icon = if (isFavorite) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
            favoriteButton.setImageResource(icon)
            // Save the favorite status to your database here
        }

        return view
    }
}
