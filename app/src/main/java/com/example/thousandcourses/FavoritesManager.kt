package com.example.thousandcourses

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()


    fun saveFavoriteCourse(course: Course) {
        val courseJson = gson.toJson(course)
        prefs.edit().putString("course_${course.id}", courseJson).apply()
    }

    fun removeFavoriteCourse(courseId: Int) {
        prefs.edit().remove("course_$courseId").apply()
    }

    fun isFavorite(courseId: Int): Boolean {
        return prefs.contains("course_$courseId")
    }

    fun getFavoriteCourses(): List<Course> {
        val allEntries = prefs.all
        val favoriteCourses = mutableListOf<Course>()

        for ((key, value) in allEntries) {
            if (key.startsWith("course_") && value is String) {
                try {
                    val course = gson.fromJson(value, Course::class.java)
                    favoriteCourses.add(course)
                } catch (e: Exception) {
                }
            }
        }

        return favoriteCourses
    }


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

    fun clearAllFavorites() {
        prefs.edit().clear().apply()
    }
}