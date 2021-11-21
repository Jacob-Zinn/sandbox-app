package com.creators.sandbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.creators.sandbox.R
import com.creators.sandbox.adapters.FilterListAdapter
import com.creators.sandbox.adapters.NurseReviewListAdapter
import com.creators.sandbox.databinding.FragmentDetailDoctorBinding
import com.creators.sandbox.models.Doctor
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDoctorFragment : BaseFragment(),
    NurseReviewListAdapter.Interaction,
    View.OnClickListener {

    // Binding
    private var _binding: FragmentDetailDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerAdapter: NurseReviewListAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailDoctorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        initRecAdapter()
        subscribeObservers()
        setLogoVisible(true)
        initSearchFiltersFromBundle()

        binding.star.setOnClickListener(this)
    }

    override fun onPause() {
        super.onPause()
        setLogoVisible(false)
    }


    private fun initSearchFiltersFromBundle() {
        //Receives arguments from Main Feed fragment mainFeedRecView
        arguments?.let { args ->
            viewModel.searchDoctorById(args.getInt("id"))
            // If new filters were set, those filters should be persisted when user navigates back.
            // This data is persisted in viewModel anyways
            arguments = null
        }
    }

    private fun subscribeObservers() {
        viewModel.viewingDoctor.observe(viewLifecycleOwner, { doctor ->

            initAllFields(doctor)

            val reviewList: MutableList<String>  = mutableListOf()
            reviewList.add(doctor.doctor_desciption_1)
            reviewList.add(doctor.doctor_desciption_2)
            reviewList.add(doctor.doctor_desciption_3)

            recyclerAdapter.apply {
                submitList(
                    list = reviewList,
                )
            }
        })
    }

    private fun initAllFields(doctor: Doctor) {

        binding.nameFirst.text = doctor.first_name
        binding.nameLast.text = doctor.last_name
        binding.ratingBedsideManners.setPercentage(doctor.bedside_manners/100f)
        binding.industryExpertise.text = "${doctor.expertise_1}, ${doctor.expertise_2}, and ${doctor.expertise_3}"

    }

    private fun saveLayoutManagerState() {
        binding.recView.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    private fun initRecAdapter() {

        binding.recView.apply {
            layoutManager =
                StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.GAP_HANDLING_NONE)

            recyclerAdapter = NurseReviewListAdapter(
                this@DetailDoctorFragment
            )
            adapter = recyclerAdapter
        }
    }


    private fun setLogoVisible(isVisible: Boolean) {
        val image: View = requireActivity().findViewById(R.id.logo)
        if (isVisible) {
            image.visibility = View.VISIBLE
        } else {
            image.visibility = View.GONE
        }
    }

    private fun toggleStarType() {
        binding.star.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_24, null))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!) {
            binding.star -> {
                viewModel.favoriteDoctorsList.add(viewModel.viewingDoctor.value!!)
                toggleStarType()
            }

        }
    }

    override fun onItemSelected(position: Int, item: String) {

    }


    override fun restoreListPosition() {
        //restores list position of main feed rec view
        viewModel.listLayoutManagerState?.let { lmState ->
            _binding?.let {
                // Nessecary safetey call - though I don't know why this is being called post onDestroy()
                it.recView.layoutManager?.onRestoreInstanceState(lmState)
            }
        }
    }


}