package com.jdagnogo.myplace.ui.fragment

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jdagnogo.myplace.databinding.FragmentHomeBinding
import com.jdagnogo.myplace.databinding.FragmentVenueDetailsBinding
import com.jdagnogo.myplace.viewmodel.MainViewModel
import javax.inject.Inject

class VenueDetailsFragment : BaseFragment(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentVenueDetailsBinding

    private lateinit var viewModel: MainViewModel


    override fun subscribeViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun setSupportInjection(): Fragment {
        return this
    }

    override fun initViewBiding(): View {
        binding = FragmentVenueDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {

    }

    override fun getFragmentTag(): String {
        return TAG
    }

    companion object {
        fun newInstance() = VenueDetailsFragment().apply {
            arguments = bundleOf()
        }
        const val TAG = "VenueDetailsFragment"
    }

}