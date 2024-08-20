package iti.example.foodhub.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iti.example.foodhub.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerButton = view.findViewById<ImageButton>(R.id.drawer_button)

        drawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val items = listOf(
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Chicken Hawaiian", "Chicken, Cheese and pineapple"),
            Item("Item 3", "$30.00"),
            Item("Item 1", "$10.00"),
            Item("Item 2", "$20.00"),
            Item("Item 3", "$30.00")
        )

        val adapter = ItemsAdapter(items)
        recyclerView.adapter = adapter
    }
}