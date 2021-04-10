package dev.innei.work.short_url_generator.model.repository.url.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface URLApi {
    @POST("link")
    fun generatorLink(@Body body: URLDto): Call<URLPayload>
}

data class URLDto(
    @SerializedName("url")
    val url: String
)

data class URLPayload(
    @SerializedName("data")
    val url: String
)