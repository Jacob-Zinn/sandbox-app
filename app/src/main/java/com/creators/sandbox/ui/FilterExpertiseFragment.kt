package com.creators.sandbox.ui

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.creators.sandbox.R
import com.creators.sandbox.adapters.FilterListAdapter
import com.creators.sandbox.databinding.FragmentFilterBinding
import com.creators.sandbox.models.*
import com.creators.sandbox.util.*
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.DateFormat.*


@AndroidEntryPoint
class FilterExpertiseFragment : BaseFragment(),
    FilterListAdapter.Interaction,
    View.OnClickListener {

    // Binding
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var recyclerAdapter: FilterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMainFeedRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getExpertiseFilters()
        viewModel.queries.clear()
        setLogoVisible(true)
        subscribeObservers()
    }

    override fun onPause() {
        super.onPause()
        setLogoVisible(false)
        saveLayoutManagerState()
    }


    private fun subscribeObservers() {
        viewModel.expertiseList.observe(viewLifecycleOwner, { doctors ->
            recyclerAdapter.apply {
                submitList(
                    list = doctors.toMutableList(),
                )
            }
        })
    }


    private fun saveLayoutManagerState() {
        binding.recView.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    private fun initMainFeedRecyclerView() {

        binding.recView.apply {
            layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.GAP_HANDLING_NONE)

            recyclerAdapter = FilterListAdapter(
                this@FilterExpertiseFragment
            )
        }
    }

    override fun onItemSelected(position: Int, item: String, tag: String) {
        when {
            viewModel.queries.expertise1 != null -> viewModel.queries.expertise1 = item
            viewModel.queries.expertise2 != null -> viewModel.queries.expertise2 = item
            viewModel.queries.expertise3 != null -> {
                viewModel.queries.expertise3 = item
                navigateToNextFragment()
            }
        }
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
        // clear references (can leak memory)
        binding.recView.adapter = null

        _binding = null
    }

    private fun navigateToNextFragment() {
        try {
            findNavController().navigate(R.id.action_filterExpertiseFragment_to_filterQualitiesFragment)
        } catch (e: NullPointerException) {
            Timber.e(
                e,
                "navigateToSessionsFragmentFromRecViewSelection: ERROR: Failed to navigate"
            )
        }
    }

    override fun onClick(v: View?) {
        when (v!!) {
            binding.nextBtn -> navigateToNextFragment()
        }
    }


}