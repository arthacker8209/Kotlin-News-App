package com.arthacker.tazakhabar.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?

    //Serializable is used because we are sending whole data class to another fragment
    // because it is not primitive type it is more complex so it will be serialized and bundled up
):Serializable