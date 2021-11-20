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
        binding.searchAdvanced.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v!!) {
            binding.searchAdvanced -> {
                try {
                    findNavController().navigate(R.id.action_feedFragment_to_filterFragment)
                } catch (e: NullPointerException) {
                    Timber.e(e, "navigateToSessionsFragmentFromRecViewSelection: ERROR: Failed to navigate")
                }
            }
        }
    }


}