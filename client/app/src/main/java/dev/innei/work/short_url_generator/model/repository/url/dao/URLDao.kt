package dev.innei.work.short_url_generator.model.repository.url.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.innei.work.short_url_generator.model.repository.url.URLModel

@Dao
interface URLDao {
    @Query("SELECT * FROM urls")
    fun findAll(): LiveData<List<URLModel>>

    @Query("SELECT * FROM urls WHERE UID = :id LIMIT 1")
    fun findById(id: Int): URLModel?

    @Insert
    fun insertAll(vararg urls: URLModel)

    @Insert
    fun insert(url: URLModel): Long

    @Delete
    fun delete(url: URLModel)

    @Query("DELETE FROM urls WHERE uid = :id")
    fun deleteById(id: Int)

}