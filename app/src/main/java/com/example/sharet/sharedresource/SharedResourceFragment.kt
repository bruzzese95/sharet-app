package com.example.sharet.sharedresource

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sharet.R
import com.example.sharet.database.SharedResourceDatabase
import com.example.sharet.databinding.FragmentSharedResourceBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SharedResourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharedResourceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentSharedResourceBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shared_resource,
            container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SharedResourceDatabase.getInstance(application).sharedResourceDatabaseDao

        val viewModelFactory = SharedResourceViewModelFactory(dataSource, application)

        val sharedResourceViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SharedResourceViewModel::class.java)

        binding.sharedResourceViewModel = sharedResourceViewModel

        binding.lifecycleOwner = this

        sharedResourceViewModel.navigateToCustomDialog.observe(viewLifecycleOwner, Observer { resource ->
            resource?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit add again
                // after using the back button, not if we hit stop and choose a name.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                this.findNavController().navigate(
                    SharedResourceFragmentDirections.actionSharedResourceFragmentToCustomDialogFragment()
                )
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sharedResourceViewModel.doneNavigating()
            }
        })


        sharedResourceViewModel.navigateToSharedResourceDetail.observe(viewLifecycleOwner, Observer { resource ->
            resource?.let {
                this.findNavController().navigate(
                    SharedResourceFragmentDirections.actionSharedResourceFragmentToSharedResourceDetailFragment(resource)
                )
                sharedResourceViewModel.onSharedResourceDetailNavigated()
            }
        })


        val adapter = SharedResourceAdapter(SharedResourceListener { resourceId ->
            sharedResourceViewModel.onAddButtonClicked(resourceId)
        })

        binding.rvSharedResource.adapter = adapter

        sharedResourceViewModel.resources.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}