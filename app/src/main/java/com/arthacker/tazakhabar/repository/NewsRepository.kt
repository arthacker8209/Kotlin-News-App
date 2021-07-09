package com.arthacker.tazakhabar.repository

import com.arthacker.tazakhabar.api.RetrofitInstance
import com.arthacker.tazakhabar.db.ArticleDatabase
import com.arthacker.tazakhabar.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchForNews(q :String , pageNumber: Int) =
        RetrofitInstance.api.searchForNews(q,pageNumber)

    suspend fun upsert(article: Article)= db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article)= db.getArticleDao().deleteArticle(article)

    fun getSavedNews()= db.getArticleDao().getAllArticles()
}