package com.dicoding.submission_githubuserapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission_githubuserapp.databinding.FragmentListFollowBinding
import com.dicoding.submission_githubuserapp.model.User
import com.dicoding.submission_githubuserapp.view.activity.DetailUserActivity
import com.dicoding.submission_githubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private val listUser: ArrayList<User> = arrayListOf()
    private lateinit var binding: FragmentListFollowBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        binding = FragmentListFollowBinding.inflate(inflater, container, false)

        showRecycler()
        progessBar(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.setListFollowingUser(username)
        viewModel.getListFollowingUser().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setListUser(it)
                progessBar(false)
            }
            if (it.count() != 0) {
                showNotFound(false)
            } else {
                showNotFound(true)
            }
        }
        return binding.root
    }

    private fun showRecycler() {
        adapter = ListUserAdapter(listUser)
        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(context)
            rvFollow.adapter = adapter
        }
    }

    private fun progessBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showNotFound(state: Boolean) {
        binding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }
}