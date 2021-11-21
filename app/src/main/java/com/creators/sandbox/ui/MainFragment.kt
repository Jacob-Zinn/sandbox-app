package com.creators.sandbox.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.creators.sandbox.R
import com.creators.sandbox.databinding.FragmentMainBinding
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment(),
    View.OnClickListener {

    // Binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setOnClickListeners()
        setLogoVisible(true)
    }

    private fun setOnClickListeners() {
        binding.nearBtn.setOnClickListener(this)
        binding.experienceBtn.setOnClickListener(this)
        binding.personableBtn.setOnClickListener(this)
        binding.thoughtfulBtn.setOnClickListener(this)
    }

    override fun onPause() {
        super.onPause()
        setLogoVisible(false)
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

    enum class FilterInteger(val filter: Int) {
        NEAR(1),
        PERSONABLE(2),
        EXPERIENCED(3)
    }

    private fun skipToResults(filterInt: FilterInteger) {
        val bundle = Bundle()
        bundle.putInt("filterInt", filterInt.filter)
        try {
            findNavController().navigate(R.id.action_mainFragment_to_previewDoctorsFragment, bundle)
        } catch (e: NullPointerException) {
            Timber.d( "onItemSelected: Session selected from recyclerview")
        }
    }

    override fun onClick(v: View?) {
        when (v!!) {
            binding.thoughtfulBtn -> {
                findNavController().navigate(R.id.action_mainFragment_to_filterExpertiseFragment)
            }
            binding.experienceBtn -> skipToResults(FilterInteger.EXPERIENCED)
            binding.nearBtn -> skipToResults(FilterInteger.NEAR)
            binding.personableBtn -> skipToResults(FilterInteger.PERSONABLE)
        }
    }


}