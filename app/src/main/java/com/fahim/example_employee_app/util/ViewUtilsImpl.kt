package com.fahim.example_employee_app.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.callBack.AlertDialogCallBack
import javax.inject.Inject

class ViewUtilsImpl @Inject constructor(private val activity:Activity) : ViewUtils(){


    override fun showAlertDialog(title:String?, message:String?, viewInfo:Int, callBack: AlertDialogCallBack){
        val builder = AlertDialog.Builder(activity)

        if((viewInfo and IS_CANCALABLE)>0)  builder.setCancelable(true)
        else builder.setCancelable(false)

        title?.let { builder.setTitle(it) }
        message?.let { builder.setMessage(it) }

        if((viewInfo and SHOW_POSITIVE_BUTTON)>0) {
            builder.setPositiveButton(R.string.yes) { dialog, _ ->
                callBack.onClickPositiveButton()
                dialog.cancel()
            } }

        if((viewInfo and SHOW_NEGATIVE_BUTTON)>0) {
            builder.setNegativeButton(R.string.no) { dialog, _ ->
                callBack.onClickNegativeButton()
                dialog.cancel() } }

        builder.create().show()
    }

}