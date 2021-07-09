package com.arthacker.tazakhabar.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arthacker.tazakhabar.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}