package dev.innei.work.short_url_generator.model.repository.url

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.innei.work.short_url_generator.model.repository.url.dao.URLDao
import dev.innei.work.short_url_generator.utils.DateConverter

@Database(entities = [URLModel::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class URLDatabase : RoomDatabase() {
    abstract val urlDao: URLDao


    companion object {
        private var INSTANCE: URLDatabase? = null
        fun getDatabase(context: Context): URLDatabase {

            if (INSTANCE == null) {
                synchronized(URLDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(context.applicationContext, URLDatabase::class.java, "url_database")
                                .build()
                    }
                }
            }
            return INSTANCE as URLDatabase

        }
    }
}
