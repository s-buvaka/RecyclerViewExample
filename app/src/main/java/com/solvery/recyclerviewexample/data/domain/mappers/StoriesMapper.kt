package com.solvery.recyclerviewexample.data.domain.mappers

import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.network.models.ResultsItem

class StoriesMapper : Mapper<ResultsItem, Story> {

    override fun map(model: ResultsItem): Story =
        with(model) {
            Story(
                url = url.orEmpty(),
                title = title.orEmpty(),
                byline = byline.orEmpty(),
                section = section.orEmpty(),
                imageCover = multimedia?.last()?.url.orEmpty()
            )
        }
}