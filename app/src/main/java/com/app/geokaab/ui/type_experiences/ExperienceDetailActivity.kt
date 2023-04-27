package com.app.geokaab.ui.type_experiences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.app.geokaab.R
import com.app.geokaab.data.model.Experience
import com.app.geokaab.databinding.ActivityMainBinding
import com.app.geokaab.databinding.ActivityExperienceDetailBinding
import com.app.geokaab.util.UiState
import com.app.geokaab.util.hide
import com.app.geokaab.util.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ExperienceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExperienceDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperienceDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var objNote: Experience? = null

        objNote = intent.getSerializableExtra("experience") as? Experience

        objNote?.let { experience ->
            //
            binding.image.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(experience.images[0]).into(binding.image)
            //
            binding.title.setText(experience.title)
            //
            var locationText = ""
            for (position in experience.location.indices){
                if (position != 0){
                    locationText += experience.location[position] + "/ "
                }
            }
            binding.location.setText(locationText)
            //
            var offersText = ""
            for (position in experience.offers.indices){
                if (position == 0){
                    offersText = "✔ " + experience.offers[position]
                }else{
                    offersText += "\n" + "✔ " + experience.offers[position]
                }
            }
            binding.offers.setText(offersText)
            //
            binding.description.setText(experience.description)
            //
            var availabilityText = ""
            for (position in experience.availability.indices){
                availabilityText += experience.availability[position] + "/ "
            }
            binding.availability.setText(availabilityText)
            //
            var activitiesText = ""
            for (position in experience.activities.indices){
                if (position == 0){
                    activitiesText = "● " + experience.activities[position]
                }else{
                    activitiesText += "\n" + "● " + experience.activities[position]
                }
            }
            binding.activities.setText(activitiesText)
            //
            binding.capacity.setText(experience.capacity.toString())
            //
            binding.observations.setText(experience.observations)
            //
            binding.map.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(experience.images[0]).into(binding.map)
        }


    }
}