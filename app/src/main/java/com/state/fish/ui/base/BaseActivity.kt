package com.state.fish.ui.base

import android.app.Dialog
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.state.fish.R


open class BaseActivity : AppCompatActivity() {

    private lateinit var mDialog: Dialog

    fun showDialog(text: String) {
        mDialog = Dialog(this)
        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mDialog.setContentView(R.layout.fish_dialog)
        val title : TextView = mDialog.findViewById(R.id.tv_progress_text)

        title.text = text
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        //Start the dialog and display it on screen.
        mDialog.show()
    }

    fun hideDialog(){
        mDialog.dismiss()
    }

}