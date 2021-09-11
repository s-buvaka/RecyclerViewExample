package com.solvery.recyclerviewexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.solvery.recyclerviewexample.data.models.User
import com.solvery.recyclerviewexample.data.repo.UsersRepository
import com.solvery.recyclerviewexample.data.repo.UsersRepositoryImpl
import com.solvery.recyclerviewexample.databinding.ActivityUserListBinding
import com.solvery.recyclerviewexample.presenter.UserListPresenter

class UserListActivity : AppCompatActivity(), UserListView {

    private val usersAdapter: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) { UsersAdapter(::onUserClick) }

    private val usersRepository: UsersRepository = UsersRepositoryImpl()
    private val presenter: UserListPresenter by lazy(LazyThreadSafetyMode.NONE) {
        UserListPresenter(usersRepository)
    }

    private lateinit var binding: ActivityUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attach(this)

        initRecycler()
        initSearchView()
        presenter.loadData()
    }


    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun updateUsers(users: List<User>) {
        usersAdapter.update(users)
    }

    override fun showLoader(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecycler() {
        with(binding.usersRecycler) {
            layoutManager = LinearLayoutManager(this@UserListActivity)
            adapter = usersAdapter
        }
    }

    private fun initSearchView() {
        with(binding.searchView) {
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.handleQuery(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    presenter.handleQuery(query.orEmpty())
                    return true
                }
            })
        }
    }

    private fun onUserClick(user: User) {
        presenter.onUserClick(user)
    }
}