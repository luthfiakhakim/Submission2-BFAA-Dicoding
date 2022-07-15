package com.dicoding.submission_githubuserapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission_githubuserapp.api.RetrofitService
import com.dicoding.submission_githubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<User>>()

    fun setListFollowerUser(username: String) {
        RetrofitService.apiInstance.getFollower(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>,
            ) {
                if (response.isSuccessful) {
                    listFollower.postValue(response.body())
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getListFollowerUser(): LiveData<ArrayList<User>> {
        return listFollower
    }
}