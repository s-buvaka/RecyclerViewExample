package com.solvery.recyclerviewexample.application.executors

import java.util.concurrent.Executor

class Executors(
    val mainThread: Executor,
    val background: Executor,
)