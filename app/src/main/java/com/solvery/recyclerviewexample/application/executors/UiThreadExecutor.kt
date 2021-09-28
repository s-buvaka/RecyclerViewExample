package com.solvery.recyclerviewexample.application.executors

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class UiThreadExecutor : Executor {

    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    override fun execute(runnable: Runnable) {
        mainThreadHandler.post(runnable)
    }
}