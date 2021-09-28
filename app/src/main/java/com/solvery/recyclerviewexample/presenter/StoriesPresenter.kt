package com.solvery.recyclerviewexample.presenter

import android.annotation.SuppressLint
import android.os.Handler
import com.solvery.recyclerviewexample.data.domain.ResultListener
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.repo.StoriesRepository
import com.solvery.recyclerviewexample.ui.StoriesView
import com.solvery.recyclerviewexample.ui.models.SectionVO
import com.solvery.recyclerviewexample.ui.models.StoryVO
import com.solvery.recyclerviewexample.ui.models.VisualObject
import kotlin.random.Random

class StoriesPresenter(private val storiesRepository: StoriesRepository) {

    private var view: StoriesView? = null

    private var stories: List<VisualObject> = emptyList()

    fun attach(view: StoriesView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    @SuppressLint("NewApi")
    fun loadData() {
        storiesRepository.getStories(object : ResultListener<List<Story>> {
            override fun onSuccess(data: List<Story>) {
                val storiesVO = data.map { story ->
                    StoryVO(
                        url = story.url,
                        title = story.title,
                        byline = story.byline,
                        section = story.section
                    )
                }

                val dataList: MutableList<VisualObject> = mapWithCategories(storiesVO)

                stories = dataList
                view?.updateStories(dataList)
            }

            override fun onError(error: Throwable) {
                view?.showMessage("Something is wrong")
            }
        })
    }

    fun onUserClick(story: StoryVO) {
        view?.showLoader(true)
        doSomethingWithUser(story)
    }

    fun handleQuery(query: CharSequence) {
        val filteredList = stories.filter { vo ->
            when (vo) {
                is StoryVO -> {
                    vo.title.contains(query, true) ||
                            vo.byline.contains(query, true) ||
                            "${vo.title} ${vo.byline}".contains(query, true)
                }
                else -> true
            }
        }.filterIndexed { index, vo ->
            when (vo) {
                is StoryVO -> true
                is SectionVO -> {
                    if (index < stories.size - 1) {
                        when (val next = stories[index + 1]) {
                            is SectionVO -> false
                            is StoryVO -> next.section == vo.name
                            else -> false
                        }
                    } else false
                }
                else -> false
            }
        }

        view?.updateStories(filteredList)
    }

    private fun mapWithCategories(storiesVO: List<StoryVO>): MutableList<VisualObject> {
        val dataList: MutableList<VisualObject> = mutableListOf()
        if (storiesVO.isNotEmpty()) {
            var currentSection = SectionVO(storiesVO.first().section)
            dataList.add(currentSection)

            storiesVO.forEach { storyVO ->
                if (currentSection.name != storyVO.section) {
                    currentSection = SectionVO(storyVO.section)
                    dataList.add(currentSection)
                }
                dataList.add(storyVO)
            }
        }
        return dataList
    }

    private fun doSomethingWithUser(story: StoryVO) {
        Handler().postDelayed({
            view?.showLoader(false)
            view?.showMessage("${story.title} ${story.byline} was clicked")
        }, Random.nextLong(2000))
    }
}