package com.creators.sandbox.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.creators.sandbox.adapters.PreviewDoctorListAdapter
import com.creators.sandbox.databinding.FragmentAccountBinding
import com.creators.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountFragment: BaseFragment(),
    View.OnClickListener
{

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by viewModels()

    private lateinit var recViewAdapter: PreviewDoctorListAdapter






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onResume() {
        super.onResume()
        if (viewModel.favoriteDoctorsList.isEmpty()) {
            binding.noResults.visibility = View.VISIBLE
        } else {
            binding.noResults.visibility = View.GONE
            recViewAdapter.apply {
                submitList(
                    list = viewModel.favoriteDoctorsList
                )
            }
        }
    }

    private fun initRecView() {

        binding.recView.apply {
            layoutManager = LinearLayoutManager(this@AccountFragment.context)

            recViewAdapter = PreviewDoctorListAdapter()

            adapter = recViewAdapter
        }
    }

    override fun onClick(p0: View?) {

    }
}