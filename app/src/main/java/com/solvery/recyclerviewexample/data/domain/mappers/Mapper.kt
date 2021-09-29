package com.solvery.recyclerviewexample.data.domain.mappers

interface Mapper<E, K> {

    fun map(model: E): K
}