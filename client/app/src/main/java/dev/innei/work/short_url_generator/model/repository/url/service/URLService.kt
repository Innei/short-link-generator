package dev.innei.work.short_url_generator.model.repository.url.service

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import dev.innei.work.short_url_generator.model.repository.url.URLModel
import dev.innei.work.short_url_generator.model.repository.url.api.URLDto
import dev.innei.work.short_url_generator.utils.Database
import dev.innei.work.short_url_generator.utils.Network
import java.net.ConnectException
import java.util.*

class URLService {
    companion object {
        fun generatorURL(context: Context, rawURL: String): URLModel? {
            val db = Database.getURLDatabase(context)
            try {
                val res = Network.shared.api.generatorLink(URLDto(rawURL)).execute()
                if (res.isSuccessful) {
                    val body = res.body()
                    if (body != null) {
                        val model = URLModel(rawURL, body.url, Date())
                        val id = db.urlDao.insert(model)
                        model.uid = id.toInt()
                        return model
                    } else {
                        Log.d(URLService::class.java.name, "body is empty.")
                    }

                }

            } catch (e: ConnectException) {
                // https://stackoverflow.com/questions/3875184/cant-create-handler-inside-thread-that-has-not-called-looper-prepare
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show()
                }

                e.message?.let { Log.d(URLService::class.java.name, it) }
            }

            return null
        }
    }


}