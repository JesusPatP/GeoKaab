package com.app.geokaab.ui.type_experiences

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.geokaab.R
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.databinding.FragmentExperiencesBinding
import com.app.geokaab.util.UiState
import com.app.geokaab.util.hide
import com.app.geokaab.util.show
import com.app.geokaab.util.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperiencesFragment : Fragment() {

    private var MainActivityBottomBar: SupportMapFragment? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private var _binding: FragmentExperiencesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var firstTypeExperience: String = "cW8j8mHtofEvflsOqVM0"

    //To Firestore
    val viewModelTypes: TypeExperienceViewModel by viewModels()
    val viewModelExperiences: ExperienceViewModel by viewModels()
    //lateinit var MainActivityBottomBar: Fragment

    //

    val adapterExperiences by lazy {
        ExperienceListingAdapter(
            onItemClicked = { pos, item ->
                val intent = Intent(activity, ExperienceDetailActivity::class.java).apply {
                    putExtra("experience", item)
                }
                startActivity(intent)
            }
        )
    }

    val adapterTypes by lazy {
        TypeExperienceListingAdapter(
            onItemClicked = { pos, item ->
                Toast.makeText(context, "Hola", Toast.LENGTH_LONG)
                adapterExperiences.filterByTypeCode(item.id.toString())

                //viewModelExperiences.filterExperiences(item.id)
            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext());
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExperiencesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //val appContext = context!!.applicationContext
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Observer
        oberver()

        //Manager to types
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerViewTypes.layoutManager = layoutManager
        binding.recyclerViewTypes.adapter = adapterTypes

        //Manager to experiences
        val layoutManagerExperiences =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerViewExperiences.layoutManager = layoutManagerExperiences
        binding.recyclerViewExperiences.adapter = adapterExperiences

        //Get Types
        viewModelTypes.getTypeExperiences()
        //Get Experiences
        viewModelExperiences.getExperiences()
        //Map Buttom
        binding.buttonMap.setOnClickListener {
            //findNavController().navigate(R.id.action_experiencesFragment_to_experienceDetailActivity)
            //viewModelExperiences.getExperiences()


        }

        //BottomSheet
        BottomSheet()

        //Map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        //


    }

    private fun BottomSheet() {

        bottomSheetBehavior = BottomSheetBehavior.from(binding.designBottomSheet)
        bottomSheetBehavior.apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_EXPANDED
            binding.imgBottonSheet.hide()
            binding.buttonMap.show()


        }
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.buttonMap.hide()
                        binding.imgBottonSheet.show()
                        binding.designBottomSheet.background = resources.getDrawable(R.drawable.round_sheet)
                        //MainActivityBottomBar.view.visibility(view.GONE)
                    }
                    BottomSheetBehavior.STATE_EXPANDED ->{
                        binding.buttonMap.show()
                        binding.imgBottonSheet.hide()
                        binding.designBottomSheet.background = resources.getDrawable(R.color.white)

                    }
                    /*
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(
                        context,
                        "STATE_DRAGGING",
                        Toast.LENGTH_SHORT
                    ).show()
                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(
                        context,
                        "STATE_SETTLING",
                        Toast.LENGTH_SHORT
                    ).show()
                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(
                        context,
                        "STATE_HIDDEN",
                        Toast.LENGTH_SHORT
                    ).show()
                     */
                    //else -> Toast.makeText(context, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

    }

    private fun oberver() {

        viewModelTypes.typeExperience.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    firstTypeExperience = adapterTypes.onCreateList(state.data.toMutableList())
                }
            }
        }

        viewModelExperiences.Experience.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapterExperiences.createList(state.data.toMutableList(), firstTypeExperience)
                }
            }
        }


    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}

