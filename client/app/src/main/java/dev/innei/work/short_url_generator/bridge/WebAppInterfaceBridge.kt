package dev.innei.work.short_url_generator.bridge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import dev.innei.work.short_url_generator.constants.EventType
import dev.innei.work.short_url_generator.model.repository.url.URLDatabase
import dev.innei.work.short_url_generator.model.repository.url.service.URLService
import dev.innei.work.short_url_generator.view.MWebView
import dev.innei.work.short_url_generator.view.MainActivity
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread


class WebAppInterfaceBridge(
    private val mContext: Context,
    private val webView: MWebView,

    ) {
    private val logger = Logger.getLogger(WebAppInterfaceBridge::class.java.name)

    @JavascriptInterface
    fun showToast(toast: String): String {

        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()

        return toast
    }

    @JavascriptInterface
    fun openLink(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        (webView.context as MainActivity).startActivity(i)

    }


    @JavascriptInterface
    fun callGeneratorApi(url: String) {

        thread {
            kotlin.run {
                val model = URLService.generatorURL(mContext, url)

                if (model != null) {
                    (webView).emitEventByBus(model, EventType.APPEND)
                }
            }
        }
    }

    @JavascriptInterface
    fun ipcEmitter(eventType: String, payload: String? = "") {
        logger.log(Level.INFO, "EventType: $eventType, payload: $payload")
        val dao = URLDatabase.getDatabase(context = mContext).urlDao
        val data = JSON.parse(payload)
        when (val type = eventType) {
            EventType.WANT_CREATE.name -> {
                val url = (data as JSONObject).getString("data")
                callGeneratorApi(url)
            }

            EventType.REMOVE_ALL.name -> {

            }

            EventType.REMOVE.name -> {

            }

            EventType.REMOVE_MANY.name -> {
                (data as JSONArray).forEach { id ->
                    logger.log(Level.INFO, "$id")
                    dao.deleteById(id as Int)
                }
                this.webView.emitEventByBus(data, EventType.REMOVE_MANY)
            }

            EventType.VISIT.name -> {

            }
            else -> {
                logger.log(Level.INFO, "EventType: $eventType")
            }
        }
    }
}
//
//
//data class DataString(val data: String) {
//
//}