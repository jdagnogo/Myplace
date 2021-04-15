package com.jdagnogo.myplace.ui.fragment

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.databinding.FragmentHomeBinding
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.ui.VenueListView
import com.jdagnogo.myplace.ui.adapter.VenueAdapter
import com.jdagnogo.myplace.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), VenueListView {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: VenueAdapter

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: MainViewModel

    override fun subscribeViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
                .get(MainViewModel::class.java)
    }

    override fun setSupportInjection(): Fragment {
        return this
    }

    override fun initViewBiding(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        binding.venueList.adapter = adapter
        adapter.listener = viewModel
        viewModel.view = this
        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.searchVenue(binding.searchRepo.text.toString())
                true
            } else {
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.searchVenue(binding.searchRepo.text.toString())
                true
            } else {
                false
            }
        }
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

    override fun onNewData(venues: List<Venue>?) {
        venues?.let {
            binding.progressCircular.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE
            adapter.submitList(it)
        }
    }

    override fun displayLoader() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    override fun displayError(errorMessage: String) {
        binding.progressCircular.visibility = View.GONE
        binding.errorMessage.text = errorMessage
        binding.errorMessage.visibility = View.VISIBLE
    }

    override fun displayNoInternet() {
        binding.progressCircular.visibility = View.GONE
        Snackbar.make(binding.container, R.string.no_internet, Snackbar.LENGTH_SHORT)
            .show()
    }
}