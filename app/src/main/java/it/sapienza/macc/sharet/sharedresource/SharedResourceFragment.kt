package it.sapienza.macc.sharet.sharedresource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import it.sapienza.macc.sharet.R
import it.sapienza.macc.sharet.auth.LoginViewModel
import it.sapienza.macc.sharet.database.SharedResourceDatabase
import it.sapienza.macc.sharet.databinding.FragmentSharedResourceBinding
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.sensor.MagSensorActivity

/**
 * A simple [Fragment] subclass.
 * Use the [SharedResourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharedResourceFragment : Fragment() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentSharedResourceBinding
    private val TAG ="SharedResourceFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shared_resource,
            container, false
        )

        val sharedPref = activity?.getSharedPreferences(requireContext().packageName+".auth", Context.MODE_PRIVATE)


        val application = requireNotNull(this.activity).application

        val dataSource = SharedResourceDatabase.getInstance(application).sharedResourceDatabaseDao

        val viewModelFactory = SharedResourceViewModelFactory(sharedPref, dataSource, application)

        val sharedResourceViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SharedResourceViewModel::class.java)

        binding.sharedResourceViewModel = sharedResourceViewModel

        binding.lifecycleOwner = this

        binding.logoutButton.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()) }





        sharedResourceViewModel.navigateToCustomDialogResource.observe(viewLifecycleOwner
        ) { resource ->
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
                sharedResourceViewModel.onCustomDialogResourceNavigated()
            }
        }



        sharedResourceViewModel.navigateToSharedResourceCalendar.observe(viewLifecycleOwner
        ) { resource ->
            resource?.let {
                this.findNavController().navigate(
                    SharedResourceFragmentDirections.actionSharedResourceFragmentToSharedResourceCalendarFragment()
                )
                sharedResourceViewModel.onSharedResourceCalendarNavigated()
            }
        }



        sharedResourceViewModel.navigateToCustomDialogAddUser.observe(viewLifecycleOwner
        ) { resource ->
            resource?.let {
                this.findNavController().navigate(
                    SharedResourceFragmentDirections.actionSharedResourceFragmentToCustomDialogUserFragment()
                )
                sharedResourceViewModel.onCustomDialogAddUserNavigated()
            }
        }





        val adapter = SharedResourceAdapter(SharedResourceListener { resourceId ->
            sharedPref?.edit()?.putInt("idResource", resourceId)?.apply()
            sharedResourceViewModel.onSharedResourceButtonClicked(resourceId)
        }, DeleteResourceListener { resourceId ->
            showDeleteDialog(resourceId)
        }, AddUserListener {
            sharedResourceViewModel.onAddUserButtonClicked()
        })



        binding.rvSharedResource.adapter = adapter

        sharedResourceViewModel.sharedResourcesList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }


        binding.sensorButton.setOnClickListener {
            val intent = Intent(requireContext(), MagSensorActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleAuthState()
    }


    private fun showDeleteDialog(resourceId: Int) {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.custom_dialog_delete_resource)

        dialog.window?.setBackgroundDrawableResource(R.drawable.round_corner)

        dialog.findViewById<TextView>(R.id.positive_button).setOnClickListener{

            binding.sharedResourceViewModel?.onClearWithId(resourceId)

            val retrofit = SharedResourceApi.retrofitService.deleteResource(resourceId)

            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.negative_button).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun handleAuthState() {
        val navController = findNavController()
        val sharedPref = activity?.getSharedPreferences(requireContext().packageName+".auth", Context.MODE_PRIVATE)

        loginViewModel.authenticationState.observe(viewLifecycleOwner) { authState ->
            when(authState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    Log.i(TAG, "Authenticated")
                    /*binding.textView.text = String.format(
                        "Hello %s! Your idToken is %s",
                        FirebaseAuth.getInstance().currentUser?.displayName,
                        sharedPref?.getString("id_token", null) //TODO WIP
                    )*/
                    //Log.i(TAG, sharedPref?.getString("id_token", null).toString())
                }
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    Log.i(TAG, "Unauthenticated")
                    navController.navigate(R.id.action_shared_resource_fragment_to_loginFragment)
                }
                else ->
                    Log.i(TAG, "authState=$authState not managed in the view model.")
            }
        }
    }
}