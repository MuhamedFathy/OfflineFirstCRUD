package com.github.offlinefirstcrud.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.offlinefirstcrud.data.datasource.local.entity.PostDBEntity

const val DATABASE_NAME = "posts.db"
const val DATABASE_VERSION = 1

@Database(
    entities = [PostDBEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false,
)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao
}
