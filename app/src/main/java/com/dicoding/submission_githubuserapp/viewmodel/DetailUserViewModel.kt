package com.dicoding.submission_githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission_githubuserapp.api.RetrofitService
import com.dicoding.submission_githubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val users = MutableLiveData<User>()
    fun setUserDetail(username: String) {
        RetrofitService.apiInstance.getUserDetail(username).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>,
            ) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }
}