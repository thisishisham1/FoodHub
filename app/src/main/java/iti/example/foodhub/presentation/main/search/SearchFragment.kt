package iti.example.foodhub.presentation.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import iti.example.foodhub.databinding.FragmentSearchBinding
import iti.example.foodhub.presentation.model.MealUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val mealList = mutableListOf<MealUiModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchBar()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(requireContext(), mealList) { meal ->
            // التعامل مع الضغط على العنصر
        }
        binding.idRecyclerViewMeals.adapter = adapter
    }

    private fun setupSearchBar() {
        binding.idSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    searchMeals(query)
                } else {
                    mealList.clear()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchMeals(query: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$query")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        connection.inputStream.bufferedReader().use { it.readText() }
                    } else {
                        null
                    }
                }
                if (response.isNullOrEmpty()) {
                    return@launch
                }
                val jsonObject = JSONObject(response)
                val mealsArray = jsonObject.optJSONArray("meals")
                mealList.clear()
                if (mealsArray != null) {
                    for (i in 0 until mealsArray.length()) {
                        val mealObject = mealsArray.getJSONObject(i)
                        val meal = MealUiModel(
                            mealObject.getString("strMeal"),
                            mealObject.getString("strMealThumb"),
                            mealObject.getString("idMeal")
                        )
                        mealList.add(meal)
                    }
                }
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
            }
        }
    }
}
