package com.arthacker.tazakhabar.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthacker.tazakhabar.models.Article
import com.arthacker.tazakhabar.models.NewsResponse
import com.arthacker.tazakhabar.repository.NewsRepository
import com.arthacker.tazakhabar.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse:NewsResponse?=null
    var searchNewsPage = 1
    var searchNewsResponse:NewsResponse?=null

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchBreakingNews(q: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchForNews(q, searchNewsPage)
        searchNews.postValue(handleSearchResponse(response))
    }

    fun saveNewsLocally(article: Article)= viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews()= newsRepository.getSavedNews()

    fun deleteArticle(article: Article)= viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse==null){
                    breakingNewsResponse= resultResponse
                }
                else{
                    val oldResponse = breakingNewsResponse?.articles
                    val newResponse= resultResponse.articles
                    oldResponse?.addAll(newResponse)
                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse==null){
                    breakingNewsResponse= resultResponse
                }
                else{
                    val oldResponse= searchNewsResponse?.articles
                    val newResponse= resultResponse.articles
                    oldResponse?.addAll(newResponse)
                }
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}