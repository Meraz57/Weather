package com.example.weather.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.weather.databinding.FragmentNewsViewBinding
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewFragment : Fragment() {
   private var _binding:FragmentNewsViewBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
   _binding= FragmentNewsViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNews()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }



    }

    private fun getNews() {
        val api=RetrofitClient.apiInterface
        api.newsPost().enqueue(object :Callback<ResponseNewsData>{
            override fun onResponse(
                call: Call<ResponseNewsData>,
                response: Response<ResponseNewsData>
            ) {
                if (response.isSuccessful){
                    val news= response.body()!!.data?.get(0)
                    binding.apply {

                        title.text=news?.title
                        tittleDescription.text=news?.description
                        imageView.load(news?.image)


                    }


                }

            }

            override fun onFailure(call: Call<ResponseNewsData>, t: Throwable) {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}