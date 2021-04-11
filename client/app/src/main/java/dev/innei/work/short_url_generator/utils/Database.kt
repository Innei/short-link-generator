package dev.innei.work.short_url_generator.utils

import android.app.Application
import android.content.Context
import dev.innei.work.short_url_generator.model.repository.url.URLDatabase

class Database {

    companion object {
        fun getURLDatabase(context: Context): URLDatabase {
            return URLDatabase.getDatabase(context)
        }

        fun getURLDatabase(application: Application): URLDatabase {
            return URLDatabase.getDatabase(application)
        }
    }
}