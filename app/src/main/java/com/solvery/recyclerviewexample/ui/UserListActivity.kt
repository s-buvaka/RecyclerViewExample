package com.solvery.recyclerviewexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvery.recyclerviewexample.data.models.User
import com.solvery.recyclerviewexample.data.repo.UsersRepository
import com.solvery.recyclerviewexample.data.repo.UsersRepositoryImpl
import com.solvery.recyclerviewexample.databinding.ActivityUserListBinding
import com.solvery.recyclerviewexample.presenter.UserListPresenter

class UserListActivity : AppCompatActivity(), UserListView {

    private val usersAdapter: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) { UsersAdapter() }

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
        presenter.loadData()
    }


    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun updateUsers(users: List<User>) {
        usersAdapter.update(users)
    }

    private fun initRecycler() {
        with(binding.usersRecycler) {
            layoutManager = LinearLayoutManager(this@UserListActivity)
            adapter = usersAdapter
        }
    }
}