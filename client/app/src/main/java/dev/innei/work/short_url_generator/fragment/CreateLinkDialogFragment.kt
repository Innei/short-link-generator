package dev.innei.work.short_url_generator.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CreateLinkDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(this.buildView())


            builder.setMessage("生成")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun buildView(): ViewGroup {
        val layout = LinearLayout(context)
//    layout.gravity = Gravity.FILL_VERTICAL
        layout.orientation = LinearLayout.VERTICAL


        val tv1 = TextView(context)

        val tv2 = TextView(context)

        layout.addView(tv1)
        layout.addView(tv2)

        return layout
    }
}