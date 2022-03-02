package com.example.sharet.sharedresourcedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sharet.R
import com.example.sharet.database.SharedResourceDatabase
import com.example.sharet.databinding.FragmentSharedResourceDetailBinding

class SharedResourceDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSharedResourceDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shared_resource_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SharedResourceDetailFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = SharedResourceDatabase.getInstance(application).sharedResourceDatabaseDao
        val viewModelFactory = SharedResourceDetailViewModelFactory(arguments.sharedResourceKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sharedResourceDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SharedResourceDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.sharedResourceDetailViewModel = sharedResourceDetailViewModel

        // binding.setLifecycleOwner(this)
        binding.lifecycleOwner = this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sharedResourceDetailViewModel.navigateToSharedResource.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SharedResourceDetailFragmentDirections.actionSharedResourceDetailFragmentToSharedResourceFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sharedResourceDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
