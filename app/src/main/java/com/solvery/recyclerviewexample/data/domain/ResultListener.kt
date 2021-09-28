package com.solvery.recyclerviewexample.data.domain

interface ResultListener<T> {

    fun onSuccess(data: T)

    fun onError(error: Throwable)
}