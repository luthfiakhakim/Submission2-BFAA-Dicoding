package com.dicoding.submission_githubuserapp.api

import com.dicoding.submission_githubuserapp.model.User
import com.dicoding.submission_githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getSearchUser(
        @Query("q")
        query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getUserDetail(
        @Path("username")
        username: String,
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollower(
        @Path("username")
        username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(
        @Path("username")
        username: String,
    ): Call<ArrayList<User>>
}