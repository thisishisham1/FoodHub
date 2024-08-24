package iti.example.foodhub.presentation.main.details

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.databinding.FragmentDetailsBinding
import iti.example.foodhub.viewModel.Details.MealDetailsViewModel
import iti.example.foodhub.viewModel.Details.MealDetailsViewModelFactory

class DetailsFragment : Fragment() {
    private val homeRepository: HomeRepository =
        HomeRepository(RemoteDataSourceImpl(RetrofitService.mealsService))

    private val viewModel: MealDetailsViewModel by viewModels(
        factoryProducer = { MealDetailsViewModelFactory(homeRepository) }
    )


    private lateinit var foodImageView: ImageView
    lateinit var foodDescription: TextView
    lateinit var mealVideoView: VideoView
    lateinit var seeMoreTextView: TextView
    lateinit var mealVideoController: MediaController
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val mealsId = arguments?.getString("i") ?: ""
       // viewModel.getMealDetails(mealsId)

       // findId(view)
       // HandleBackDrawer()
       // HandleFavouriteButton()


        //viewModel.mealDetails.observe(viewLifecycleOwner) { mealsId ->
            //Glide.with(this).load(mealsId.meals[0].strMealThumb).into(foodImageView)


            /* foodDescription.text = mealsId.strInstructions

            // handle video
            if (mealsId.strYoutube.isNotBlank()) {
                val videoUri = Uri.parse(mealsId.strYoutube)
                mealVideoView.setVideoURI(videoUri)
                mealVideoController.setAnchorView(mealVideoView)

            } else {
                mealVideoView.visibility = View.GONE
            }





*/
        }
    }
/*
    private fun findId(view: View) {
        foodImageView = view.findViewById(R.id.food_image)
        foodDescription = view.findViewById(R.id.food_description)
        seeMoreTextView = view.findViewById(R.id.seeMoreTextView)
        mealVideoView = view.findViewById(R.id.mealVideoView)
        mealVideoController = view.findViewById(R.id.mediaController)

    }

    private fun HandleBackDrawer() {
        val backArrow = view?.findViewById<ImageView>(R.id.back_arrow)
        backArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun HandleFavouriteButton() {
        val favoriteButton = view?.findViewById<ImageView>(R.id.favorite_button)
        favoriteButton?.setOnClickListener {
            isFavorite = !isFavorite
            val icon = if (isFavorite) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
            favoriteButton.setImageResource(icon)
            // Save the favorite status to your database here
        }

    }
}

*/



// handle see more
/*var isExpanded = false
seeMoreTextView.setOnClickListener {
    isExpanded = !isExpanded
    foodDescription.maxLines = if (isExpanded)
        Int.MAX_VALUE else 3
    seeMoreTextView.text = if (isExpanded) getString(R.string.see_more)
    else getString(R.string.see_less)*/

//get id
// _binding = FragmentDetailsBinding.bind(view)