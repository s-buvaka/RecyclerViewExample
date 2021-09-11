package com.solvery.recyclerviewexample.ui

import com.solvery.recyclerviewexample.data.models.User

interface UserListView {

    fun updateUsers(users: List<User>)
}