package com.example.thousandcourses

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.thousandcourses.databinding.ItemCourseBinding

class CoursesAdapter(
    val courses: List<Course>,
    private val onFavoriteClick: (Course) -> Unit
) : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.apply {

                tvTitle.text = course.title

                tvDescription.text = if (course.text.lines().size > 2) {
                    course.text.lines().take(2).joinToString("\n") + "..."
                } else {
                    course.text
                }

                tvPrice.text = course.price
                tvRating.text = course.rate.toString()
                tvDate.text = course.publishDate


                ivFavorite.isSelected = course.hasLike
                ivFavorite.setImageResource(
                    if (course.hasLike) android.R.drawable.btn_star_big_on
                    else android.R.drawable.btn_star_big_off
                )
                ivFavorite.setColorFilter(
                    if (course.hasLike) android.graphics.Color.parseColor("#4CAF50") // Зеленый
                    else android.graphics.Color.GRAY
                )


                ivFavorite.setOnClickListener {
                    onFavoriteClick(course)
                }

                btnDetails.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Детали курса: ${course.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                root.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Выбран курс: ${course.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}