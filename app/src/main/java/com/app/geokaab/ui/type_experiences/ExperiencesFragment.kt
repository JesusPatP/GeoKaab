package com.app.geokaab.ui.type_experiences

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.geokaab.R
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.databinding.FragmentExperiencesBinding
import com.app.geokaab.di.FirebaseModule
import com.app.geokaab.ui.home.HomeViewModel
import com.app.geokaab.util.UiState
import com.app.geokaab.util.hide
import com.app.geokaab.util.show
import com.app.geokaab.util.toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExperiencesFragment : Fragment() {

    private var _binding: FragmentExperiencesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //To Firestore
    val viewModelTypes: TypeExperienceViewModel by viewModels()
    val viewModelExperiences: ExperienceViewModel by viewModels()


    val adapterTypes by lazy {
        TypeExperienceListingAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.navigation_home,Bundle().apply {
                    putParcelable("type_experience",item)
                })
            }
        )
    }

    val adapterExperiences by lazy {
        ExperienceListingAdapter(
            onItemClicked = { pos, item ->
                /*
                findNavController().navigate(R.id.action_experiencesFragment_to_experienceDetailActivity,Bundle().apply {
                    putParcelable("experiences",item)
                })
                */

                val intent = Intent(activity, ExperienceDetailActivity::class.java).apply {
                    putExtra("experiences", item)
                }
                startActivity(intent)
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
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentExperiencesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.text.observe(viewLifecycleOwner) {
        }
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

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        viewModelTypes.getTypeExperiences()
        viewModelExperiences.getExperiences()

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
                    adapterTypes.updateList(state.data.toMutableList())
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
                    adapterExperiences.updateList(state.data.toMutableList())
                }
            }
        }
    }

}