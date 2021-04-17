package com.jdagnogo.myplace.ui.fragment

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jdagnogo.myplace.databinding.FragmentHomeBinding
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.ui.MainActivity
import com.jdagnogo.myplace.ui.adapter.VenueAdapter
import com.jdagnogo.myplace.ui.adapter.VenueListener
import com.jdagnogo.myplace.viewmodel.MainViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment() , VenueListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: VenueAdapter

    private lateinit var binding: FragmentHomeBinding

    @VisibleForTesting lateinit var viewModel: MainViewModel

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
        adapter.listener = this
        viewModel.currentResult.observe(this, venuesObserver)
        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.spinner.observe(this, Observer {
            binding.errorView.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.snackbar.observe(viewLifecycleOwner, Observer{ text ->
            text?.let {
                Snackbar.make(binding.container, text, Snackbar.LENGTH_SHORT)
                    .show()
                viewModel.onSnackbarShown()
            }
        })

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

    private val errorObserver = Observer<Int> {
        with(binding.errorView) {
            if (it != 0) {
                errorMessage.text = getString(it)
                errorMessage.visibility = View.VISIBLE
            }
        }
    }

    private val venuesObserver = Observer<List<Venue>?> {
        if (it?.isEmpty() == true){
            binding.venueList.visibility = View.GONE
        }else{
            binding.errorView.errorMessage.visibility = View.GONE
            binding.venueList.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onClick(venue: Venue) {
        viewModel.currentVenueId = venue.id
        (requireActivity() as MainActivity).onNavigationToFragment(VenueDetailsFragment.TAG)
    }

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = bundleOf()
        }

        const val TAG = "HomeFragment"
    }
}