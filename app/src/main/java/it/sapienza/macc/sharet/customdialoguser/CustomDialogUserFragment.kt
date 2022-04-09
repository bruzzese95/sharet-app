package it.sapienza.macc.sharet.customdialoguser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.sapienza.macc.sharet.R
import it.sapienza.macc.sharet.customdialogresource.CustomDialogResourceFragmentDirections
import it.sapienza.macc.sharet.customdialogresource.CustomDialogResourceViewModelFactory
import it.sapienza.macc.sharet.database.SharedResourceDatabase
import it.sapienza.macc.sharet.database.UserDatabase
import it.sapienza.macc.sharet.databinding.FragmentCustomDialogUserBinding


class CustomDialogUserFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCustomDialogUserBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_custom_dialog_user, container, false)
        val application = requireNotNull(this.activity).application


        //Create an instance of the ViewModelFactory
        val dataSource = UserDatabase.getInstance(application).UserDatabaseDao
        val viewModelFactory = CustomDialogUserViewModelFactory(dataSource)

        //Get a reference to the ViewModel associated with this fragment
        val customDialogViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CustomDialogUserViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.customDialogUserViewModel = customDialogViewModel


        customDialogViewModel.navigateToSharedResource.observe(viewLifecycleOwner) {
            if (it == true) {
                customDialogViewModel.doneNavigating()
            }
        }


        binding.submitAddUserButton.setOnClickListener { view: View ->
            val idResource = CustomDialogUserFragmentArgs.fromBundle(requireArguments()).idResource
            val idUser = binding.idUser.text.toString()
            customDialogViewModel.onAddedUsername(idUser.toInt(), idResource)

            this.findNavController().navigate(CustomDialogUserFragmentDirections.actionCustomDialogUserFragmentToSharedResourceFragment())
        }

        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}