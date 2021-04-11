package dev.innei.work.short_url_generator.utils

import dev.innei.work.short_url_generator.model.repository.url.api.URLApi
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    private var retrofit: Retrofit = Builder()
        .baseUrl("http://mbp.cc:8080/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .callbackExecutor { e ->
            e.runCatching { }
        }
        .build()


    val api = this.retrofit.create(URLApi::class.java)

    companion object {
        public val shared = Network()
    }
}