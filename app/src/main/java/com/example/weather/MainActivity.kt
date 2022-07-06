package com.example.weather
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.Gravity.LEFT
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.weather.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.invoke.ConstantCallSite
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    private val permissionId = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controlNavigationDrawer()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocation()


    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    }
                }
            } else {

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    private fun controlNavigationDrawer() {

        binding.btnSearchByDate.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.searchByDate)
        }

        /*binding.btnWeatherFormat.setOnClickListener{
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.weatherFormat)

        }*/
        binding.btnLoginLogout.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.login)
        }


        binding.btnAboutUs.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.aboutUs)
        }

        binding.btnSearchByLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.findByLocation)

        }
        binding.btnFindMyLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.findMyLocation)
        }
        binding.btnTermsCondition.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.teamsCondition)
        }



        binding.btnSubscription.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.upgradeToPremium)
        }

        binding.btnPrivacyPolicy.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.upgradeToPremium)
        }


    }
}














