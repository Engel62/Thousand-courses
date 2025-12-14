package com.example.thousandcourses

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thousandcourses.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesManager: FavoritesManager
    private var allCourses = listOf<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoritesManager = FavoritesManager(this)
        setupRecyclerView()
        setupViews()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewCourses
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Получаем курсы (можно заменить на MockData.getCourses())
        allCourses = listOf(
            Course(
                id = 1,
                title = "Java-разработчик с нуля",
                text = "Освойте backend-разработку и программирование на Java, фреймворки Spring, Hibernate и создайте первое приложение",
                price = "999 ₽",
                rate = 4.9f,
                startDate = "22 Мая 2024",
                hasLike = favoritesManager.isFavorite(1),
                publishDate = "2024-05-22"
            ),
            Course(
                id = 2,
                title = "3D-дженералист",
                text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать модели, текстуры и анимацию",
                price = "12 000 ₽",
                rate = 3.9f,
                startDate = "10 Сентября 2024",
                hasLike = favoritesManager.isFavorite(2),
                publishDate = "2024-09-10"
            ),
            Course(
                id = 3,
                title = "Android разработка на Kotlin",
                text = "Научись создавать современные Android приложения с использованием Kotlin, Coroutines, Retrofit и Jetpack",
                price = "1 500 ₽",
                rate = 4.7f,
                startDate = "15 Июня 2024",
                hasLike = favoritesManager.isFavorite(3),
                publishDate = "2024-06-15"
            ),
            Course(
                id = 4,
                title = "Data Science с нуля",
                text = "Изучи Python, машинное обучение, анализ данных и визуализацию для работы в области Data Science",
                price = "2 300 ₽",
                rate = 4.5f,
                startDate = "5 Августа 2024",
                hasLike = favoritesManager.isFavorite(4),
                publishDate = "2024-08-05"
            ),
            Course(
                id = 5,
                title = "Веб-разработчик",
                text = "Fullstack разработка на JavaScript, React, Node.js и базы данных",
                price = "1 800 ₽",
                rate = 4.6f,
                startDate = "20 Июля 2024",
                hasLike = favoritesManager.isFavorite(5),
                publishDate = "2024-07-20"
            )
        )

        recyclerView.adapter = CoursesAdapter(allCourses) { course ->
            toggleFavorite(course)
        }
    }

    private fun toggleFavorite(course: Course) {
        if (favoritesManager.isFavorite(course.id)) {
            // Удаляем из избранного
            favoritesManager.removeFavoriteCourse(course.id)
            course.hasLike = false
            Toast.makeText(
                this,
                "Курс \"${course.title}\" удален из избранного",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // Добавляем в избранное
            favoritesManager.saveFavoriteCourse(course)
            course.hasLike = true
            Toast.makeText(
                this,
                "Курс \"${course.title}\" добавлен в избранное",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Обновляем отображение
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupViews() {
        // Кнопка сортировки
        binding.btnSort.setOnClickListener {
            sortCoursesByDate()
        }

        // Поиск и фильтр пока неактивны (хардкод по ТЗ)
        binding.etSearch.isEnabled = false
        binding.ivFilter.isEnabled = false
    }

    private fun sortCoursesByDate() {
        val adapter = recyclerView.adapter as CoursesAdapter
        val sortedCourses = adapter.courses.sortedByDescending { it.publishDate }

        // Обновляем адаптер с отсортированными данными
        recyclerView.adapter = CoursesAdapter(sortedCourses) { course ->
            toggleFavorite(course)
        }

        Toast.makeText(
            this,
            "Отсортировано по дате (новые сверху)",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Уже на главной
                    true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_account -> {
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список при возвращении на экран
        // (на случай, если избранное изменилось в другом месте)
        val adapter = recyclerView.adapter as CoursesAdapter
        val updatedCourses = adapter.courses.map { course ->
            course.copy(hasLike = favoritesManager.isFavorite(course.id))
        }

        recyclerView.adapter = CoursesAdapter(updatedCourses) { course ->
            toggleFavorite(course)
        }
    }
}

// Модель курса (если Course.kt еще не создан)
data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Float,
    val startDate: String,
    var hasLike: Boolean,
    val publishDate: String
)