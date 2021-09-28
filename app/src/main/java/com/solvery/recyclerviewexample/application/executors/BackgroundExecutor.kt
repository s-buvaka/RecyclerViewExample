package com.solvery.recyclerviewexample.application.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BackgroundExecutor : Executor {

    private val backgroundExecutor = Executors.newCachedThreadPool()

    override fun execute(runnable: Runnable) {
        backgroundExecutor.execute(runnable)
    }
}