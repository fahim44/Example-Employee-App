package com.fahim.example_employee_app.util


import com.fahim.example_employee_app.callBack.AlertDialogCallBack

abstract class ViewUtils{
    companion object {
        const val SHOW_POSITIVE_BUTTON = (1 shl 0)
        const val SHOW_NEGATIVE_BUTTON = (1 shl 1)
        const val IS_CANCELABLE = (1 shl 2)
    }

    abstract fun showAlertDialog(title:String?, message:String?, viewInfo:Int, callBack: AlertDialogCallBack)
}