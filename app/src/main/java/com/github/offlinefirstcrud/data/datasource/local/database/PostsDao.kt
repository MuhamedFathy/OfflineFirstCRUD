package com.github.offlinefirstcrud.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.github.offlinefirstcrud.data.datasource.local.entity.PostDBEntity

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostDBEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPosts(posts: List<PostDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(posts: PostDBEntity)

    @Update
    suspend fun updatePost(post: PostDBEntity)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePost(id: Int)
}
