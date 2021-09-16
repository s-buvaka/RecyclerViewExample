package com.solvery.recyclerviewexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.solvery.recyclerviewexample.data.models.Story
import com.solvery.recyclerviewexample.data.network.NyTimesApi
import com.solvery.recyclerviewexample.data.network.RetrofitFactory
import com.solvery.recyclerviewexample.data.repo.StoriesRepository
import com.solvery.recyclerviewexample.data.repo.StoriesRepositoryImpl
import com.solvery.recyclerviewexample.databinding.ActivityStoriesBinding
import com.solvery.recyclerviewexample.presenter.StoriesPresenter

class StoriesActivity : AppCompatActivity(), StoriesView {

    private val storiesAdapter: StoriesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        StoriesAdapter(::onStoryClick)
    }

    private val nyTimesApi: NyTimesApi by lazy { RetrofitFactory.nyTimesApi }
    private val storiesRepository: StoriesRepository by lazy { StoriesRepositoryImpl(nyTimesApi) }
    private val presenter: StoriesPresenter by lazy(LazyThreadSafetyMode.NONE) {
        StoriesPresenter(storiesRepository)
    }

    private lateinit var binding: ActivityStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attach(this)

        initRecycler()
        initSearchView()
        presenter.loadData()
    }


    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun updateStories(stories: List<Story>) {
        storiesAdapter.update(stories)
    }

    override fun showLoader(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecycler() {
        with(binding.usersRecycler) {
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

    private fun onStoryClick(story: Story) {
        presenter.onUserClick(story)
    }
}