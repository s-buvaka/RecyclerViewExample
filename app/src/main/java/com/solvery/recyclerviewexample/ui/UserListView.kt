package com.solvery.recyclerviewexample.ui

import com.solvery.recyclerviewexample.data.models.User

interface UserListView {

    fun showLoader(isShow: Boolean)

    fun showMessage(message: String)

    fun updateUsers(users: List<User>)
}