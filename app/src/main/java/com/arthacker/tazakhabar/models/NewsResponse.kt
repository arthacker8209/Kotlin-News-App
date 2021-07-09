package com.arthacker.tazakhabar.models


import com.arthacker.tazakhabar.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)