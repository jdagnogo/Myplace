package com.jdagnogo.myplace.ui.fragment

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jdagnogo.myplace.databinding.FragmentVenueDetailsBinding
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
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

    override fun observeValues() {
        viewModel.currentVenueDetails.observe(this, venueDetailsObserver)
        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.snackbar.observe(viewLifecycleOwner, Observer{ text ->
            text?.let {
                Snackbar.make(binding.container, text, Snackbar.LENGTH_SHORT)
                        .show()
                viewModel.onSnackbarShown()
            }
        })
    }

    override fun setSupportInjection(): Fragment {
        return this
    }

    override fun initViewBiding(): View {
        binding = FragmentVenueDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        viewModel.getVenueDetails()
    }

    private val venueDetailsObserver = Observer<VenueDetails?> {
        binding.venueTitle.text = it?.name
        binding.venueDescription.text = it?.description
        binding.venueFacebook.text = it?.facebook
        binding.venueLocation.text = it?.location
        binding.venuePhone.text = it?.phone
        binding.venuePhoto.text = it?.photo
        binding.venueTwitter.text = it?.twitter
        binding.venueRating.text = it?.rating.toString()
    }
    /**
     * This listener will be trigger everytime an error message should be display
     * use  _errorMessage.postValue(msg) to trigger it
     */
    private val errorObserver = Observer<Int> {
        with(binding.errorView) {
            if (it !=0) {
                errorMessage.text = getString(it)
                errorMessage.visibility = View.VISIBLE
            }
        }
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