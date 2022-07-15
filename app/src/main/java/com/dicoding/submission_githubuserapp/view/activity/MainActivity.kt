package com.dicoding.submission_githubuserapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_githubuserapp.R
import com.dicoding.submission_githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission_githubuserapp.databinding.ActivityMainBinding
import com.dicoding.submission_githubuserapp.model.User
import com.dicoding.submission_githubuserapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val listUser: ArrayList<User> = arrayListOf()
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "\tGitHub User App"
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.logo_bar)
        }

        adapter = ListUserAdapter(listUser)
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        binding.apply {
            rvListUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListUser.setHasFixedSize(true)
            rvListUser.adapter = adapter
        }

        binding.searchBar.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    progessBar(true)
                    viewModel.setSearchUser(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    listUser.clear()
                    return false
                }
            })
        }

        viewModel.getSearchUser().observe(this) {
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
    }

    private fun progessBar(state: Boolean) {
            binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showNotFound(state: Boolean) {
        binding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }
}