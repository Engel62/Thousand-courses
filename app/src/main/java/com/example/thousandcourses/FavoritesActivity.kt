package com.example.thousandcourses

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thousandcourses.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var favoritesManager: FavoritesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoritesManager = FavoritesManager(this)
        setupViews()
        loadFavorites()
    }

    private fun setupViews() {
        recyclerView = binding.recyclerViewFavorites
        tvEmpty = binding.tvEmpty
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Настраиваем кнопку "назад" в ActionBar (если он есть)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Избранное")
    }

    private fun loadFavorites() {
        val favoriteCourses = favoritesManager.getFavoriteCourses()

        if (favoriteCourses.isEmpty()) {
            // Показываем сообщение "пусто"
            tvEmpty.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
        } else {
            // Показываем список избранных курсов
            tvEmpty.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE

            recyclerView.adapter = CoursesAdapter(favoriteCourses) { course ->
                // Удаляем курс из избранного
                removeFromFavorites(course)
            }
        }
    }

    private fun removeFromFavorites(course: Course) {
        favoritesManager.removeFavoriteCourse(course.id)


        Toast.makeText(
            this,
            "Курс \"${course.title}\" удален из избранного",
            Toast.LENGTH_SHORT
        ).show()


        loadFavorites()
    }

    override fun onResume() {
        super.onResume()

        loadFavorites()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}