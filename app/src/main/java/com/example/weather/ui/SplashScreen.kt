package com.example.weather.ui
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentSplashScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class SplashScreen : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnAgree.setOnClickListener {

              findNavController().navigate(R.id.action_splashScreen2_to_login2)

        }
        binding.termsAndCondition.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreen2_to_teamsCondition2)
        }

    }

    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location = it.result
                    Log.d("TAG", "getLocation: $location")
                    if (location != null) {
                        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                        /*val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude,1)
                        val address=list[0]*/

                    }else{
                        Log.d("TAG", "getLocation: else block ")
                    }
                }
            }
        }
    }


    private fun isLocationEnabled(): Boolean {

            val locationManager: LocationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

        private fun checkPermission(): Boolean {

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
            return false
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
