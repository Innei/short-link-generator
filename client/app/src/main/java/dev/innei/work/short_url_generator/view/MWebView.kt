package dev.innei.work.short_url_generator.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.fastjson.JSON
import dev.innei.work.short_url_generator.bridge.WebAppInterfaceBridge
import dev.innei.work.short_url_generator.constants.EventType
import java.util.logging.Level
import java.util.logging.Logger


@SuppressLint("SetJavaScriptEnabled")
class MWebView(context: Context) : WebView(context) {
    private val logger: Logger = Logger.getLogger("logger")

    init {
        this.webViewClient = object : WebViewClient() {
//            override fun onLoadResource(view: WebView?, url: String?) {
//                super.onLoadResource(view, url)
//                Log.d("onLoadResource", "----------------------")
//            }

//            override fun onPageFinished(view: WebView?, url: String?) {
//                Log.d("onPageFinished", "----------------------")
//                super.onPageFinished(view, url)
//
//            }

//            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                Log.d("onPageStarted", "----------------------")
//
//                super.onPageStarted(view, url, favicon)
//            }
        }

        this.settings.javaScriptEnabled = true
        this.loadUrl("file:///android_asset/index.html")

        this.registerInterface()

        this.webChromeClient = object : WebChromeClient() {


            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.apply {
                    Log.d(MWebView::class.java.name, "${message()} -- From line ${lineNumber()} of ${sourceId()}")
                }
                return true
            }
        }
    }


    fun emitEventByBus(data: Any, eventName: EventType = EventType.DISPATCH) {
        this.post {
            this.evaluateJavascript(
                """
            (() => {
                const bus = window.bus
                if(bus) {
                    bus.emit("${eventName.name}", ${JSON.toJSONString(data)})
                }
                
            })()
            
        """.trimIndent()
            ) { e ->
                logger.log(Level.INFO, "[Event: ${eventName.name}]: ${JSON.toJSONString(data)}")
            }
        }

    }

    private fun registerInterface() {
        this.addJavascriptInterface(WebAppInterfaceBridge(context, this), "Bridge")
    }
}