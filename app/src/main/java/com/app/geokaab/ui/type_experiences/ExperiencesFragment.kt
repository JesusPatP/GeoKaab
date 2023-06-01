package com.app.geokaab.ui.type_experiences

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.geokaab.R
import com.app.geokaab.data.model.Contact
import com.app.geokaab.data.model.Location
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
    val viewModelLocation: LocationViewModel by viewModels()
    val viewModelContact: ContactViewModel by viewModels()
    //lateinit var MainActivityBottomBar: Fragment

    //
    private var list: List<Location> = arrayListOf()
    private var latitude : Double = 20.5790629
    private  var longitude : Double = -87.1195703
    private var title : String = "Xcaret"

    private var list_contacts: List<Contact> = arrayListOf()
    val adapterExperiences by lazy {
        ExperienceListingAdapter(
            onItemClicked = { index, item ->
                val intent = Intent(activity, ExperienceDetailActivity::class.java).apply {
                    putExtra("experience", item)
                    putExtra("contact",getContact(item.contacts[0]))
                    //getContact(item.contacts[0])
                }
                startActivity(intent)
            }
        )
    }

    val adapterTypes by lazy {
        TypeExperienceListingAdapter(
            onItemClicked = { pos, item ->
                adapterExperiences.filterByTypeCode(item.id.toString())
                //viewModelExperiences.filterExperiences(item.id)
            }
        )
    }

    fun getContact(id:String): Contact? {
        var item : Contact? = null
        for ((index,element) in list_contacts.withIndex()){
            if (element.id == id){
                item = element
            }
        }
        return item
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
        //val layoutManagerExperiences =
            //LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        //binding.recyclerViewExperiences.layoutManager = layoutManagerExperiences
        binding.recyclerViewExperiences.adapter = adapterExperiences


        //Get Types
        viewModelTypes.getTypeExperiences()
        //Get Locations
        viewModelLocation.getLocations()
        //Get contacts
        viewModelContact.getContacts()



        //Map Buttom
        binding.buttonMap.setOnClickListener {
            //findNavController().navigate(R.id.action_experiencesFragment_to_experienceDetailActivity)
            //viewModelExperiences.getExperiences()
            binding.buttonMap.hide()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        }

        //BottomSheet
        BottomSheet()

        //


    }
    private fun createMap(){
        //Map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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
                        binding.designBottomSheet.background = resources.getDrawable(R.color.bg_sheet)

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
                    //Get Experiences
                    viewModelExperiences.getExperiences()

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

        viewModelLocation.Location.observe(viewLifecycleOwner) { state ->
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
                    getLocations(state.data.toList())
                    createMap()
                    //adapterExperiences.createList(state.data.toMutableList(), firstTypeExperience)
                }
            }
        }

        viewModelContact.Contact.observe(viewLifecycleOwner) { state ->
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
                    list_contacts = state.data.toMutableList()
                    //createMap()
                    //adapterExperiences.createList(state.data.toMutableList(), firstTypeExperience)
                }
            }
        }


    }

    fun getLocations(list: List<Location>){
        this.list = list
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


        /*
        val Naranjal = LatLng(19.5774805,-88.0454902)
        googleMap.addMarker(MarkerOptions().position(Naranjal).title("Naranjal Poniente"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Naranjal))

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(Naranjal,5f),
            3000,null
        )

        if(list.isEmpty()){
            println("######Empty########")

        }else{
            println("######NotEmpty########")
        }

         */


        var coordinates =  LatLng(19.5774805,-88.0454902)
        for (contador in 0..(list.size-1)) {
            //Varaiables para el mapa
            var latlong =
                list.get(contador).coordinates.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            latitude = latlong[0].toDouble()
            longitude = latlong[1].toDouble()
            title = list.get(contador).location

            coordinates = LatLng(latitude,longitude)

            googleMap.addMarker(MarkerOptions().position(coordinates).title(title))
        }


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates))


        // googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        //googleMap.addMarker(MarkerOptions().position(chichen).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Museo_Maya))


    }

}

