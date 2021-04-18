package com.jdagnogo.myplace.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

/**
 * This class will reduce the code that we have to generate and focus only on the important one
 */
abstract class BaseFragment : Fragment() {
    /**
     * We are using ViewBinding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        subscribeViewModel()
        observeValues()
        return initViewBiding()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(setSupportInjection())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    /**
     * In this method, we should handle all viewmodel stuff
     * like init
     */
    abstract fun subscribeViewModel()

    /**
     * In this method, we should observe all livedata
     */
    abstract fun observeValues()

    /**
     * This method is needed by Dagger. We have to give the instance of the current fragment
     */
    abstract fun setSupportInjection(): Fragment

    /**
     * In this method, we should init the Viewbinding
     * like
     */
    abstract fun initViewBiding(): View
    /**
     * In this method, we should handle all view stuff
     * like
     */
    abstract fun initViews()

    /**
     * We need the tag in order to  retrieve the fragment from the fragmentManager
     */
    abstract fun getFragmentTag():String
}