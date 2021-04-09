package dev.innei.work.short_url_generator.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = MWebView(this)

        setContentView(webView)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                webView.post(Runnable {
                    webView.emitEventByBus("aaaaaaaaaaaaaaaaaa", null)
                })

            }
        }, 1000L)
    }
}
