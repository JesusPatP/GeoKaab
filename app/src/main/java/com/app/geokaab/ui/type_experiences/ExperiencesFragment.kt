package com.app.geokaab.ui.type_experiences

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExperiencesFragment : Fragment() {

    private var _binding: FragmentExperiencesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var firstTypeExperience : String = "Hn3PrplqevVhJfPe4ymq"

    //To Firestore
    val viewModelTypes: TypeExperienceViewModel by viewModels()
    val viewModelExperiences: ExperienceViewModel by viewModels()

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
                Toast.makeText(context, "Hola",Toast.LENGTH_LONG)
                adapterExperiences.filterByTypeCode(item.id.toString() )

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

        oberver()

        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerViewTypes.layoutManager = layoutManager
        binding.recyclerViewTypes.adapter = adapterTypes

        val layoutManagerExperiences = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerViewExperiences.layoutManager = layoutManagerExperiences
        binding.recyclerViewExperiences.adapter = adapterExperiences

        viewModelTypes.getTypeExperiences()
        viewModelExperiences.getExperiences()

        binding.button.setOnClickListener {
            //findNavController().navigate(R.id.action_experiencesFragment_to_experienceDetailActivity)
            viewModelExperiences.getExperiences()
        }

        BottomSheetBehavior.from(binding.designBottomSheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_EXPANDED
        }





    }

    private fun oberver(){
        viewModelTypes.typeExperience.observe(viewLifecycleOwner) { state ->
            when(state){
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
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapterExperiences.createList(state.data.toMutableList(),firstTypeExperience)
                }
            }
        }


    }

}