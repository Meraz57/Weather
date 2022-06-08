package com.example.weather
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.LEFT
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controlNavigationDrawer()


    }

    private fun controlNavigationDrawer() {

        binding.btnSearchByDate.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.searchByDate)
        }

        binding.btnWeatherFormat.setOnClickListener{
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController(R.id.fragmentContainerView).navigate(R.id.weatherFormat)

        }
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


    }

    fun drawerMenu(){


        if(binding.drawerLayout.isDrawerOpen(Gravity.LEFT)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}