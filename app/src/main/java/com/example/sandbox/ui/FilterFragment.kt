package com.example.sandbox.ui

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.sandbox.R
import com.example.sandbox.adapters.ListAdapter
import com.example.sandbox.databinding.FragmentFeedBinding
import com.example.sandbox.models.*
import com.example.sandbox.util.*
import com.example.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.DateFormat.*


@AndroidEntryPoint
class FilterFragment: BaseFragment(),
    ListAdapter.Interaction,
    View.OnClickListener {

    // Binding
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var recyclerAdapter: ListAdapter

    //Data passed to sessions fragment
    private lateinit var trackNamePassedToSessionsFragment: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMainFeedRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        subscribeObservers()
        setLogoVisible(true)
    }

    override fun onPause() {
        super.onPause()
        setLogoVisible(false)
        saveLayoutManagerState()
    }

    private fun subscribeObservers() {

        viewModel.list.observe(viewLifecycleOwner) { list ->
            binding.swipeRefresh.isRefreshing = false
            recyclerAdapter.apply {
                submitList(
                    list = list
                )
            }
        }
    }


    private fun saveLayoutManagerState() {
        binding.feedRecyclerview.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    private fun initMainFeedRecyclerView() {

        binding.feedRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@FilterFragment.context)

            recyclerAdapter = ListAdapter(
                this@FilterFragment
            )
        }
    }

    override fun onItemSelected(position: Int, item: ListItem, tag: String) {
        val toast = Toast.makeText(context, item.title, Toast.LENGTH_SHORT)
        toast.show()
    }


    override fun restoreListPosition() {
        //restores list position of main feed rec view
        viewModel.listLayoutManagerState?.let { lmState ->
            _binding?.let {
                // Nessecary safetey call - though I don't know why this is being called post onDestroy()
                it.feedRecyclerview.layoutManager?.onRestoreInstanceState(lmState)
            }
        }
    }

    private fun navigateToSessionsFragmentFromRecViewSelection(arg: String) {
        val bundle = Bundle()
        bundle.putString("arg", arg)
        try {
            // do nothing
//            findNavController().navigate(R.id.action_mainFeed_to_sessionsFragment, bundle)
        } catch (e: NullPointerException) {
            Timber.e(e,"navigateToSessionsFragmentFromRecViewSelection: ERROR: Failed to navigate")
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
        binding.feedRecyclerview.adapter = null
        binding.linkedAthletesRecView.adapter = null

        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!) {
        }
    }


}