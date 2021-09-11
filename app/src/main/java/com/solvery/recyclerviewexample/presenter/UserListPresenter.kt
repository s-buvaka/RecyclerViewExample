package com.solvery.recyclerviewexample.presenter

import com.solvery.recyclerviewexample.data.repo.UsersRepository
import com.solvery.recyclerviewexample.ui.UserListView
import kotlin.random.Random

class UserListPresenter(private val usersRepository: UsersRepository) {

    private var view: UserListView? = null

    fun attach(view: UserListView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    fun loadData() {
        val users = usersRepository.getUsers(Random.nextInt(100))

        view?.updateUsers(users)
    }
}