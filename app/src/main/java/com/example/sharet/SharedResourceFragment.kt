package com.example.sharet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.sharet.databinding.FragmentSharedResourceBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SharedResourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharedResourceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSharedResourceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shared_resource,
            container, false)
        binding.addResourceButton.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_sharedResourceFragment_to_customDialog))

        val adapter = SharedResourceAdapter()
        binding.rvSharedResource.adapter = adapter

        //implement observe with view model

        return binding.root
    }
}