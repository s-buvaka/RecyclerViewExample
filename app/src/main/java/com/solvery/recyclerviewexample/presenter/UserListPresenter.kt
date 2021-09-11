package com.solvery.recyclerviewexample.presenter

import android.annotation.SuppressLint
import android.os.Handler
import com.solvery.recyclerviewexample.data.models.User
import com.solvery.recyclerviewexample.data.repo.UsersRepository
import com.solvery.recyclerviewexample.ui.UserListView
import kotlin.random.Random

class UserListPresenter(private val usersRepository: UsersRepository) {

    private var view: UserListView? = null

    private lateinit var users: List<User>

    fun attach(view: UserListView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    @SuppressLint("NewApi")
    fun loadData() {
        users = usersRepository.getUsers(Random.nextInt(100))

        view?.updateUsers(users)
    }

    fun onUserClick(user: User) {
        view?.showLoader(true)
        doSomethingWithUser(user)
    }

    fun handleQuery(query: CharSequence) {
        val filteredList = users.filter { user ->
            user.name.contains(query, true) ||
                    user.surname.contains(query, true) ||
                    "${user.name} ${user.surname}".contains(query, true)
        }

        view?.updateUsers(filteredList)
    }

    private fun doSomethingWithUser(user: User) {
        Handler().postDelayed({
            view?.showLoader(false)
            view?.showMessage("${user.name} ${user.surname} was clicked")
        }, Random.nextLong(2000))
    }
}