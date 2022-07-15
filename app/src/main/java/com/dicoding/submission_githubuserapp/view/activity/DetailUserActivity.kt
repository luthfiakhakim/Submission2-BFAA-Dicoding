package com.dicoding.submission_githubuserapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submission_githubuserapp.adapter.ListFollowPageAdapter
import com.dicoding.submission_githubuserapp.databinding.ActivityDetailUserBinding
import com.dicoding.submission_githubuserapp.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val users = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, users)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]
        viewModel.setUserDetail(users.toString())
        viewModel.users.observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar)
                        .circleCrop()
                        .into(userAvatar)
                    username.text = it.username
                    name.text = it.name
                    tvTotalRepo.text = it.repository.toString()
                    tvTotalFollower.text = it.follower.toString()
                    tvTotalFollowing.text = it.following.toString()
                    company.text = it.company
                    tvLocation.text = it.location
                }
            }
        }

        val listFollowPageAdapter = ListFollowPageAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = listFollowPageAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
        supportActionBar?.elevation = 0f
    }

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
    }
}