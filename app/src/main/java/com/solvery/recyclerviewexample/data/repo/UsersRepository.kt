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
            "Сергей",
            "Алекснадр",
            "Борис",
            "Андрей",
            "Иван",
            "Максим",
            "Петр",
            "Илья",
            "Павел",
            "Николай",
        )

        private val SURNAMES = listOf(
            "Гребнев",
            "Ляпустин",
            "Савостин",
            "Григоров",
            "Мощев",
            "Кривоносов",
            "Долгов",
            "Снытко",
            "Щепалин",
            "Фролов",
        )
    }
}