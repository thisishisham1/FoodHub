package iti.example.foodhub.presentation.main.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import iti.example.foodhub.R
import iti.example.foodhub.data.remote.retrofit.RetrofitService
import iti.example.foodhub.data.remote.source.RemoteDataSourceImpl
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.viewModel.Details.MealDetailsViewModel
import iti.example.foodhub.viewModel.Details.MealDetailsViewModelFactory

class DetailsFragment : Fragment() {
    private val homeRepository: HomeRepository =
        HomeRepository(RemoteDataSourceImpl(RetrofitService.mealsService))

    private val viewModel: MealDetailsViewModel by viewModels(
        factoryProducer = { MealDetailsViewModelFactory(homeRepository) }
    )

    private lateinit var foodImageView: ImageView
    private lateinit var foodDescription: TextView
    private lateinit var seeMoreTextView: TextView
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var titleTextView: TextView
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DetailsFragment", "onViewCreated called")

        findId(view)


        // Fetch meal details
        val mealsId = arguments?.getString("i") ?: "52772"
        viewModel.getMealDetails(mealsId)

        // Observe ViewModel data
        viewModel.mealDetails.observe(viewLifecycleOwner) { mealDetails ->
            val meal = mealDetails.meals[0]
            Log.d("DetailsFragment", "Meal details observed: $meal")
            Glide.with(this).load(meal.strMealThumb).into(foodImageView)
            titleTextView.text = meal.strMeal
            foodDescription.text = meal.strInstructions
            if (meal.strYoutube.isNotBlank()) {
                val videoId = Uri.parse(meal.strYoutube).getQueryParameter("v")
                lifecycle.addObserver(youtubePlayerView)
                youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        if (videoId != null) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    }
                })
            } else {
                youtubePlayerView.visibility = View.GONE
            }

        }
    }
}



