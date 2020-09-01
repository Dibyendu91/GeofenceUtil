package com.dibyendu.gefenceutil.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dibyendu.gefenceutil.R
import com.dibyendu.gefenceutil.data.model.Reminder
import com.dibyendu.gefenceutil.data.model.Location
import com.dibyendu.gefenceutil.databinding.FragmentAddGeofenceBinding
import com.dibyendu.gefenceutil.util.Resource
import com.dibyendu.gefenceutil.util.Type
import com.dibyendu.gefenceutil.util.hideKeyboard
import com.dibyendu.gefenceutil.view.listener.SubmitClickListener
import com.dibyendu.gefenceutil.view.viewmodel.AddGeofenceViewModel
import com.dibyendu.gefenceutil.view.viewmodel.AddGeofenceViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_geofence.view.*

class AddGeofenceFragment : Fragment(R.layout.fragment_add_geofence), SubmitClickListener {

    private lateinit var binding: FragmentAddGeofenceBinding
    private val viewModel by viewModels<AddGeofenceViewModel> {
        AddGeofenceViewModelFactory((activity as GeofenceActivity).appComponent.repository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGeofenceBinding.inflate(inflater, container, false)
        binding.listener = this
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun geofenceStatus() {
        viewModel.submitStatus.observe(this) { status ->
            when (status) {
                is Resource.Loading -> binding.showLoader = true
                is Resource.Success -> {
                    binding.showLoader = false
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.add_geofence_success_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_addGeofenceFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    binding.showLoader = false
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.add_geofence_error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onSubmitClick(view: View) {
        when {
            binding.root.tvLocationName.editText?.text.toString().isEmpty() -> {
                setError(Type.NAME)
            }
            binding.root.tvLatitude.editText?.text.toString()
                .isEmpty() -> {
                setError(Type.LATITUDE)
            }
            binding.root.tvLongitude.editText?.text.toString().isEmpty() -> {
                setError(Type.LONGITUDE)
            }
            else -> {
                hideKeyboard(requireContext(), binding.root)
                viewModel.addGeofence(
                    Reminder(
                        name = binding.root.tvLocationName.editText?.text.toString(),
                        location = Location(
                            binding.root.tvLatitude.editText?.text.toString().toDouble(),
                            binding.root.tvLongitude.editText?.text.toString().toDouble()
                        )
                    )
                )
                geofenceStatus()
            }
        }
    }

    private fun setError(type: Type) {
        when (type) {
            Type.NAME -> binding.root.tvLocationName.editText?.error =
                resources.getString(R.string.required_name_error_message)
            Type.LATITUDE -> binding.root.tvLatitude.editText?.error =
                resources.getString(R.string.required_latitude_error_message)
            Type.LONGITUDE -> binding.root.tvLongitude.editText?.error =
                resources.getString(R.string.required_longitude_error_message)
        }
    }
}