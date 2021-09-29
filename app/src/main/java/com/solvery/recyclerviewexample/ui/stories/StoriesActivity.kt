package com.solvery.recyclerviewexample.ui.stories

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.solvery.recyclerviewexample.R
import com.solvery.recyclerviewexample.application.executors.BackgroundExecutor
import com.solvery.recyclerviewexample.application.executors.Executors
import com.solvery.recyclerviewexample.application.executors.UiThreadExecutor
import com.solvery.recyclerviewexample.data.databse.DatabaseHolder
import com.solvery.recyclerviewexample.data.databse.StoriesDatabase
import com.solvery.recyclerviewexample.data.domain.mappers.Mapper
import com.solvery.recyclerviewexample.data.domain.mappers.StoriesMapper
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.network.NyTimesApi
import com.solvery.recyclerviewexample.data.network.RetrofitFactory
import com.solvery.recyclerviewexample.data.network.StoriesSection
import com.solvery.recyclerviewexample.data.network.models.ResultsItem
import com.solvery.recyclerviewexample.data.repo.StoriesRepository
import com.solvery.recyclerviewexample.data.repo.StoriesRepositoryImpl
import com.solvery.recyclerviewexample.databinding.ActivityStoriesBinding
import com.solvery.recyclerviewexample.ui.models.StoryVO
import com.solvery.recyclerviewexample.ui.models.VisualObject
import com.solvery.recyclerviewexample.ui.stories.mappers.StoriesVoMapper
import com.solvery.recyclerviewexample.ui.stories.presenter.StoriesPresenter
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StoriesActivity : AppCompatActivity(), StoriesView {

    private val executors: Executors = Executors(
        mainThread = UiThreadExecutor(),
        background = BackgroundExecutor()
    )
    private val nyTimesApi: NyTimesApi by lazy { RetrofitFactory.nyTimesApi }
    private val database: StoriesDatabase by lazy { DatabaseHolder.storiesDatabase }
    private val storyMapper: Mapper<ResultsItem, Story> = StoriesMapper()
    private val storiesRepository: StoriesRepository by lazy {
        StoriesRepositoryImpl(executors, nyTimesApi, database, storyMapper)
    }
    private val storyVoMapper: Mapper<Story, StoryVO> = StoriesVoMapper()
    private val presenter: StoriesPresenter by lazy(LazyThreadSafetyMode.NONE) {
        StoriesPresenter(storiesRepository, storyVoMapper)
    }

    private lateinit var binding: ActivityStoriesBinding

    private val storiesAdapter: StoriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        StoriesAdapter(::onStoryClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attach(this)

        initRecycler()
        initSearchView()
        initSwipeToRefresh()
        initSpinner()
        presenter.loadData(false, getCurrentCategory())
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun updateStories(stories: List<VisualObject>) {
        storiesAdapter.update(stories)
    }

    override fun showLoader(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecycler() {
        with(binding.storiesRecycler) {
            layoutManager = LinearLayoutManager(this@StoriesActivity)
            adapter = storiesAdapter
        }
    }

    private fun initSearchView() {
        with(binding.searchView) {
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.handleQuery(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    presenter.handleQuery(query.orEmpty())
                    return true
                }
            })
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.loadData(true, getCurrentCategory())
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.sections_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sectionsSpinner.adapter = adapter
            binding.sectionsSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        presenter.loadData(true, StoriesSection.values()[position].section)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
        }
    }

    private fun onStoryClick(story: StoryVO) {
        presenter.onUserClick(story)
    }

    private fun getCurrentCategory(): String =
        StoriesSection.values()[binding.sectionsSpinner.selectedItemPosition].section
}