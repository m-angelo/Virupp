package com.healtify.virupp.classes

import com.healtify.virupp.R
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import kotlinx.android.synthetic.main.dialog_exit.*


class ExitDialogBox
    (var c: Activity) : Dialog(c), View.OnClickListener {
    var d: Dialog? = null
    var yes: Button? = null
    var no: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_exit)
        btn_yes.setOnClickListener(this)
        btn_no.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_yes -> c.finish()
            R.id.btn_no -> dismiss()
            else -> {
            }
        }
        dismiss()
    }

}