package com.app.geokaab.ui.type_experiences

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.app.geokaab.R
import com.app.geokaab.data.model.Contact
import com.app.geokaab.data.model.Experience
import com.app.geokaab.databinding.ActivityExperienceDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class ExperienceDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityExperienceDetailBinding
    private lateinit var mMap: GoogleMap
    private var latitude : Double = 20.5790629
    private  var longitude : Double = -87.1195703
    private var title : String = "Xcaret"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperienceDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialize()

        binding.cardBtnBack.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onMapReady(googleMap:GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val mapLocation = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(mapLocation).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapLocation))
        /*
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(mapLocation,10f),
            3000,null
        )
         */
    }

    fun initialize(){
        var objExperience: Experience? = null

        //Para el mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        objExperience = intent.getSerializableExtra("experience") as? Experience

        objExperience?.let { experience ->
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
            //Varaiables para el mapa
            val latlong =
                experience.location[0].split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            latitude = latlong[0].toDouble()
            longitude = latlong[1].toDouble()
            title = experience.location[1]

            mapFragment?.getMapAsync(this)

            //val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            //mapFragment?.getMapAsync(callback)

        }
        var objContact: Contact? = null

        //Para el mapa
        objContact = intent.getSerializableExtra("contact") as? Contact
        objContact?.let { contact ->
            var name = contact.name + " " + contact.last_name
            binding.name.setText(name.toString())
            binding.locationContact.setText(contact.location[0])
            var lenguagesText = ""
            for (position in contact.languages.indices){
                if (position == 0){
                    lenguagesText = contact.languages[position]
                }else{
                    lenguagesText += "/" + contact.languages[position]
                }
            }
            binding.lenguagesContact.setText(lenguagesText.toString())
            binding.phoneContact.setText(contact.phone_number.toString())
            //
            binding.imageLocationContact.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(contact.location_images[0]).into(binding.imageLocationContact)
            //
            binding.imageContact.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(contact.images[0]).into(binding.imageContact)
        }

    }


}