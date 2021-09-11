package com.solvery.recyclerviewexample.data.repo

import com.solvery.recyclerviewexample.data.models.User
import kotlin.random.Random

interface UsersRepository {

    fun getUsers(count: Int): List<User>
}

internal class UsersRepositoryImpl : UsersRepository {

    override fun getUsers(count: Int): List<User> = generateUsers(count)

    private fun generateUsers(count: Int): List<User> {
        val users = mutableListOf<User>()

        for (index in 0..count) {
            users.add(
                User(
                    id = index.toString(),
                    name = NAMES[Random.nextInt(NAMES.size)],
                    surname = SURNAMES[Random.nextInt(SURNAMES.size)],
                    age = Random.nextInt(100),
                )
            )
        }

        return users
    }

    companion object {

        private val NAMES = listOf(
            "Sergey",
            "Alexandr",
            "Boris",
            "Andrey",
            "Ivan",
            "Maxim",
            "Petr",
            "Illa",
            "Pavel",
            "Nikolay",
        )

        private val SURNAMES = listOf(
            "Grebnev",
            "Lyapustin",
            "Savostin",
            "Grigorov",
            "Moshev",
            "Krivonosov",
            "Dolgov",
            "Snytko",
            "Shepalyn",
            "Frolov",
        )
    }
}