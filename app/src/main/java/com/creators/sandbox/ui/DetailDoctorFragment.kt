package com.creators.sandbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.creators.sandbox.R
import com.creators.sandbox.databinding.FragmentDetailDoctorBinding
import com.creators.sandbox.databinding.FragmentMainBinding
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailDoctorFragment : BaseFragment(),
    View.OnClickListener {

    // Binding
    private var _binding: FragmentDetailDoctorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


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

        initSearchFiltersFromBundle()
    }

    override fun onResume() {
        super.onResume()

        setLogoVisible(true)
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


    private fun setLogoVisible(isVisible: Boolean) {
        val image: View = requireActivity().findViewById(R.id.logo)
        if (isVisible) {
            image.visibility = View.VISIBLE
        } else {
            image.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!) {

        }
    }


}