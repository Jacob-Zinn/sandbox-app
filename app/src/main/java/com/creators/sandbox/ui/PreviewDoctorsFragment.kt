package com.creators.sandbox.ui

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.creators.sandbox.R
import com.creators.sandbox.adapters.PreviewDoctorListAdapter
import com.creators.sandbox.databinding.FragmentPreviewDoctorsBinding
import com.creators.sandbox.models.*
import com.creators.sandbox.util.*
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.DateFormat.*


@AndroidEntryPoint
class PreviewDoctorsFragment: BaseFragment(),
    PreviewDoctorListAdapter.Interaction,
    View.OnClickListener {

    // Binding
    private var _binding: FragmentPreviewDoctorsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var recyclerAdapter: PreviewDoctorListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPreviewDoctorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecView()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFilteredDoctors()
        subscribeObservers()
        setLogoVisible(true)
    }

    override fun onPause() {
        super.onPause()
        setLogoVisible(false)
        saveLayoutManagerState()
    }

    private fun subscribeObservers() {
        viewModel.list.observe(viewLifecycleOwner, { doctors ->
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

    private fun initRecView() {

        binding.recView.apply {
            layoutManager = LinearLayoutManager( this@PreviewDoctorsFragment.context)

            recyclerAdapter = PreviewDoctorListAdapter(
                this@PreviewDoctorsFragment
            )
        }
    }

    override fun onItemSelected(position: Int, item: Doctor, tag: String) {
        // rec view selection
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        try {
            findNavController().navigate(R.id.action_previewDoctorsFragment_to_detailDoctorFragment, bundle)
        } catch (e: NullPointerException) {
            Timber.e(e, "onItemSelected: Navigate to sessionFragment by athlete selection")
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

    override fun onClick(v: View?) {
        when (v!!) {
        }
    }


}