package dev.innei.work.short_url_generator.model.repository.url

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "urls")
data class URLModel(

    @PrimaryKey(autoGenerate = true)

    @SerializedName("uid")
    var uid: Int,

    @SerializedName("fullUrl")
    @ColumnInfo(name = "full_url")
    val fullUrl: String,

    @ColumnInfo(name = "code")
    @SerializedName("code")
    val code: String,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: Date


) {
    constructor(fullUrl: String, code: String, createdAt: Date) : this(0, fullUrl, code, createdAt)
}