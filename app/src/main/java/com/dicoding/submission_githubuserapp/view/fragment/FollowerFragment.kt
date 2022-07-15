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
import com.dicoding.submission_githubuserapp.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {
    private val listUser: ArrayList<User> = arrayListOf()
    private lateinit var binding: FragmentListFollowBinding
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        binding = FragmentListFollowBinding.inflate(inflater, container, false)

        showRecyclerView()
        progessBar(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
        viewModel.setListFollowerUser(username)
        viewModel.getListFollowerUser().observe(viewLifecycleOwner) {
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

    private fun showRecyclerView() {
        adapter = ListUserAdapter(listUser)
        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(context)
            rvFollow.adapter = adapter
        }
    }

    private fun progessBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(state: Boolean) {
        binding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }
}