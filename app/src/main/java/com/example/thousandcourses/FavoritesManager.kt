package com.example.thousandcourses

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Сохраняем весь курс как JSON
    fun saveFavoriteCourse(course: Course) {
        val courseJson = gson.toJson(course)
        prefs.edit().putString("course_${course.id}", courseJson).apply()
    }

    // Удаляем курс
    fun removeFavoriteCourse(courseId: Int) {
        prefs.edit().remove("course_$courseId").apply()
    }

    // Проверяем, есть ли в избранном
    fun isFavorite(courseId: Int): Boolean {
        return prefs.contains("course_$courseId")
    }

    // Получаем все избранные курсы
    fun getFavoriteCourses(): List<Course> {
        val allEntries = prefs.all
        val favoriteCourses = mutableListOf<Course>()

        for ((key, value) in allEntries) {
            if (key.startsWith("course_") && value is String) {
                try {
                    val course = gson.fromJson(value, Course::class.java)
                    favoriteCourses.add(course)
                } catch (e: Exception) {
                    // Если не удалось распарсить, пропускаем
                }
            }
        }

        return favoriteCourses
    }

    // Получаем ID избранных курсов
    fun getFavoriteIds(): List<Int> {
        return prefs.all.keys
            .filter { it.startsWith("course_") }
            .mapNotNull {
                try {
                    it.removePrefix("course_").toInt()
                } catch (e: Exception) {
                    null
                }
            }
    }

    // Очищаем все избранное
    fun clearAllFavorites() {
        prefs.edit().clear().apply()
    }
}