package com.jdagnogo.myplace.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jdagnogo.myplace.databinding.FragmentHomeBinding
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.ui.adapter.VenueAdapter
import com.jdagnogo.myplace.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: VenueAdapter

    private lateinit var viewModel: MainViewModel
    override fun subscribeViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(MainViewModel::class.java)
        viewModel.venues.observe(this, venueObserver)
    }

    private val venueObserver = Observer<Resource<List<Venue>>> { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.let { adapter.submitList(it) }
            }
            Resource.Status.LOADING -> {
            }
            Resource.Status.ERROR -> {
            }
        }
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
        venue_list.adapter = adapter
        adapter.listener = viewModel
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