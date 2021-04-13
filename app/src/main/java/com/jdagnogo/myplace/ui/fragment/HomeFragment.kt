package com.jdagnogo.myplace.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jdagnogo.myplace.databinding.FragmentHomeBinding
import com.jdagnogo.myplace.viewmodel.MainViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    override fun subscribeViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun setSupportInjection(): Fragment {
        return this
    }

    override fun initDataBiding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        return FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }.root
    }

    override fun initViews() {

    }

    override fun getFragmentTag(): String {
        return TAG
    }

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }
        const val TAG = "HomeFragment"
    }
}