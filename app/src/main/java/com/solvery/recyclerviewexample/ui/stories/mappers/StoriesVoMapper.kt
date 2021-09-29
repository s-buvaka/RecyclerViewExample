package com.solvery.recyclerviewexample.ui.stories.mappers

import com.solvery.recyclerviewexample.data.domain.mappers.Mapper
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.ui.models.StoryVO

class StoriesVoMapper : Mapper<Story, StoryVO> {

    override fun map(model: Story): StoryVO =
        with(model) {
            StoryVO(
                url = url,
                title = title,
                byline = byline,
                section = section,
                imageCover = imageCover
            )
        }
}