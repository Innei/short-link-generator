package dev.innei.work.short_url_generator.model.repository.url

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.innei.work.short_url_generator.model.repository.url.dao.URLDao
import dev.innei.work.short_url_generator.utils.DateConverter

@Database(entities = [URLModel::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class URLDatabase : RoomDatabase() {
    abstract val urlDao: URLDao

}
