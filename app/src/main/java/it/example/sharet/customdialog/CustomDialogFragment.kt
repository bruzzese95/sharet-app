package it.example.sharet.customdialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.example.sharet.R
import it.example.sharet.database.SharedResourceDatabase
import it.example.sharet.databinding.FragmentCustomDialogBinding

class CustomDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCustomDialogBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_custom_dialog, container, false)

        val application = requireNotNull(this.activity).application


        //Create an instance of the ViewModelFactory
        val dataSource = SharedResourceDatabase.getInstance(application).sharedResourceDatabaseDao
        val viewModelFactory = CustomDialogViewModelFactory(dataSource)

        //Get a reference to the ViewModel associated with this fragment
        val customDialogViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CustomDialogViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.customDialogViewModel = customDialogViewModel


        customDialogViewModel.navigateToSharedResource.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                customDialogViewModel.doneNavigating()
            }
        })


        binding.submitCreateResourceButton.setOnClickListener { view: View ->
            customDialogViewModel.onSetSharedResourceName(binding.nameResource.text.toString())

            this.findNavController().navigate(CustomDialogFragmentDirections.actionCustomDialogToSharedResourceFragment())
        }


        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}