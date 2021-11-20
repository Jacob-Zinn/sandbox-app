package com.example.sandbox.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sandbox.databinding.FragmentAccountBinding
import com.example.sandbox.models.User
import com.example.sandbox.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment: BaseFragment(),
    View.OnClickListener
{

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    val mainViewModel: MainViewModel by viewModels()


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

        setAccountDataFields(mainViewModel.user)
    }

    private fun setAccountDataFields(user: User) {
        // setting personal info
        binding.accountEmail.text = (user.email)
        binding.firstName.text = (user.firstName)
        binding.lastName.text = (user.lastName)

    }

    override fun onClick(p0: View?) {

    }
}