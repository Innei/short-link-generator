package dev.innei.work.short_url_generator.bridge

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterfaceToast (private val mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}