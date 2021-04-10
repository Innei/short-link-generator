package dev.innei.work.short_url_generator.model.repository.url.service

import android.content.Context
import android.util.Log
import dev.innei.work.short_url_generator.model.repository.url.URLModel
import dev.innei.work.short_url_generator.model.repository.url.api.URLDto
import dev.innei.work.short_url_generator.utils.Database
import dev.innei.work.short_url_generator.utils.Network
import java.util.*

class URLService {
    companion object {
        fun generatorURL(context: Context, rawURL: String): URLModel? {
            val db = Database.getURLDatabase(context)
            val res = Network.shared.api.generatorLink(URLDto(rawURL)).execute()
            if (res.isSuccessful) {
                val body = res.body()
                if (body != null) {
                    val model = URLModel(rawURL, body.url, Date())
                    db.urlDao.insert(model)
                    return model
                } else {
                    Log.d(URLService::class.java.name, "body is empty.")
                }

            }
            return null
        }
    }


}