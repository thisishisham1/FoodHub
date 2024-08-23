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
import iti.example.foodhub.data.repository.DetailsRepository
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
    private lateinit var _binding: FragmentDetailsBinding
    // private val binding get() = _binding

    private lateinit var backArrow: ImageView
    private lateinit var favoriteButton: ImageView
    private lateinit var foodImageView: ImageView
    lateinit var foodDescription: TextView
    lateinit var seeMoreTextView: TextView
    lateinit var mealVideoView: VideoView
    lateinit var mealVideoController: MediaController
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodImageView = view.findViewById(R.id.food_image)
        foodDescription = view.findViewById(R.id.food_description)
        seeMoreTextView = view.findViewById(R.id.seeMoreTextView)
        mealVideoView = view.findViewById(R.id.mealVideoView)
        mealVideoController = view.findViewById(R.id.mediaController)

        // handle see more
        var isExpanded = false
        seeMoreTextView.setOnClickListener {
            isExpanded = !isExpanded
            foodDescription.maxLines = if (isExpanded)
                Int.MAX_VALUE else 3
            seeMoreTextView.text = if (isExpanded) getString(R.string.see_more)
            else getString(R.string.see_less)

            //get id
            _binding = FragmentDetailsBinding.bind(view)
            val mealsId = arguments?.getString("id") ?: ""
            viewModel.getMealDetails(mealsId)

            viewModel.mealDetails.observe(viewLifecycleOwner) { mealsId ->
                Glide.with(this).load(mealsId.strMealImages).into(foodImageView)


                foodDescription.text = mealsId.strDescription

                // handle video
                if (mealsId.strYoutube.isNotBlank()) {
                    val videoUri = Uri.parse(mealsId.strYoutube)
                    mealVideoView.setVideoURI(videoUri)
                    mealVideoController.setAnchorView(mealVideoView)

                } else {
                    mealVideoView.visibility = View.GONE
                }


            }


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

        }


    }

}


//val mealsRepositry=RemoteDataSourceImpl(RetrofitService.mealsService)
// val viewModelFactory=MealDetailsViewModelFactory(mealsRepositry)
// viewModel=ViewModelProvider(this,MealDetailsViewModelFactory).get(MealDetailsViewModel::class.java)
