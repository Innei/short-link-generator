package dev.innei.work.short_url_generator.bridge

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import dev.innei.work.short_url_generator.constants.EventType
import dev.innei.work.short_url_generator.view.MWebView
import java.util.logging.Level
import java.util.logging.Logger

class WebAppInterfaceBridge(private val mContext: Context, private val webView: MWebView) {
    private val logger = Logger.getLogger(WebAppInterfaceBridge::class.java.name)

    @JavascriptInterface
    fun showToast(toast: String): String {

        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()

        return toast
    }

//
//    @JavascriptInterface
//    fun callGeneratorApi(url: String) {
//
//        thread {
//            kotlin.run {
//                val model = URLService.generatorURL(mContext, url)
//
//                if (model != null) {
//                    (webView).emitEventByBus(model, EventType.APPEND)
//                }
//            }
//        }
//    }

    @JavascriptInterface
    fun ipcEmitter(eventType: String, payload: Any? = null) {
        logger.log(Level.INFO, "EventType: $eventType")
        when (val type = eventType) {
            EventType.WANT_CREATE.name -> {

            }

            EventType.REMOVE_ALL.name -> {

            }

            EventType.REMOVE.name -> {

            }

            EventType.VISIT.name -> {

            }
            else -> {
                logger.log(Level.INFO, "EventType: $eventType")
            }
        }
    }
}