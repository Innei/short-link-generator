package dev.innei.work.short_url_generator.utils

import android.content.Context
import androidx.room.Room
import dev.innei.work.short_url_generator.model.repository.url.URLDatabase

class Database {
    companion object {
        fun getURLDatabase(context: Context): URLDatabase {
            return Room.databaseBuilder(context, URLDatabase::class.java, "url_database").build()
        }
    }
}