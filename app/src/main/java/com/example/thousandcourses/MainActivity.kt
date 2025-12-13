package com.example.thousandcourses

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thousandcourses.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViews()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewCourses
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Временные данные для теста
        val testCourses = listOf(
            Course(1, "Java-разработчик с нуля", "Освойте backend-разработку и программирование на Java, фреймворки Spring, Hibernate и создайте первое приложение", "999 ₽", 4.9f, "22 Мая 2024", false, "2024-05-22"),
            Course(2, "3D-дженералист", "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать модели, текстуры и анимацию", "12 000 ₽", 3.9f, "10 Сентября 2024", true, "2024-09-10"),
            Course(3, "Android разработка на Kotlin", "Научись создавать современные Android приложения с использованием Kotlin, Coroutines, Retrofit и Jetpack", "1 500 ₽", 4.7f, "15 Июня 2024", false, "2024-06-15"),
            Course(4, "Data Science с нуля", "Изучи Python, машинное обучение, анализ данных и визуализацию для работы в области Data Science", "2 300 ₽", 4.5f, "5 Августа 2024", true, "2024-08-05")
        )

        recyclerView.adapter = CoursesAdapter(testCourses) { course ->
            // Обработка клика на избранное
            Toast.makeText(this, "Курс \"${course.title}\" ${if (course.hasLike) "удален из" else "добавлен в"} избранное", Toast.LENGTH_SHORT).show()
            course.hasLike = !course.hasLike
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupViews() {
        // Кнопка сортировки
        binding.btnSort.setOnClickListener {
            Toast.makeText(this, "Сортировка по дате добавления", Toast.LENGTH_SHORT).show()
            // Сортировку реализуем позже
        }

        // Поиск и фильтр пока неактивны (хардкод по ТЗ)
        binding.etSearch.isEnabled = false
        binding.ivFilter.isEnabled = false
    }
}

// Временная модель курса (создайте отдельный файл Course.kt позже)
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