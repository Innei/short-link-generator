package dev.innei.work.short_url_generator.bridge

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import dev.innei.work.short_url_generator.model.repository.url.service.URLService
import dev.innei.work.short_url_generator.view.MWebView
import kotlin.concurrent.thread

class WebAppInterfaceBridge(private val mContext: Context, private val webView: MWebView) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String): String {

        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()

        return toast
    }


    @JavascriptInterface
    fun callGeneratorApi(url: String) {

        thread {
            kotlin.run {
                val model = URLService.generatorURL(mContext, url)


                if (model != null) {
                    (webView).emitEventByBus(model, null)
                }
            }
        }

    }
}